package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.entities.Locality;
import com.grupo05.proyecto.models.entities.Ticket;

public interface TicketService {
	void create(Locality locality) throws Exception;
	void update(Ticket ticket) throws Exception;
	void deleteById(String id) throws Exception;
	void deleteByLocality(Locality locality) throws Exception;
	Ticket findOneById(String id);
	List<Ticket> findAll();
	List<Ticket> findAllByLocality(Locality locality);
}
