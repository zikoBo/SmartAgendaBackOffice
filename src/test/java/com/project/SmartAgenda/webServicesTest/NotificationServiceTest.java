package com.project.SmartAgenda.webServicesTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import com.project.SmartAgenda.repositories.NotificationRepositorie;
import com.project.SmartAgenda.services.AgendaService;
import com.project.SmartAgenda.services.NotificationService;
import com.project.SmartAgenda.services.UserService;

@RunWith(SpringRunner.class)
//@WebMvcTest(value=UserServiceTest.class,secure=false)
@AutoConfigureMockMvc(secure=false)
@SpringBootTest(classes=NotificationService.class)
@ContextConfiguration(classes=SmartAgendaApplication.class)
@AutoConfigureJsonTesters
public class NotificationServiceTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	JacksonTester<Notification> jsonWriter;
	@Autowired
	ObjectMapper objectMapper;
	@MockBean
	NotificationRepositorie notificationRepositorie;

	Notification notification;

	@Before
	public void setUp() throws Exception {
		this.notification=new Notification("notificaationTest1", true, new Date());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetNotificationState() throws Exception {
		Mockito.when(notificationRepositorie.getOne(Mockito.anyInt())).thenReturn(this.notification);
		Mockito.when(notificationRepositorie.saveAndFlush(Mockito.any(Notification.class))).thenReturn(this.notification);
		RequestBuilder requestBuilder=MockMvcRequestBuilders.put("/smartAgenda/setNotificationState").param("idNotification", "1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testUpdateNotification() throws Exception {
		Mockito.when(notificationRepositorie.saveAndFlush(Mockito.any(Notification.class))).thenReturn(this.notification);
		String notificationJson=jsonWriter.write(this.notification).getJson();
		RequestBuilder requestBuilder=MockMvcRequestBuilders.put("/smartAgenda/updateNotification").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(notificationJson);
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
		
	}

}
