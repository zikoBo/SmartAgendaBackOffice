package com.project.SmartAgenda;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SmartAgenda.SmartAgendaApplication;
import com.project.SmartAgenda.beans.Agenda;
import com.project.SmartAgenda.beans.User;
import com.project.SmartAgenda.repositories.AgendaRepositorie;
import com.project.SmartAgenda.repositories.UserRepositorie;
import com.project.SmartAgenda.services.UserService;

@RunWith(SpringRunner.class)
//@WebMvcTest(value=UserServiceTest.class,secure=false)
@AutoConfigureMockMvc(secure=false)
@SpringBootTest(classes=UserService.class)
@ContextConfiguration(classes=SmartAgendaApplication.class)
@AutoConfigureJsonTesters
public class UserServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	JacksonTester< User> jsonWriter;
	@Autowired
	ObjectMapper objectMapper;
	@MockBean
	private UserRepositorie userRepositorie;
	@MockBean
	private AgendaRepositorie agendaRepositorie;
	
	
	User userfound;
	Agenda agenda=Agenda.getInstance();

	@Before
	public void setUp() throws Exception {
		this.userfound=new User(1,"login1", "password1", "firstName1", "lastName1", "email1");
		this.agenda.setUser(new User(2, "za", "za", "za", "za", "za"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAuthentication() throws Exception {
		Mockito.when(userRepositorie.authentication(Mockito.anyString(), Mockito.anyString())).thenReturn(this.userfound);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/smartAgenda/authentication").param("login", "jjj").param("password", "jjj").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		System.out.println("resulat : "+result.getResponse().getContentAsString());
		String expected=("{idUser:1,login:login1,password:password1,firstName:firstName1,lastName:lastName1,email:email1}");
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	
	
	@Test
	public void testDeleteAcount() throws Exception {
		Mockito.when(userRepositorie.getOne(Mockito.anyInt())).thenReturn(userfound);
		Mockito.when(agendaRepositorie.findByUser(Mockito.any())).thenReturn(Agenda.getInstance());
		//Mockito.when(userService.deleteAcount(Mockito.anyInt())).thenCallRealMethod();
		RequestBuilder requestBuilder=MockMvcRequestBuilders.delete("/smartAgenda/deleteAccount").param("idUser", "1");
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		System.out.println("resulat : "+result.getResponse().getStatus());
		System.out.println("resulat : "+result.getResponse().getContentAsString());
		//String expected=("{idUser:1,login:login1,password:password1,firstName:firstName1,lastName:lastName1,email:email1}");
		//assertEquals(HttpStatus.MOVED_PERMANENTLY, result.getResponse().getStatus(), false);	
		//this.agenda.getUser();
		assertEquals(HttpStatus.MOVED_PERMANENTLY.value(), result.getResponse().getStatus());
		assertEquals(true, Boolean.parseBoolean(result.getResponse().getContentAsString()));
		}

	@Test
	public void testDisplay() throws Exception {
		Mockito.when(userRepositorie.getOne(Mockito.anyInt())).thenReturn(userfound);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/smartAgenda/display").param("idUser", "1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		String expected=("{idUser:1,login:login1,password:password1,firstName:firstName1,lastName:lastName1,email:email1}");
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		assertEquals(HttpStatus.FOUND.value(), result.getResponse().getStatus());
	}

	
	@Test
	public void testUpdateUser() throws Exception {
		String userJson=jsonWriter.write(userfound).getJson();
		RequestBuilder requestBuilder=MockMvcRequestBuilders.put("/smartAgenda/update").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(userJson);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
	}

	@Ignore
	@Test
	public void testUpdateString() throws Exception {
		Mockito.when(userRepositorie.findByIdUser(Mockito.anyInt())).thenReturn(userfound);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.put("/smartAgenda/resetPassword").param("idUser", "1").param("new Password", "nouveau").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
	}

}
