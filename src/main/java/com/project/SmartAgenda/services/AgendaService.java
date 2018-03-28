package com.project.SmartAgenda.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.SmartAgenda.SmartAgendaApplication;
import com.project.SmartAgenda.beans.Agenda;
import com.project.SmartAgenda.beans.Event;
import com.project.SmartAgenda.repositories.AgendaRepositorie;
import com.project.SmartAgenda.repositories.EventRepositorie;

@CrossOrigin("*")
@RestController
public class AgendaService {
	@Autowired
	AgendaRepositorie agendaRepositorie;
	@Autowired
	EventRepositorie eventRepositorie;
	
	@RequestMapping(value="/smartAgenda/addEvent",method=RequestMethod.POST,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces="application/json")
	public ResponseEntity<?> addEvent(@RequestBody Event event)
	{
		event.setAgenda(Agenda.getInstance());
		agendaRepositorie.save(Agenda.getInstance());
		event=eventRepositorie.save(event);			
		Agenda.getInstance().getEvents().add(event);
		agendaRepositorie.saveAndFlush(Agenda.getInstance());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/smartAgenda/searchForAnEvent",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE,produces="application/json")
	public ResponseEntity<?> searchForAnEvent(@RequestParam(name="eventName") String eventName)
	{
		
		Set<Event> eventsFound=eventRepositorie.searchForAnEvent(eventName, Agenda.getInstance());
		if(eventsFound.size()!=0 && eventsFound!=null)
			return new ResponseEntity<Set<Event>>(eventsFound,HttpStatus.FOUND);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@RequestMapping(value="/smartAgenda/updateAnEvent",method=RequestMethod.PUT,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces="application/json")
	public ResponseEntity<?> updateAnEvent(@RequestBody Event event)
	{
		try{
			event.setAgenda(Agenda.getInstance());
			event=eventRepositorie.saveAndFlush(event);
			return new ResponseEntity<Event>(event,HttpStatus.OK);
		}catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		
	}
	
	@RequestMapping(value="/smartAgenda/ordonanceEvents",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces="application/json")
	public ResponseEntity<?> ordanaceEvents()
	{
		List<Event> eventsSorted=eventRepositorie.findAll();
		Collections.sort(eventsSorted, new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}	
		});
		return new ResponseEntity<List<Event>>(eventsSorted,HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value="/smartAgenda/ordonanceEventsForDay",method=RequestMethod.GET,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE,produces="application/json")
	public ResponseEntity<?> ordanaceEventsForDay(@RequestParam("date") Date date)
	{
		List<Event> eventsSorted=eventRepositorie.findByStartTime(date);
		Collections.sort(eventsSorted, new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}	
		});
		return new ResponseEntity<List<Event>>(eventsSorted,HttpStatus.ACCEPTED);
	}
}
