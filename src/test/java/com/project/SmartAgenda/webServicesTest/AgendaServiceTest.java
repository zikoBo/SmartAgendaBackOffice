package com.project.SmartAgenda.webServicesTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.project.SmartAgenda.beans.Event;
import com.project.SmartAgenda.beans.User;
import com.project.SmartAgenda.repositories.AgendaRepositorie;
import com.project.SmartAgenda.repositories.EventRepositorie;
import com.project.SmartAgenda.services.AgendaService;
import com.project.SmartAgenda.services.EventService;
import com.project.SmartAgenda.services.NotificationService;


@RunWith(SpringRunner.class)
//@WebMvcTest(value=UserServiceTest.class,secure=false)
@AutoConfigureMockMvc(secure=false)
@SpringBootTest(classes=AgendaService.class)
@ContextConfiguration(classes=SmartAgendaApplication.class)
@AutoConfigureJsonTesters
public class AgendaServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	JacksonTester<Event> jsonWriter;
	@Autowired
	ObjectMapper objectMapper;
//	@MockBean
//	AgendaService agendaService;
	@MockBean
	EventRepositorie eventRepositorie;
	@MockBean
	AgendaRepositorie agendaRepositorie;

	Event event;
	Set<Event> events;
	@Before
	public void setUp() throws Exception {
		this.event=new Event("event1", null, null, "adresse1");
		this.event.setIdEvent(2);
		this.event.setAgenda(Agenda.getInstance());
		this. events=new HashSet<>();
		this.events.add(event);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddEvent() throws Exception {
		//Mockito.when(agendaService.addEvent(Mockito.any())).thenCallRealMethod();
		Mockito.when(eventRepositorie.save(Mockito.any())).thenReturn(this.event);
		Mockito.when(agendaRepositorie.saveAndFlush(Mockito.any())).thenReturn(Agenda.getInstance());
		String eventJson=jsonWriter.write(event).getJson();
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post("/smartAgenda/addEvent").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(eventJson);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
	}

	
	@Test
	public void testSearchForAnEvent() throws Exception {
//		Mockito.when(eventRepositorie.searchForAnEvent(Mockito.anyString(), Mockito.any(Agenda.class))).thenReturn(events);
//		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/smartAgenda/searchForAnEvent").param("eventName", "aa").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON);
//		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
//		String expected="{idEvent:2,name:event1,dateStart:12-03-2018,dateEnd:12-03-2018,adresse:adresse1}";
//		//assertEquals(expected, result.getResponse().getContentAsString());
//		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//		assertEquals(HttpStatus.FOUND.value(), result.getResponse().getStatus());
		Mockito.when(eventRepositorie.searchForAnEvent(Mockito.anyString(), Mockito.any(Agenda.class))).thenReturn(this.events);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.get("/smartAgenda/searchForAnEvent").param("eventName", "jjj").accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType("application/json");
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		System.out.println("resulat : "+result.getResponse().getContentAsString());
		String expected="[{idEvent:2,name:event1,dateStart:null,dateEnd:null,adresse:adresse1}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	
	@Test
	public void testUpdateAnEvent() throws Exception {
		Mockito.when(agendaRepositorie.saveAndFlush(Mockito.any())).thenReturn(event);
		String eventJson=jsonWriter.write(event).getJson();
		RequestBuilder requestBuilder=MockMvcRequestBuilders.put("/smartAgenda/updateAnEvent").accept(MediaType.APPLICATION_JSON_VALUE).contentType("application/json").contentType(MediaType.APPLICATION_JSON).content(eventJson);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		String expected="{idEvent:2,name:event1,dateStart:12-03-2018,dateEnd:12-03-2018,adresse:adresse1}";
		System.out.println("resulatat : "+result.getResponse().getContentAsString());
		//assertEquals(HttpStatus.ACCEPTED.value(), result.getResponse().getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		
	}

}
