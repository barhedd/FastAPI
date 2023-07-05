package com.grupo05.proyecto.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.models.entities.TicketTransfer;
import com.grupo05.proyecto.models.entities.User;
import com.grupo05.proyecto.repositories.TicketTransferRepository;
import com.grupo05.proyecto.services.TicketTransferService;

import jakarta.transaction.Transactional;

@Service
public class TicketTransferServiceImpl implements TicketTransferService {
	
	@Autowired
	private TicketTransferRepository ticketTransferRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(String validationCode, Date codeValidityDate, Ticket ticket, User oldOwner, User newOwner) throws Exception {
		TicketTransfer ticketTransfer = new TicketTransfer(
					validationCode,
					codeValidityDate,
					ticket,
					oldOwner,
					newOwner
				);
		
		ticketTransferRepository.save(ticketTransfer);
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(TicketTransfer ticketTransfer) throws Exception {
		ticketTransferRepository.save(ticketTransfer);
	}

	@Override
	public TicketTransfer findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return ticketTransferRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<TicketTransfer> findAll() {
		return ticketTransferRepository.findAll();
	}

	@Override
	public List<TicketTransfer> findByTicket(Ticket ticket) {
		return ticketTransferRepository.findByTicket(ticket);
	}

	@Override
	public List<TicketTransfer> findByOldOwner(User user) {
		return ticketTransferRepository.findByOldOwner(user);
	}

	@Override
	public List<TicketTransfer> findByNewOwner(User user) {
		return ticketTransferRepository.findByNewOwner(user);
	}

	@Override
	public List<TicketTransfer> findByExecutedTransfer(Boolean executedTransfer) {
		return ticketTransferRepository.findByExecutedTransfer(executedTransfer);
	}
}
