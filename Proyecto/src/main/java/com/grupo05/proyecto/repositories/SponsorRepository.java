package com.grupo05.proyecto.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Sponsor;

public interface SponsorRepository 
	extends ListCrudRepository<Sponsor, String>{

}
