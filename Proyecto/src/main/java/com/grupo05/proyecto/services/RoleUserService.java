package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.entities.Role;
import com.grupo05.proyecto.models.entities.RoleUser;
import com.grupo05.proyecto.models.entities.User;

public interface RoleUserService {
	void create(Role role, User user) throws Exception;
	void delete(String id) throws Exception;
	RoleUser findOneById(String id);
	List<RoleUser> findAll();
	List<RoleUser> findByUser(User user);
}
