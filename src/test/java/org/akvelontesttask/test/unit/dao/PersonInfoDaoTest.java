package org.akvelontesttask.test.unit.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.akvelontesttask.dao.PersonInfoDao;
import org.akvelontesttask.domain.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * This is a test class for PersonInfoDao. 
 * Used in-memory db to perform test tasks.
 * @author baddev
 * 
 */
@ContextConfiguration(locations="classpath:test-applicationContext-dao.xml")
public class PersonInfoDaoTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private PersonInfoDao personInfoDao;
	
	private PersonInfo getDummyPersonInfo(){
		return new PersonInfo.Builder("test", "test")
				.age(22)
				.birthDate(new Date())
				.gender('m')
			.build();
	}
	
	private int countRows(){
		return countRowsInTable("person_info");
	}
	
	private void checkIfDbEmpty(){
		assertEquals(0, countRows());
	}
	
	@Test
	public void testMakePersistentOrUpdate(){
		checkIfDbEmpty();
		PersonInfo pi = getDummyPersonInfo();
		personInfoDao.makePersistentOrUpdate(pi);
		assertEquals(1, countRows());
		assertEquals(pi, personInfoDao.findById(pi.getId()));
	}
	
	@Test
	public void testFindAll(){
		checkIfDbEmpty();
		PersonInfo pi1 = getDummyPersonInfo();
		PersonInfo pi2 = getDummyPersonInfo();
		personInfoDao.makePersistentOrUpdate(pi1);
		personInfoDao.makePersistentOrUpdate(pi2);
		assertEquals(2, countRows());
		List<PersonInfo> all = personInfoDao.findAll();
		assertTrue("Check if loaded list contains pi1", all.contains(pi1));
		assertTrue("Check if loaded list contains pi2" ,all.contains(pi2));
	}
	
	@Test
	public void testFindById(){
		checkIfDbEmpty();
		PersonInfo pi = getDummyPersonInfo();
		personInfoDao.makePersistentOrUpdate(pi);
		assertEquals(1, countRows());
		assertNotNull("Check if pi was found by id" ,personInfoDao.findById(pi.getId()));
	}
	
	@Test
	public void testFindByExample(){
		checkIfDbEmpty();
		personInfoDao.makePersistentOrUpdate(getDummyPersonInfo());
		assertEquals(1, countRows());
		assertNotNull("Check if object was found by example", personInfoDao.findByExample(getDummyPersonInfo()));
	}
	
	@Test
	public void testMakeTransient(){
		checkIfDbEmpty();
		PersonInfo pi = getDummyPersonInfo();
		personInfoDao.makePersistentOrUpdate(pi);
		assertEquals(1, countRows());
		personInfoDao.makeTransient(pi);
		//using findAll() method instead of countRows() because data in db marked to be deleted
		//and will be deleted in the end of transaction, but our transaction will roll back
		assertEquals(0, personInfoDao.findAll().size());
	}
	
}
