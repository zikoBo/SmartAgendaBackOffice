package com.project.SmartAgenda.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEvent;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date dateStart;
	@Temporal(TemporalType.DATE)
	private Date dateEnd;
	private String adresse;
	@Enumerated(EnumType.STRING)
	private Tag tag;
	@Enumerated(EnumType.STRING)
	private Transport transport;
	@Enumerated(EnumType.STRING)
	private Weather weather;
	@JsonIgnore
	@OneToMany(mappedBy="event")
	private Set<Notification> notifications;
	@JsonIgnore
	@ManyToOne
	private Agenda agenda;
	
	public Event() {
		super();
	}

	public Event(String name, Date dateStart, Date dateEnd, String adresse) {
		super();
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.adresse = adresse;
	}
	
	public Event(String name, Date dateStart, Date dateEnd, String adresse, Tag tag, Transport transport,
			Weather weather, Set<Notification> notifications,Agenda agenda) {
		super();
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.adresse = adresse;
		this.tag = tag;
		this.transport = transport;
		this.weather = weather;
		this.notifications = notifications;
		this.agenda=agenda;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result + ((agenda == null) ? 0 : agenda.hashCode());
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + idEvent;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notifications == null) ? 0 : notifications.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((transport == null) ? 0 : transport.hashCode());
		result = prime * result + ((weather == null) ? 0 : weather.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (agenda == null) {
			if (other.agenda != null)
				return false;
		} else if (!agenda.equals(other.agenda))
			return false;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		if (idEvent != other.idEvent)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notifications == null) {
			if (other.notifications != null)
				return false;
		} else if (!notifications.equals(other.notifications))
			return false;
		if (tag != other.tag)
			return false;
		if (transport != other.transport)
			return false;
		if (weather != other.weather)
			return false;
		return true;
	}
	
	
	
}
