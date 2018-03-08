package com.project.SmartAgenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SmartAgenda.beans.Agenda;

public interface AgendaRepositorie extends JpaRepository<Agenda, Integer> {

	
	
}
