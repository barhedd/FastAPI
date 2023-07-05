package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.entities.Role;
import com.grupo05.proyecto.models.entities.RoleUser;
import com.grupo05.proyecto.models.entities.User;
import com.grupo05.proyecto.repositories.RoleUserRepository;
import com.grupo05.proyecto.services.RoleUserService;

import jakarta.transaction.Transactional;

@Service
public class RoleUserServiceImpl implements RoleUserService{

	@Autowired
	private RoleUserRepository roleUserRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(Role role, User user) throws Exception {
		RoleUser roleUser = new RoleUser(role, user);
		
		roleUserRepository.save(roleUser);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void delete(String id) throws Exception {
		UUID code = UUID.fromString(id);
		roleUserRepository.deleteById(code);
	}

	@Override
	public RoleUser findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return roleUserRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<RoleUser> findAll() {
		return roleUserRepository.findAll();
	}

	@Override
	public List<RoleUser> findByUser(User user) {
		return roleUserRepository.findByUser(user);
	}

}
