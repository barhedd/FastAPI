package com.grupo05.proyecto.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Place;

public interface PlaceRepository
	extends ListCrudRepository<Place, String>{

}
