package com.grupo05.proyecto.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Locality;
import com.grupo05.proyecto.models.entities.Ticket;

public interface TicketRepository
	extends ListCrudRepository<Ticket, UUID>{
	
	List<Ticket> findByLocality(Locality locality);
	void deleteByLocality(Locality locality);
}
