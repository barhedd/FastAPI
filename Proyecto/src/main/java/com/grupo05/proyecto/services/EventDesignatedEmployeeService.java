package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventDesignatedEmployee;
import com.grupo05.proyecto.models.entities.User;

public interface EventDesignatedEmployeeService {
	void create(Event event, User employee) throws Exception;
	void delete(String id) throws Exception;
	EventDesignatedEmployee findOneById(String id);
	List<EventDesignatedEmployee> findAll();
	List<EventDesignatedEmployee> findByEvent(Event event);
}
