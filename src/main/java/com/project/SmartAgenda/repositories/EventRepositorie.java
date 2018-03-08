package com.project.SmartAgenda.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.SmartAgenda.beans.Agenda;
import com.project.SmartAgenda.beans.Event;

public interface EventRepositorie extends JpaRepository<Event, Integer> {

	@Query("FROM Event e WHERE e.name LIKE %:nameEvent% AND e.agenda=:agenda")
	public Set<Event> searchForAnEvent(@Param("nameEvent") String nameEvent,@Param("agenda") Agenda agenda);
}
