package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventSponsor;
import com.grupo05.proyecto.models.entities.Sponsor;
import com.grupo05.proyecto.repositories.EventSponsorRepository;
import com.grupo05.proyecto.services.EventSponsorService;

import jakarta.transaction.Transactional;

@Service
public class EventSponsorServiceImpl implements EventSponsorService {
	
	@Autowired
	private EventSponsorRepository eventSponsorRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(Event event, Sponsor sponsor) throws Exception {
		EventSponsor eventSponsor = new EventSponsor(event, sponsor);
		
		eventSponsorRepository.save(eventSponsor);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void delete(String id) throws Exception {
		UUID code = UUID.fromString(id);
		eventSponsorRepository.deleteById(code);
	}

	@Override
	public EventSponsor findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return eventSponsorRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<EventSponsor> findAll() {
		return eventSponsorRepository.findAll();
	}

	@Override
	public List<EventSponsor> findByEvent(Event event) {
		return eventSponsorRepository.findByEvent(event);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(EventSponsor updateEventSponsor) throws Exception {
		eventSponsorRepository.save(updateEventSponsor);
	}
}
