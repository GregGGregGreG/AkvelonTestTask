package org.akvelontesttask.dao;

import java.util.List;

/**
 * Generic DAO which holds main persistence operations on objects
 * @author baddev
 *
 * @param <T> type of target object
 * @param <ID> identifier type of target object 
 */
public interface GenericDao<T, ID> {

	/**
	 * Makes entity persistent or updates existing persistent entity
	 * @param entity to become persistent
	 * @return persisted/updated entity
	 */
	T makePersistentOrUpdate(T entity);

	/**
	 * Finds and returns list of all entities
	 * @return list of entities
	 */
	List<T> findAll();

	/**
	 * Finds entity by identifier
	 * @param id is an identifier of entity to load
	 * @return persistent entity
	 */
	T findById(ID id);

	/**
	 * Looks for entities which are similar to <code>exampleEntity</code>.
	 * <p>
	 * Null valued fields of <code>exampleEntity</code> will be excluded from search criteria.
	 * @param exampleEntity
	 * @return list of found entities
	 */
	List<T> findByExample(T exampleEntity);

	/**
	 * Makes persistent entity transient
	 * @param persistentEntity is an entity which we want to remove
	 * @return transient entity
	 */
	T makeTransient(T persistentEntity);
}
