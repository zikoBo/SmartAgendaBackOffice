package com.project.SmartAgenda.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Agenda implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAgenda;
	private String nameAgenda;
	private static Agenda agenda=new Agenda();
	@JsonIgnore
	@OneToOne
	private User user;
	@JsonIgnore
	@OneToMany(mappedBy="agenda",cascade=CascadeType.REMOVE)
	private Set<Event> events;
	
	
	public static Agenda getInstance()
	{
		
			if(agenda==null)
			agenda=new Agenda();
		return agenda;
	}
	
	private Agenda()
	{
		
	}

	public int getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(int idAgenda) {
		this.idAgenda = idAgenda;
	}

	public String getNameAgenda() {
		return nameAgenda;
	}

	public void setNameAgenda(String nameAgenda) {
		this.nameAgenda = nameAgenda;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

		
}
