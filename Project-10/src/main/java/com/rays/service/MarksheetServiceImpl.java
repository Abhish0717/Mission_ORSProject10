package com.rays.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rays.common.BaseServiceImpl;
import com.rays.common.UserContext;
import com.rays.dao.MarksheetDAOInt;
import com.rays.dto.MarksheetDTO;

/**
 * Service implementation for Marksheet entity.
 * 
 * This class extends BaseServiceImpl to provide standard CRUD operations
 * for MarksheetDTO using MarksheetDAOInt.
 * 
 * Additional Features:
 * - Find marksheet by name
 * - Find marksheet by roll number
 * - Retrieve merit list of students
 * 
 * Annotated with @Service to indicate service layer component.
 * Annotated with @Transactional to manage database transactions.
 * 
 * @author Abhishish Bhawsar
 */
@Service
@Transactional
public class MarksheetServiceImpl extends BaseServiceImpl<MarksheetDTO, MarksheetDAOInt>
		implements MarksheetServiceInt {

	/**
	 * Finds marksheet by student name.
	 * 
	 * @param name    student name
	 * @param context user context
	 * @return MarksheetDTO object
	 */
	@Override
	public MarksheetDTO findByName(String name, UserContext context) {
		return baseDao.findByUniqueKey("name", name, context);
	}

	/**
	 * Finds marksheet by roll number.
	 * 
	 * @param rollNo  roll number
	 * @param context user context
	 * @return MarksheetDTO object
	 */
	@Override
	public MarksheetDTO findByRollNo(String rollNo, UserContext context) {
		return baseDao.findByUniqueKey("rollNo", rollNo, context);
	}

	/**
	 * Retrieves top merit list of students.
	 * 
	 * @param context user context
	 * @return list of top MarksheetDTO records
	 */
	@Override
	public List<MarksheetDTO> getMeritList(UserContext context) {
		return baseDao.getMeritList();
	}
}
