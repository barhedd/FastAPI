package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.dtos.SaveLocalityDTO;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.Locality;

public interface LocalityService {
	void create(SaveLocalityDTO info, Event event) throws Exception;
	void update(Locality locality) throws Exception;
	void deleteById(String id) throws Exception;
	Locality findOneById(String id);
	List<Locality> findAll();
	List<Locality> findByNameLike(String name);
	List<Locality> findByEvent(Event event);
}
