package com.rays.common;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Abstract Base DAO implementation providing generic database operations.
 * 
 * This class uses JPA EntityManager and Criteria API to perform CRUD operations
 * and dynamic queries.
 * 
 * @param <T> DTO type extending BaseDTO
 * 
 *            Responsibilities: - Add, update, delete operations - Find by
 *            primary key and unique key - Dynamic search with pagination -
 *            Utility methods for validation checks
 * 
 * @author Abhishish Bhawsar
 */
public abstract class BaseDAOImpl<T extends BaseDTO> implements BaseDAOInt<T> {

	/**
	 * EntityManager used for database interactions.
	 */
	@PersistenceContext
	protected EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Returns DTO class type.
	 * 
	 * @return Class of DTO
	 */
	public abstract Class<T> getDTOClass();

	/**
	 * Builds dynamic where clause predicates.
	 * 
	 * @param dto     DTO containing search criteria
	 * @param builder CriteriaBuilder instance
	 * @param qRoot   Root of query
	 * @return List of predicates
	 */
	protected abstract List<Predicate> getWhereClause(T dto, CriteriaBuilder builder, Root<T> qRoot);

	/**
	 * Hook method to populate additional fields before persistence.
	 * 
	 * @param dto         DTO object
	 * @param userContext current user context
	 */
	protected void populate(T dto, UserContext userContext) {

	}

	/**
	 * Adds a new record to the database.
	 * 
	 * @param dto         DTO object
	 * @param userContext current user context
	 * @return generated ID of the record
	 */
	@Override
	public long add(T dto, UserContext userContext) {

		dto.setCreatedBy(userContext.getLogin());
		dto.setCreatedDatetime((new Timestamp(new Date().getTime())));
		dto.setModifiedBy(userContext.getLogin());
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

		populate(dto, userContext);

		entityManager.persist(dto);

		return dto.getId();
	}

	/**
	 * Updates an existing record in the database.
	 * 
	 * @param dto         DTO object
	 * @param userContext current user context
	 */
	@Override
	public void update(T dto, UserContext userContext) {

		dto.setModifiedBy(userContext.getLogin());
		dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

		populate(dto, userContext);

		entityManager.merge(dto);
	}

	/**
	 * Deletes a record from the database.
	 * 
	 * @param dto         DTO object
	 * @param userContext current user context
	 */
	@Override
	public void delete(T dto, UserContext userContext) {
		entityManager.remove(dto);
	}

	/**
	 * Finds a record by primary key.
	 * 
	 * @param pk          primary key
	 * @param userContext current user context
	 * @return DTO object
	 */
	@Override
	public T findByPk(long pk, UserContext userContext) {
		T dto = entityManager.find(getDTOClass(), pk);
		return dto;
	}

	/**
	 * Finds a record using a unique key and value.
	 * 
	 * @param attribute   field name
	 * @param val         value to search
	 * @param userContext current user context
	 * @return DTO object if found
	 */
	@Override
	public T findByUniqueKey(String attribute, Object val, UserContext userContext) {
		Class<T> dtoClass = getDTOClass();

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> cq = builder.createQuery(dtoClass);

		Root<T> qRoot = cq.from(dtoClass);

		Predicate codition = builder.equal(qRoot.get(attribute), val);

		cq.where(codition);

		TypedQuery<T> query = entityManager.createQuery(cq);

		List<T> list = query.getResultList();

		T dto = null;

		if (list.size() > 0) {
			dto = list.get(0);
		}
		return dto;
	}

	/**
	 * Creates a dynamic criteria query based on DTO values.
	 * 
	 * @param dto         DTO containing search criteria
	 * @param userContext current user context
	 * @return TypedQuery object
	 */
	protected TypedQuery<T> createdCriteria(T dto, UserContext userContext) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> cq = builder.createQuery(getDTOClass());

		Root<T> qRoot = cq.from(getDTOClass());

		cq.select(qRoot);

		List<Predicate> whereClause = getWhereClause(dto, builder, qRoot);

		cq.where(whereClause.toArray(new Predicate[whereClause.size()]));

		TypedQuery<T> query = entityManager.createQuery(cq);

		return query;

	}

	/**
	 * Searches records with pagination.
	 * 
	 * @param dto         DTO containing search criteria
	 * @param pageNo      page number
	 * @param pageSize    number of records per page
	 * @param userContext current user context
	 * @return list of results
	 */
	@Override
	public List search(T dto, int pageNo, int pageSize, UserContext userContext) {

		TypedQuery<T> query = createdCriteria(dto, userContext);

		if (pageSize > 0) {
			query.setFirstResult(pageNo * pageSize);
			query.setMaxResults(pageSize);
		}

		List list = query.getResultList();
		return list;
	}

	/**
	 * Searches all records without pagination.
	 * 
	 * @param dto         DTO containing search criteria
	 * @param userContext current user context
	 * @return list of results
	 */
	@Override
	public List search(T dto, UserContext userContext) {
		return search(dto, 0, 0, userContext);
	}

	/**
	 * Retrieves top 10 records based on given HQL query. Typically used for merit
	 * list or ranking.
	 * 
	 * @param hql         HQL query string
	 * @param userContext current user context
	 * @return list of results
	 */
	public List marksheetMeritList(String hql, UserContext userContext) {
		Query q = entityManager.createQuery(hql);
		q.setFirstResult(0);
		q.setMaxResults(10);
		List l = q.getResultList();
		return l;
	}

	/**
	 * Checks if a string is null or empty.
	 * 
	 * @param val string value
	 * @return true if empty
	 */
	protected boolean isEmptyString(String val) {
		return val == null || val.trim().length() == 0;
	}

	/**
	 * Checks if Double value is null or zero.
	 * 
	 * @param val Double value
	 * @return true if zero
	 */
	protected boolean isZeroNumber(Double val) {
		return val == null || val == 0;
	}

	/**
	 * Checks if Long value is null or zero.
	 * 
	 * @param val Long value
	 * @return true if zero
	 */
	protected boolean isZeroNumber(Long val) {
		return val == null || val == 0;
	}

	/**
	 * Checks if Integer value is null or zero.
	 * 
	 * @param val Integer value
	 * @return true if zero
	 */
	protected boolean isZeroNumber(Integer val) {
		return val == null || val == 0;
	}

	/**
	 * Checks if object is not null.
	 * 
	 * @param val object
	 * @return true if not null
	 */
	protected boolean isNotNull(Object val) {
		return val != null;
	}
}
