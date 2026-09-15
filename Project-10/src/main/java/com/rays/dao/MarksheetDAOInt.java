package com.rays.dao;

import java.util.List;

import com.rays.common.BaseDAOInt;
import com.rays.dto.MarksheetDTO;

/**
 * DAO Interface for Marksheet entity.
 * 
 * This interface extends BaseDAOInt to inherit
 * generic CRUD operations such as:
 * - Add
 * - Update
 * - Delete
 * - Find by ID
 * - Search
 * 
 * Additional functionality:
 * - Fetch merit list of students based on marks
 * 
 * @author Abhishish Bhawsar
 */
public interface MarksheetDAOInt extends BaseDAOInt<MarksheetDTO> {

	/**
	 * Retrieves merit list of students.
	 * 
	 * @return list of MarksheetDTO representing top-performing students
	 */
	public List<MarksheetDTO> getMeritList();
}
