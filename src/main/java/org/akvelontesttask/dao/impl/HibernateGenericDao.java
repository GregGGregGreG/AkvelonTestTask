package org.akvelontesttask.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.akvelontesttask.dao.GenericDao;
import org.akvelontesttask.domain.IEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Default, underlying implementation of basic data access operations.
 * @author baddev
 *
 * @param <T> target type
 * @param <ID> type of identifier of target type
 * @see {@link GenericDao}
 */
public abstract class HibernateGenericDao<T extends IEntity<ID>, ID extends Serializable> implements GenericDao<T, ID> {

	private Class<T> persistentClass;

	@Autowired
	private SessionFactory sessionFactory;

	// all subclasses must proceed actual type of entity
	public HibernateGenericDao(Class<T> persistentClazz) {
		this.persistentClass = persistentClazz;
	}

	protected Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public T makePersistentOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Override
	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(final ID id) {
		return (T) getSession().get(getPersistentClass(), id);
	}

	@Override
	public List<T> findByExample(final T exampleEntity) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		// prevent duplicates which are expected by using criteria with
		// joins(eager fetch) on entities
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Example example = Example.create(exampleEntity);
		criteria.add(example);
		// exclude all null and zero properties from matching
		example.excludeZeroes();

		@SuppressWarnings("unchecked")
		List<T> entities = (List<T>) criteria.list();

		if (entities.isEmpty()) {
			return new ArrayList<T>();
		}
		return entities;
	}

	@Override
	public T makeTransient(T persistentEntity) {
		getSession().delete(persistentEntity);
		return persistentEntity;
	}

	protected List<T> findByCriteria(final Criterion... criterion) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		// prevents duplicates which are expected by using criteria with
		// joins(eager fetch) on entities
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if (criterion.length > 0) {
			for (Criterion c : criterion) {
				criteria.add(c);
			}
		}

		@SuppressWarnings("unchecked")
		List<T> entities = (List<T>) criteria.list();

		if (entities.isEmpty()) {
			return new ArrayList<T>();
		}

		return entities;
	}

}
