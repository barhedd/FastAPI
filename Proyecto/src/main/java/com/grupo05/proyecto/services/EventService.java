package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.dtos.SaveEventDTO;
import com.grupo05.proyecto.models.entities.Category;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.Place;

public interface EventService {
	void create(SaveEventDTO info, Place place, Category category) throws Exception;
	void update(Event event) throws Exception;
	void deleteById(String id) throws Exception;
	Event findOneById(String id);
	List<Event> findAll();
	List<Event> findByTtitleLike(String title);
	List<Event> findByCategory(Category category);
}
