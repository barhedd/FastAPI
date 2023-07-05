package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.UserRegisterDTO;
import com.grupo05.proyecto.models.entities.User;
import com.grupo05.proyecto.repositories.UserRepository;
import com.grupo05.proyecto.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void register(UserRegisterDTO info) throws Exception {
		User user = new User(
					info.getEmail(),
					info.getName()
				);
		
		userRepository.save(user);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(User user) throws Exception {
		userRepository.save(user);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);
		userRepository.deleteById(code);
	}

	@Override
	public User findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return userRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User findOneByEmail(String email) {
		try {
			return userRepository.findOneByEmail(email);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}
