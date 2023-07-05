package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.dtos.SaveRoleDTO;
import com.grupo05.proyecto.models.entities.Role;

public interface RoleService {
	void create(SaveRoleDTO info) throws Exception;
	void update(Role role) throws Exception;
	void deleteById(String id) throws Exception;
	Role findOneById(String id);
	List<Role> findAll();
}
