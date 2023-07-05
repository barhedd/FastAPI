package com.grupo05.proyecto.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.RoleUser;
import com.grupo05.proyecto.models.entities.User;

public interface RoleUserRepository
	extends ListCrudRepository<RoleUser, UUID>{
	
	List<RoleUser> findByUser(User user);
}
