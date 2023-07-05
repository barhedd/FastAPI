package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.entities.EntityInvolved;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventEntityInvolved;
import com.grupo05.proyecto.repositories.EventEntityInvolvedRepository;
import com.grupo05.proyecto.services.EventEntityInvolvedService;

import jakarta.transaction.Transactional;

@Service
public class EventEntityInvolvedServiceImpl implements EventEntityInvolvedService {
	
	@Autowired
	private EventEntityInvolvedRepository eventEntityInvolvedRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(Event event, EntityInvolved entityInvolved) throws Exception {
		EventEntityInvolved eventEntityInvolved = new EventEntityInvolved(
					event,
					entityInvolved
				);
		
		eventEntityInvolvedRepository.save(eventEntityInvolved);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void delete(String id) throws Exception {
		UUID code = UUID.fromString(id);
		eventEntityInvolvedRepository.deleteById(code);
	}

	@Override
	public EventEntityInvolved findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return eventEntityInvolvedRepository.findById(code)
					.orElse(null);
		} catch (Exception e) { 
			return null;
		}
	}

	@Override
	public List<EventEntityInvolved> findAll() {
		return eventEntityInvolvedRepository.findAll();
	}

	@Override
	public List<EventEntityInvolved> findByEvent(Event event) {
		return eventEntityInvolvedRepository.findByEvent(event);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(EventEntityInvolved eventEntityUpdate) throws Exception {
		eventEntityInvolvedRepository.save(eventEntityUpdate);
		
	}
}
