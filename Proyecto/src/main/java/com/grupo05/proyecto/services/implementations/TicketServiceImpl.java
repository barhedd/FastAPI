package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.entities.Locality;
import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.repositories.TicketRepository;
import com.grupo05.proyecto.services.TicketService;

import jakarta.transaction.Transactional;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(Locality locality) throws Exception {
		Ticket ticket = new Ticket(
					locality
				);
		
		ticketRepository.save(ticket);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Ticket ticket) throws Exception {
		ticketRepository.save(ticket);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);
		ticketRepository.deleteById(code);
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteByLocality(Locality locality) throws Exception{
		ticketRepository.deleteByLocality(locality);
	}

	@Override
	public Ticket findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return ticketRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
	public List<Ticket> findAllByLocality(Locality locality) {
		return ticketRepository.findByLocality(locality);
	}
}
