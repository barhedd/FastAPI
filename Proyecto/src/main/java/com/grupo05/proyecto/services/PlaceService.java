package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.dtos.SavePlaceDTO;
import com.grupo05.proyecto.models.entities.Place;

public interface PlaceService {
	void create(SavePlaceDTO info) throws Exception;
	void update(Place place) throws Exception;
	void deleteById(String id) throws Exception;
	Place findOneById(String id);
	List<Place> findAll();
}
