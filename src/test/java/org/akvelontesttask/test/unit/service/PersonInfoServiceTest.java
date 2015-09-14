package org.akvelontesttask.test.unit.service;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.akvelontesttask.dao.PersonInfoDao;
import org.akvelontesttask.domain.PersonInfo;
import org.akvelontesttask.service.PersonInfoService;
import org.akvelontesttask.service.impl.PersonInfoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@RunWith(BlockJUnit4ClassRunner.class)
public class PersonInfoServiceTest {

	@Mock
	private PersonInfoDao personInfoDao;
	
	@InjectMocks
	private PersonInfoService personInfoService = new PersonInfoServiceImpl();
	
	private PersonInfo getFakePersonInfo(){
		return new PersonInfo.Builder("test", "test")
				.age(22)
				.birthDate(new Date())
				.gender('m')
			.build();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		//stubbing dao methods
		when(personInfoDao.makePersistentOrUpdate(any(PersonInfo.class))).thenAnswer(new Answer<PersonInfo>() {
			@Override
			public PersonInfo answer(InvocationOnMock invocation) throws Throwable {
				PersonInfo pi = invocation.getArgumentAt(0, PersonInfo.class);
				pi.setId(1L);
				return pi;
			}
		});
		when(personInfoDao.findById(any(Long.class))).thenAnswer(new Answer<PersonInfo>(){
			@Override
			public PersonInfo answer(InvocationOnMock invocation) throws Throwable {
				Long id = invocation.getArgumentAt(0, Long.class);
				PersonInfo pi = getFakePersonInfo();
				pi.setId(id);
				return pi;
			}
		});
		when(personInfoDao.findAll()).thenAnswer(new Answer<List<PersonInfo>>(){
			@Override
			public List<PersonInfo> answer(InvocationOnMock invocation) throws Throwable {
				PersonInfo pi = getFakePersonInfo();
				pi.setId(1L);
				return Arrays.asList(pi);
			}
		});
		when(personInfoDao.findByExample(any(PersonInfo.class))).thenAnswer(new Answer<List<PersonInfo>>() {
			@Override
			public List<PersonInfo> answer(InvocationOnMock invocation) throws Throwable {
				PersonInfo pi = invocation.getArgumentAt(0, PersonInfo.class);
				pi.setId(1L);
				return Arrays.asList(pi);
			}
		});
		when(personInfoDao.makeTransient(any(PersonInfo.class))).thenAnswer(new Answer<PersonInfo>(){
			@Override
			public PersonInfo answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArgumentAt(0, PersonInfo.class);
			}
			
		});
	}

	@Test
	public void testAddOrSave() {
		PersonInfo expected = getFakePersonInfo();
		assertNull(expected.getId());
		PersonInfo actual = personInfoService.addOrSave(expected);
		assertNotNull(actual.getId());
		assertSame(expected, actual);
	}

	@Test
	public void testLoadAll() {
		List<PersonInfo> all = personInfoService.loadAll();
		assertNotNull(all);
		assertFalse(all.isEmpty());
	}

	@Test
	public void testLoadExact() {
		PersonInfo expected = getFakePersonInfo();
		assertNull(expected.getId());
		PersonInfo actual  = personInfoService.load(expected);
		assertNotNull(actual);
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getAge(), actual.getAge());
		assertEquals(expected.getBirthDate(), actual.getBirthDate());
		assertEquals(expected.getGender(), actual.getGender());
	}
	
	@Test
	public void testLoadById() {
		long expected = 1;
		PersonInfo pi = personInfoService.load(expected);
		assertNotNull(pi);
		assertTrue((long)expected==(long)pi.getId());
	}

	/*@Test
	public void testDelete() {
		Long expected = 1L;
		PersonInfo actual = personInfoService.delete(expected);
		assertNotNull(actual);
		assertSame(expected, actual.getId());
	}*/
	
	@Test
	public void testDeleteSeveral(){
		List<Long> expected = Arrays.asList(1L,2L,3L);
		List<PersonInfo> actual = personInfoService.delete(expected);
		assertNotNull(actual);
		assertFalse(actual.isEmpty());
	}
	
	/*@Test
	public void testDeleteAll(){
		List<PersonInfo> deleted = personInfoService.deleteAll();
		assertNotNull(deleted);
		assertFalse(deleted.isEmpty());
	}*/

}
