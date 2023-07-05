package com.grupo05.proyecto.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.models.entities.TicketExchange;
import com.grupo05.proyecto.models.entities.User;
import com.grupo05.proyecto.repositories.TicketExchangeRepository;
import com.grupo05.proyecto.services.TicketExchangeService;

import jakarta.transaction.Transactional;

@Service
public class TicketExchangeServiceImpl implements TicketExchangeService {

	@Autowired
	private TicketExchangeRepository ticketExchangeRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(String validationHash, Date hashValidityDate, Ticket ticket, User ticketOwner) throws Exception {
		TicketExchange ticketExchange = new TicketExchange(
					validationHash,
					hashValidityDate,
					ticket,
					ticketOwner
				);
		
		ticketExchangeRepository.save(ticketExchange);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(TicketExchange ticketExchange) throws Exception {
		ticketExchangeRepository.save(ticketExchange);
	}

	@Override
	public TicketExchange findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return ticketExchangeRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<TicketExchange> findAll() {
		return ticketExchangeRepository.findAll();
	}

	@Override
	public List<TicketExchange> findByTicket(Ticket ticket) {
		return ticketExchangeRepository.findByTicket(ticket);
	}

	@Override
	public List<TicketExchange> findByTicketOwner(User ticketOwner) {
		return ticketExchangeRepository.findByTicketOwner(ticketOwner);
	}

	@Override
	public List<TicketExchange> findByExecutedExchange(Boolean executedExchange) {
		return ticketExchangeRepository.findByExecutedExchange(executedExchange);
	}
}
