package com.grupo05.proyecto.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventDesignatedEmployee;

public interface EventDesignatedEmployeeRepository
	extends ListCrudRepository<EventDesignatedEmployee, UUID>{
	
	List<EventDesignatedEmployee> findByEvent(Event event);
}
