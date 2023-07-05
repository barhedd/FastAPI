package com.grupo05.proyecto.services;

import java.util.Date;
import java.util.List;

import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.models.entities.TicketExchange;
import com.grupo05.proyecto.models.entities.User;

public interface TicketExchangeService {
	void create(String validationHash, Date hashValidityDate, Ticket ticket, User ticketOwner) throws Exception;
	void update(TicketExchange ticketExchange) throws Exception;
	TicketExchange findOneById(String id);
	List<TicketExchange> findAll();
	List<TicketExchange> findByTicket(Ticket ticket);
	List<TicketExchange> findByTicketOwner(User ticketOwner);
	List<TicketExchange> findByExecutedExchange(Boolean executedExchange);
}
