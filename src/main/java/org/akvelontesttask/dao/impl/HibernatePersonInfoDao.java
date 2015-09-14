package org.akvelontesttask.dao.impl;

import org.akvelontesttask.dao.PersonInfoDao;
import org.akvelontesttask.domain.PersonInfo;
import org.springframework.stereotype.Repository;

/**
 * Default implementation of {@link PersonInfoDao}
 * @author baddev
 * @see {@link org.akvelontesttask.dao.GenericDao}
 */
@Repository("personInfoDao")
public final class HibernatePersonInfoDao extends HibernateGenericDao<PersonInfo, Long>
		implements PersonInfoDao {

	public HibernatePersonInfoDao() {
		super(PersonInfo.class);
	}
}
