package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.common.UserContext;
import com.rays.dto.MarksheetDTO;
import com.rays.dto.StudentDTO;

/**
 * DAO Implementation for Marksheet entity.
 * 
 * This class extends BaseDAOImpl to provide database operations for
 * MarksheetDTO using JPA Criteria API.
 * 
 * Features: - Populates student name using StudentDAO - Dynamic search using
 * multiple fields - Fetches merit list based on subject marks
 * 
 * Annotated with @Repository to indicate DAO layer component.
 * 
 * @author Abhishish Bhawsar
 */
@Repository
public class MarksheetDAOImpl extends BaseDAOImpl<MarksheetDTO> implements MarksheetDAOInt {

	/**
	 * DAO for Student entity.
	 */
	@Autowired
	StudentDAOInt studentDao = null;

	/**
	 * Returns the DTO class type.
	 * 
	 * @return MarksheetDTO class
	 */
	@Override
	public Class<MarksheetDTO> getDTOClass() {
		return MarksheetDTO.class;
	}

	/**
	 * Populates additional fields such as student name.
	 * 
	 * @param dto         MarksheetDTO object
	 * @param userContext current user context
	 */
	@Override
	protected void populate(MarksheetDTO dto, UserContext userContext) {
		if (dto.getStudentId() != null) {
			StudentDTO studentDTO = studentDao.findByPk(dto.getStudentId(), userContext);
			if (studentDTO != null) {
				dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());
			}
		}
	}

	/**
	 * Builds dynamic where clause for Marksheet search.
	 * 
	 * Filters applied: - Name (starts with) - Roll Number (starts with) - Student
	 * ID (exact match)
	 * 
	 * @param dto     MarksheetDTO containing search criteria
	 * @param builder CriteriaBuilder instance
	 * @param qRoot   query root
	 * @return list of predicates
	 */
	@Override
	protected List<Predicate> getWhereClause(MarksheetDTO dto, CriteriaBuilder builder, Root<MarksheetDTO> qRoot) {

		List<Predicate> whereCondition = new ArrayList<Predicate>();

		if (!isEmptyString(dto.getName())) {

			whereCondition.add(builder.like(qRoot.get("name"), dto.getName() + "%"));
		}

		if (!isEmptyString(dto.getRollNo())) {

			whereCondition.add(builder.like(qRoot.get("rollNo"), dto.getRollNo() + "%"));
		}

		if (!isZeroNumber(dto.getStudentId())) {

			whereCondition.add(builder.equal(qRoot.get("studentId"), dto.getStudentId()));
		}

		return whereCondition;
	}
	
	/**
	 * Returns merit list of students.
	 * 
	 * Conditions: - Physics &gt;= 33 - Chemistry &gt;= 33 - Maths &gt;= 33
	 * 
	 * Sorted by total marks (descending).
	 */
	@Override
	public List<MarksheetDTO> getMeritList() {
		List list = super.marksheetMeritList(
				"from MarksheetDTO where physics >=33 and chemistry >=33 and maths >=33 order by (physics+chemistry+maths) desc",
				null);
		return list;
	}

}
