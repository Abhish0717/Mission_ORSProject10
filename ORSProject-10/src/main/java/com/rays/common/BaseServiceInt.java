package com.rays.common;

import java.util.List;

/**
 * Base Service Interface defining business layer operations.
 * 
 * This interface acts as a bridge between Controller and DAO layers. It defines
 * common operations such as: - Add, Update, Save - Delete - Find operations -
 * Search with and without pagination
 * 
 * Implementations of this interface contain business logic and transaction
 * management.
 * 
 * @param <T> DTO type extending BaseDTO
 * 
 * @author Abhishish Bhawsar
 */
public interface BaseServiceInt<T extends BaseDTO> {

	/**
	 * Adds a new record.
	 * 
	 * @param dto         DTO object
	 * @param userContext current user context
	 * @return generated ID
	 */
	public long add(T dto, UserContext userContext);

	/**
	 * Updates an existing record.
	 * 
	 * @param dto         DTO object
	 * @param userContext current user context
	 */
	public void update(T dto, UserContext userContext);

	/**
	 * Saves a record (insert or update).
	 * 
	 * @param dto         DTO object
	 * @param userContext current user context
	 * @return primary key of saved record
	 */
	public long save(T dto, UserContext userContext);

	/**
	 * Deletes a record by ID.
	 * 
	 * @param id          primary key
	 * @param userContext current user context
	 * @return deleted DTO object
	 */
	public T delete(long id, UserContext userContext);

	/**
	 * Finds a record by ID.
	 * 
	 * @param id          primary key
	 * @param userContext current user context
	 * @return DTO object
	 */
	public T findById(long id, UserContext userContext);

	/**
	 * Finds a record using a unique attribute.
	 * 
	 * @param attribute   field name
	 * @param val         value to search
	 * @param userContext current user context
	 * @return DTO object
	 */
	public T findByUniqueKey(String attribute, String val, UserContext userContext);

	/**
	 * Searches records with pagination.
	 * 
	 * @param dto         DTO containing search criteria
	 * @param pageNo      page number
	 * @param pageSize    number of records per page
	 * @param userContext current user context
	 * @return list of results
	 */
	public List search(T dto, int pageNo, int pageSize, UserContext userContext);

	/**
	 * Searches all records without pagination.
	 * 
	 * @param dto         DTO containing search criteria
	 * @param userContext current user context
	 * @return list of results
	 */
	public List search(T dto, UserContext userContext);

}