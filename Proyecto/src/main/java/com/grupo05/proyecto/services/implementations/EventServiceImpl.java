package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.SaveEventDTO;
import com.grupo05.proyecto.models.entities.Category;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.Place;
import com.grupo05.proyecto.repositories.EventRepository;
import com.grupo05.proyecto.services.EventService;

import jakarta.transaction.Transactional;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(SaveEventDTO info, Place place, Category category) throws Exception {
		Event event = new Event(
					info.getTitle(),
					info.getImage(),
					info.getDate(),
					info.getDuration(),
					place,
					category
				);
		
		eventRepository.save(event);		
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Event event) throws Exception {
		eventRepository.save(event);		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);	
		eventRepository.deleteById(code);		
	}

	@Override
	public Event findOneById(String id) {
		try { 
			UUID code = UUID.fromString(id);
			return eventRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	@Override
	public List<Event> findByTtitleLike(String title) {
		return eventRepository.findByTitleContaining(title);
	}

	@Override
	public List<Event> findByCategory(Category category) {
		return eventRepository.findByCategory(category);
	}

}
