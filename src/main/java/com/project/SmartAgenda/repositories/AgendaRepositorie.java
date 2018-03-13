package com.project.SmartAgenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SmartAgenda.beans.Agenda;
import com.project.SmartAgenda.beans.User;

public interface AgendaRepositorie extends JpaRepository<Agenda, Integer> {

	public Agenda findByUser(User user);
	
}
