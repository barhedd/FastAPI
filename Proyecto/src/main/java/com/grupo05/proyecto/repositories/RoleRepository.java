package com.grupo05.proyecto.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Role;

public interface RoleRepository
	extends ListCrudRepository<Role, String>{

}
