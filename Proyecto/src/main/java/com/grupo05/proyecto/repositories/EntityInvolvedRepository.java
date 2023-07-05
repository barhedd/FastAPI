package com.grupo05.proyecto.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.EntityInvolved;

public interface EntityInvolvedRepository
	extends ListCrudRepository<EntityInvolved, String>{

}
