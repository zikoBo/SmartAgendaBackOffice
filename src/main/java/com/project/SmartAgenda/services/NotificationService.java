package com.project.SmartAgenda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartAgenda.beans.Event;
import com.project.SmartAgenda.beans.Notification;
import com.project.SmartAgenda.repositories.EventRepositorie;
import com.project.SmartAgenda.repositories.NotificationRepositorie;

@CrossOrigin("*")
@RestController
public class NotificationService {
	
	@Autowired
	NotificationRepositorie notificationRepositorie;
	@Autowired
	EventRepositorie eventRepositorie;
	
	@RequestMapping(value="/smartAgenda/setNotificationState",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setNotificationState(@RequestParam int idNotification)
	{
		//Notification notificationToSet=notificationRepositorie.findByEvent(eventRepositorie.getOne(event.getIdEvent()));
		Notification notificationToSet=notificationRepositorie.getOne(idNotification);
		notificationToSet.setState(false);
		notificationRepositorie.saveAndFlush(notificationToSet);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value="/smartAgenda/updateNotification",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateNotification(@RequestBody Notification notification)
	{
		
		notificationRepositorie.saveAndFlush(notification);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
