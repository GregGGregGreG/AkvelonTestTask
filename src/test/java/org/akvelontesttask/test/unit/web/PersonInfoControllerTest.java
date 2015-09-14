package org.akvelontesttask.test.unit.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.akvelontesttask.domain.PersonInfo;
import org.akvelontesttask.service.PersonInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:test-webApplicationContext.xml"})
@WebAppConfiguration
public class PersonInfoControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	private PersonInfoService personInfoServiceMock;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private PersonInfo getFakePersonInfo(){
		return new PersonInfo.Builder("Test", "Test")
				.age(22)
				.birthDate(new Date())
				.gender('m')
				.id(1L)
			.build();
	}
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		reset(personInfoServiceMock);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		when(personInfoServiceMock.addOrSave(any(PersonInfo.class))).thenAnswer(new Answer<PersonInfo>(){
			@Override
			public PersonInfo answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArgumentAt(0, PersonInfo.class);
			}
		});
		when(personInfoServiceMock.delete(Matchers.<List<Long>>any())).thenAnswer(new Answer<List<PersonInfo>>(){
			@Override
			public List<PersonInfo> answer(InvocationOnMock invocation) throws Throwable {
				@SuppressWarnings("unchecked")
				List<Long> ids = invocation.getArgumentAt(0, List.class);
				List<PersonInfo> pis = new ArrayList<>();
				for(Long id : ids){
					PersonInfo pi = getFakePersonInfo();
					pi.setId(id);
					pis.add(pi);
				}
				return pis;
			}
		});
		when(personInfoServiceMock.load(any(Long.class))).thenAnswer(new Answer<PersonInfo>(){
			@Override
			public PersonInfo answer(InvocationOnMock invocation) throws Throwable {
				PersonInfo pi = getFakePersonInfo();
				pi.setId(invocation.getArgumentAt(0, Long.class));
				return pi;
			}
		});
		when(personInfoServiceMock.load(any(PersonInfo.class))).thenAnswer(new Answer<PersonInfo>(){
			@Override
			public PersonInfo answer(InvocationOnMock invocation) throws Throwable {
				PersonInfo pi = invocation.getArgumentAt(0, PersonInfo.class);
				return pi;
			}
		});
		when(personInfoServiceMock.loadAll()).thenReturn(Arrays.asList(getFakePersonInfo()));
	}
	
	@Test
	public void testGetMainPage() throws Exception{
		
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(model().size(2))
				.andExpect(model().attributeExists("checkBoxes"))
				.andExpect(model().attributeExists("personInfoList"))
				.andExpect(model().attribute("personInfoList", hasSize(1)));
		
		verify(personInfoServiceMock, times(1)).loadAll();
        verifyNoMoreInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testProceedToEditPage() throws Exception{
		mockMvc.perform(post("/edit").param("checkedIds", "1"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("1/edit"));
		verifyZeroInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testProceedToAddPage() throws Exception{
		mockMvc.perform(post("/edit"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("add"));
		verifyZeroInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testProceedToEditOrAddMultiplyCheckboxesFound() throws Exception{
		mockMvc.perform(post("/edit")
					.param("checkedIds", "1")
					.param("checkedIds", "2")
				)
				.andExpect(status().isFound())
				.andExpect(flash().attributeCount(1))
				.andExpect(flash().attributeExists("errorMsg"))
				.andExpect(redirectedUrl("/"));
		verifyZeroInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testGetEditPageForEdit() throws Exception{
		mockMvc.perform(get("/1/edit"))
				.andExpect(status().isOk())
				.andExpect(model().size(2))
				.andExpect(model().attributeExists("personInfoForm"))
				.andExpect(model().attributeExists("title"));
		verify(personInfoServiceMock, times(1)).load(1L);
		verifyNoMoreInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testUpdatePersonInfo() throws Exception{
		PersonInfo pi = getFakePersonInfo();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse("1998-09-19");
		pi.setBirthDate(date);
		String dDate = df.format(date);
		mockMvc.perform(post("/1/save")
					.param("firstName", "Test")
					.param("lastName", "Test")
					.param("birthDate", dDate)
					.param("age", "22")
					.param("gender", "m")
				)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
		verify(personInfoServiceMock, times(1)).addOrSave(pi);
		verifyNoMoreInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testUpdatePersonInfoValidationFails() throws Exception {
		mockMvc.perform(post("/1/save")
					.param("firstName", "test")
					.param("lastName", "Test")
					.param("birthDate", "1998-09-19")
					.param("age", "22")
					.param("gender", "m")
				)
				.andExpect(status().isFound())
				.andExpect(flash().attributeCount(2))
				.andExpect(flash().attributeExists("personInfoForm"))
				.andExpect(flash().attributeExists("org.springframework.validation.BindingResult.personInfoForm"))
				.andExpect(redirectedUrl("edit"));
		verifyZeroInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testGetEditPageForAdd() throws Exception {
		mockMvc.perform(get("/add"))
				.andExpect(status().isOk())
				.andExpect(model().size(2))
				.andExpect(model().attributeExists("personInfoForm"))
				.andExpect(model().attributeExists("title"));
		verifyZeroInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testAddNewPersonInfo() throws Exception{
		PersonInfo pi = getFakePersonInfo();
		pi.setId(null);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse("1998-09-19");
		pi.setBirthDate(date);
		String dDate = df.format(date);
		mockMvc.perform(post("/add")
					.param("firstName", "Test")
					.param("lastName", "Test")
					.param("birthDate", dDate)
					.param("age", "22")
					.param("gender", "m")
				)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
		verify(personInfoServiceMock, times(1)).addOrSave(pi);
		verifyNoMoreInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testAddNewPersonInfoValidationFails() throws Exception {
		mockMvc.perform(post("/add")
					.param("firstName", "test")
					.param("lastName", "Test")
					.param("birthDate", "1998-09-19")
					.param("age", "22")
					.param("gender", "m")
				)
				.andExpect(status().isFound())
				.andExpect(flash().attributeCount(2))
				.andExpect(flash().attributeExists("personInfoForm"))
				.andExpect(flash().attributeExists("org.springframework.validation.BindingResult.personInfoForm"))
				.andExpect(redirectedUrl("add"));
		verifyZeroInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testDeleteSelected() throws Exception{
		mockMvc.perform(post("/delete")
					.param("checkedIds", "1")
				)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
		verify(personInfoServiceMock, times(1)).delete(Arrays.asList(1L));
		verifyNoMoreInteractions(personInfoServiceMock);
	}
	
	@Test
	public void testProceedCancel() throws Exception{
		mockMvc.perform(post("/1/cancel"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
		
		mockMvc.perform(post("/cancel"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/"));
	}
	
	
	
	
}
