package com.project.SmartAgenda;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.SmartAgenda.beans.Agenda;
import com.project.SmartAgenda.beans.Event;
import com.project.SmartAgenda.beans.Notification;
import com.project.SmartAgenda.beans.User;
import com.project.SmartAgenda.repositories.AgendaRepositorie;
import com.project.SmartAgenda.repositories.EventRepositorie;
import com.project.SmartAgenda.repositories.NotificationRepositorie;
import com.project.SmartAgenda.repositories.UserRepositorie;
import com.project.SmartAgenda.services.UserService;

@SpringBootApplication
public class SmartAgendaApplication implements CommandLineRunner {

	@Autowired
	AgendaRepositorie agendaRepositorie;
	@Autowired
	UserRepositorie userRepositorie;
	@Autowired
	EventRepositorie eventRepositorie;
	@Autowired
	NotificationRepositorie notificationRepositorie;
	public static void main(String[] args) {
		SpringApplication.run(SmartAgendaApplication.class, args);
		
		
		
		
	}
	@Override
	public void run(String... args) throws Exception {
		Agenda agenda=Agenda.getInstance();
		User user=new User("zaka", "zaka", "zak", "bouhi", "zakaria.bouhia@gmail.com");
		Event event1=new Event("event1", new Date(2018, 3, 7), new Date(2018,3,8), "adresse1");
		Event event2=new Event("event2", new Date(), new Date(), "adresse2");
		Notification notification1=new Notification("notification1", true, new Date());
		Notification notification2=new Notification("notification2", true, new Date());
		notification1.setEvent(event1);
		notification2.setEvent(event1);
	
		Set<Notification> notifications=new HashSet<>();
		notifications.add(notification1);
		notifications.add(notification2);
		
		event1.setNotifications(notifications);
		Set<Event> events=new HashSet<>();
		events.add(event1);
		events.add(event2);
		userRepositorie.save(user);
		eventRepositorie.saveAll(events);
		notificationRepositorie.save(notification1);
		notificationRepositorie.save(notification2);
		agenda.setUser(user);
		agenda.setEvents(events);
		agenda.setNameAgenda("Agenda1");
		agenda=agendaRepositorie.save(agenda);
	}
}
