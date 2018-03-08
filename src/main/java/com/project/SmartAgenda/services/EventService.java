package com.project.SmartAgenda.services;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
public class EventService {

	@Autowired
	EventRepositorie eventRepositorie;
	
	@Autowired
	NotificationRepositorie notificationRepositorie;
	
	//TODO ajouter un attribut date à la classe notification et ajouter le comme params de la fonctionne
	
	@RequestMapping(value="/smartAgenda/updateEvent",method=RequestMethod.POST,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNotificationToEvent(@RequestParam Date dateOfNotification,@RequestParam int idevent)
	{
		try{
		Notification notificationToAdd=new Notification(eventRepositorie.findById(idevent).get().getName(), true, dateOfNotification);
		notificationToAdd.setEvent(eventRepositorie.findById(idevent).get());
		notificationRepositorie.save(notificationToAdd);
		Event eventToUpdate=eventRepositorie.findById(idevent).get();
		Set<Notification> notifications=eventToUpdate.getNotifications();
		notifications.add(notificationToAdd);
		eventToUpdate.setNotifications(notifications);
		eventRepositorie.saveAndFlush(eventToUpdate);
		return new ResponseEntity<>(HttpStatus.CREATED);
		}catch(Exception e )
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		
	}
	
	@RequestMapping(value="/smartAgenda/removeNotification",method=RequestMethod.DELETE,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> removeNotificationFromEvent(@RequestParam Date dateOfNotification)
	{
		Set<Notification> notifications=notificationRepositorie.findByDateOfNotification(dateOfNotification);
		for(Notification notification:notifications)
			notificationRepositorie.delete(notification);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/smartAgenda/getEstimatedTime",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEstimatedTime(@RequestParam int idEvent)
	{
		Event eventToCompute=eventRepositorie.findById(idEvent).get();
		long dateStart=eventToCompute.getDateStart().getTime();
		long dateEnd=eventToCompute.getDateEnd().getTime();
		long estimatedTime=dateEnd-dateStart;
		
		/*
		 * calcule de durée
		 */
		long secondInMilis=1000;
		long minuteInMilis=secondInMilis*60;
		long hourInMilis=minuteInMilis*60;
		long dayInMilis=hourInMilis*24;
		long diffDays=estimatedTime/dayInMilis;
		estimatedTime=estimatedTime%dayInMilis;
		long diffHours=estimatedTime/hourInMilis;
		estimatedTime=estimatedTime%hourInMilis;
		long diffMinutes=estimatedTime/minuteInMilis;
		estimatedTime=estimatedTime%minuteInMilis;
		long diffSeconds=estimatedTime/secondInMilis;
		String estimatedDuration=diffDays+" J "+diffHours+" H "+diffMinutes+" M "+diffSeconds+" S ";
		return new ResponseEntity<String>(estimatedDuration,HttpStatus.OK);
	}
	
	//TODO gestBestDepartueTime
	
	@RequestMapping(value="/smartAgenda/displayEvent",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> displayEvent(@RequestParam int idEvent)
	{
		Event eventToDisplay=eventRepositorie.findById(idEvent).get();
		if(eventToDisplay==null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Event>(eventToDisplay,HttpStatus.FOUND);
	}
	
	@RequestMapping(value="/smartAgenda/updateevent",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> displayEvent(@RequestBody Event event)
	{
		eventRepositorie.saveAndFlush(event);
		return new ResponseEntity<Event>(event,HttpStatus.OK);
	}
	
	//TODO getForecast
}

