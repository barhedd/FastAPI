package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventSponsor;
import com.grupo05.proyecto.models.entities.Sponsor;

public interface EventSponsorService {
	void create(Event event, Sponsor sponsor) throws Exception;
	void update(EventSponsor updateEventSponsor) throws Exception;
	void delete(String id) throws Exception;
	EventSponsor findOneById(String id);
	List<EventSponsor> findAll();
	List<EventSponsor> findByEvent(Event event);
}
