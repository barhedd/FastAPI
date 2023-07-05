package com.grupo05.proyecto.services;

import java.util.Date;
import java.util.List;

import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.models.entities.TicketTransfer;
import com.grupo05.proyecto.models.entities.User;

public interface TicketTransferService {
	void create(String validationCode, Date codeValidityDate, Ticket ticket, User oldOwner, User newOwner) throws Exception;
	void update(TicketTransfer ticketTransfer) throws Exception;
	TicketTransfer findOneById(String id);
	List<TicketTransfer> findAll();
	List<TicketTransfer> findByTicket(Ticket ticket);
	List<TicketTransfer> findByOldOwner(User user);
	List<TicketTransfer> findByNewOwner(User user);
	List<TicketTransfer> findByExecutedTransfer(Boolean executedTransfer);
}
