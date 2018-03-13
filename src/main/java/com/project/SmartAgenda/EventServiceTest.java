package com.project.SmartAgenda;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.project.SmartAgenda.beans.Event;
import com.project.SmartAgenda.beans.Notification;
import com.project.SmartAgenda.repositories.EventRepositorie;
import com.project.SmartAgenda.repositories.NotificationRepositorie;
import com.project.SmartAgenda.services.AgendaService;
import com.project.SmartAgenda.services.EventService;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure=false)
@SpringBootTest(classes=EventService.class)
@ContextConfiguration(classes=SmartAgendaApplication.class)
@AutoConfigureJsonTesters
public class EventServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	JacksonTester<Event> jsonWriter;
	@Autowired
	ObjectMapper objectMapper;
	@MockBean
	EventRepositorie eventRepositorie;
	@MockBean 
	NotificationRepositorie notificationRepositorie;
	@MockBean
	EventService eventService;
	Event event;
	Notification notification;
	Set<Notification> notifications=new HashSet<>();
	@Before
	public void setUp() throws Exception {
		 event=new Event("event1", new Date(), new Date(), "adresse1");
		event.setIdEvent(2);
		notification=new Notification("notif1", true, new Date());
		notification.setIdNotification(3);
		notifications.add(notification);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddNotificationToEvent() throws Exception {
		//Mockito.when(eventService.addNotificationToEvent(Mockito.any(Date.class), Mockito.anyInt())).thenCallRealMethod();
		Mockito.when(eventRepositorie.findByIdEvent(Mockito.anyInt())).thenReturn(this.event);
		Mockito.when(notificationRepositorie.save(Mockito.any())).thenReturn(this.notification);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.post("/smartAgenda/addNotificationToEvent").param("dateOfNotification", "24/03/1994").param("idEvent", "2").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		System.out.println("resulat : "+result.getResponse().getContentAsString());
		//String expected=("{idUser:1,login:login1,password:password1,firstName:firstName1,lastName:lastName1,email:email1}");
		//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
		
	}

	
	@Test
	public void testRemoveNotificationFromEvent() throws Exception {
		Mockito.when(notificationRepositorie.findByDateOfNotification(Mockito.any(Date.class))).thenReturn(notifications);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.delete("/smartAgenda/removeNotification").param("dateOfNotification", "1994/03/24").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		
	}

	@Ignore
	@Test
	public void testGetEstimatedTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisplayEventInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisplayEventEvent() {
		fail("Not yet implemented");
	}

}
