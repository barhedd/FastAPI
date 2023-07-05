package com.grupo05.proyecto.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.models.entities.TicketTransfer;
import com.grupo05.proyecto.models.entities.User;

public interface TicketTransferRepository
	extends ListCrudRepository<TicketTransfer, UUID>{
	
	List<TicketTransfer> findByTicket(Ticket ticket);
	List<TicketTransfer> findByOldOwner(User oldOwner);
	List<TicketTransfer> findByNewOwner(User newOwner);
	List<TicketTransfer> findByExecutedTransfer(Boolean executedTransfer);
}
