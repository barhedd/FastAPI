package com.grupo05.proyecto.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.User;

public interface UserRepository
	extends ListCrudRepository<User, UUID>{
	
	User findOneByEmail(String email);
}
