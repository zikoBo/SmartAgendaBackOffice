package com.project.SmartAgenda.beans;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idNotification;
	private String title;
	private boolean state;
	@Temporal(TemporalType.DATE)
	private Date dateOfNotification;
	@ManyToOne
	private Event event;
	
	public Notification() {
		super();
	}
	public Notification(String title, boolean state,Date dateOfNotification) {
		super();
		this.title = title;
		this.state = state;
		this.dateOfNotification=dateOfNotification;
	}
	
	public Notification(String title, boolean state,Date dateOfNotification, Event event) {
		super();
		this.title = title;
		this.state = state;
		this.event = event;
		this.dateOfNotification=dateOfNotification;
	}
	public int getIdNotification() {
		return idNotification;
	}
	public void setIdNotification(int idNotification) {
		this.idNotification = idNotification;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Date getDateOfNotification() {
		return dateOfNotification;
	}
	public void setDateOfNotification(Date dateOfNotification) {
		this.dateOfNotification = dateOfNotification;
	}
	
	

}
