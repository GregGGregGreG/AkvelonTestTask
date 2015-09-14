package org.akvelontesttask.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.akvelontesttask.dao.PersonInfoDao;
import org.akvelontesttask.domain.PersonInfo;
import org.akvelontesttask.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link PersonInfoService}
 * @author baddev
 */

@Service("personInfoService")
public final class PersonInfoServiceImpl implements PersonInfoService{

	@Autowired
	private PersonInfoDao personInfoDao;
	
	public PersonInfoServiceImpl() {}
	
	public PersonInfoServiceImpl(final PersonInfoDao personInfoDao){
		this.personInfoDao = personInfoDao;
	}
	
	@Override
	public List<PersonInfo> loadAll() {
		return personInfoDao.findAll();
	}

	@Override
	public PersonInfo addOrSave(final PersonInfo personInfo) {
		return personInfoDao.makePersistentOrUpdate(personInfo);
	}

	@Override
	public PersonInfo load(final PersonInfo example) {
		List<PersonInfo> foundMatching = personInfoDao.findByExample(example);
		return foundMatching.get(0);
	}

/*	@Override
	public PersonInfo delete(final Long id) {
		PersonInfo pi = personInfoDao.findById(id);
		if(pi!=null){
			personInfoDao.makeTransient(pi);
		}
		return pi;
	}

	@Override
	public List<PersonInfo> deleteAll() {
		List<PersonInfo> all = personInfoDao.findAll();
		for(PersonInfo pi : all){
			personInfoDao.makeTransient(pi);
		}
		return all;
	}*/

	@Override
	public List<PersonInfo> delete(final List<Long> persInfIds) {
		List<PersonInfo> loaded = new ArrayList<>();
		PersonInfo temp = null;
		for (Long id : persInfIds) {
			temp = personInfoDao.findById(id);
			if(temp!=null)
				loaded.add(temp);
		}
		for (PersonInfo personInfo : loaded) {
			personInfoDao.makeTransient(personInfo);
		}
		return loaded;
	}

	@Override
	public PersonInfo load(Long id) {
		return personInfoDao.findById(id);
	}

}
