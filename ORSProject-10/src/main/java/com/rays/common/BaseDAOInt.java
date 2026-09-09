package com.rays.common;

import java.util.List;

/**
 * Base DAO Interface defining generic CRUD operations.
 * 
 * This interface provides abstraction for:
 * - Create (add)
 * - Update
 * - Delete
 * - Find operations
 * - Search with and without pagination
 * 
 * It is implemented by DAO classes to interact with the database.
 * 
 * @param <T> DTO type extending BaseDTO
 * 
 * @author Abhishish Bhawsar
 */
public interface BaseDAOInt<T extends BaseDTO> {

	/**
	 * Adds a new record to the database.
	 * 
	 * @param dto DTO object
	 * @param userContext current user context
	 * @return generated ID
	 */
	public long add(T dto, UserContext userContext);

	/**
	 * Updates an existing record in the database.
	 * 
	 * @param dto DTO object
	 * @param userContext current user context
	 */
	public void update(T dto, UserContext userContext);

	/**
	 * Deletes a record from the database.
	 * 
	 * @param dto DTO object
	 * @param userContext current user context
	 */
	public void delete(T dto, UserContext userContext);

	/**
	 * Finds a record by primary key.
	 * 
	 * @param pk primary key
	 * @param userContext current user context
	 * @return DTO object
	 */
	public T findByPk(long pk, UserContext userContext);

	/**
	 * Finds a record using a unique attribute.
	 * 
	 * @param attribute field name
	 * @param val value to search
	 * @param userContext current user context
	 * @return DTO object if found
	 */
	public T findByUniqueKey(String attribute, Object val, UserContext userContext);

	/**
	 * Searches records with pagination.
	 * 
	 * @param dto DTO containing search criteria
	 * @param pageNo page number
	 * @param pageSize number of records per page
	 * @param userContext current user context
	 * @return list of results
	 */
	public List search(T dto, int pageNo, int pageSize, UserContext userContext);

	/**
	 * Searches all records without pagination.
	 * 
	 * @param dto DTO containing search criteria
	 * @param userContext current user context
	 * @return list of results
	 */
	public List search(T dto, UserContext userContext);

}