package com.grupo05.proyecto.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.SaveRoleDTO;
import com.grupo05.proyecto.models.entities.Role;
import com.grupo05.proyecto.repositories.RoleRepository;
import com.grupo05.proyecto.services.RoleService;

import jakarta.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(SaveRoleDTO info) throws Exception {
		Role role = new Role(
					info.getCode(),
					info.getName()
				);
		
		roleRepository.save(role);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Role role) throws Exception {
		roleRepository.save(role);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		roleRepository.deleteById(id);
	}

	@Override
	public Role findOneById(String id) {
		try {
			return roleRepository.findById(id)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

}
