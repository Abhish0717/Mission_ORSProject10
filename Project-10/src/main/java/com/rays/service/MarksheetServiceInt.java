package com.rays.service;

import java.util.List;

import com.rays.common.BaseServiceInt;
import com.rays.common.UserContext;
import com.rays.dto.MarksheetDTO;

/**
 * Service interface for Marksheet entity.
 * 
 * This interface extends BaseServiceInt to provide standard CRUD operations
 * for MarksheetDTO and additional business-specific methods.
 * 
 * Features:
 * - Find marksheet by student name
 * - Find marksheet by roll number
 * - Retrieve merit list of students
 * 
 * @author Abhishish Bhawsar
 */
public interface MarksheetServiceInt extends BaseServiceInt<MarksheetDTO> {
	/**
	 * Finds marksheet by student name.
	 * 
	 * @param name student name
	 * @param context user context
	 * @return MarksheetDTO object
	 */
	public MarksheetDTO findByName(String name, UserContext context);

	/**
	 * Finds marksheet by roll number.
	 * 
	 * @param rollNo roll number
	 * @param context user context
	 * @return MarksheetDTO object
	 */
	public MarksheetDTO findByRollNo(String rollNo, UserContext context);

	/**
	 * Retrieves merit list of students.
	 * 
	 * @param context user context
	 * @return list of MarksheetDTO objects
	 */
	public List<MarksheetDTO> getMeritList(UserContext context);

}