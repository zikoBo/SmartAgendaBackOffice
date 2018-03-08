package com.project.SmartAgenda.repositories;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.SmartAgenda.beans.Event;
import com.project.SmartAgenda.beans.Notification;

public interface NotificationRepositorie extends JpaRepository<Notification, Integer> {
	
	//@Query("FROM Notification n WHERE n.dateOfNotification=:dateOfNotification")
	public Set<Notification> findByDateOfNotification(Date dateOfNotification);
	public Notification findByEvent(Event event);

}
