package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.entities.EntityInvolved;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventEntityInvolved;

public interface EventEntityInvolvedService {
	void create(Event event, EntityInvolved entityInvolved) throws Exception;
	void update(EventEntityInvolved eventEntityUpdate) throws Exception;
	void delete(String id) throws Exception;
	EventEntityInvolved findOneById(String id);
	List<EventEntityInvolved> findAll();
	List<EventEntityInvolved> findByEvent(Event event);
}
