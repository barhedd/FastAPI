package com.grupo05.proyecto.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.models.entities.TicketExchange;
import com.grupo05.proyecto.models.entities.User;

public interface TicketExchangeRepository
	extends ListCrudRepository<TicketExchange, UUID>{

	List<TicketExchange> findByTicket(Ticket ticket);
	List<TicketExchange> findByTicketOwner(User ticketOwner);
	List<TicketExchange> findByExecutedExchange(Boolean executedExchange);
}
