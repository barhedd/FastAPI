package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.dtos.UserRegisterDTO;
import com.grupo05.proyecto.models.entities.User;

public interface UserService {
	void register(UserRegisterDTO info) throws Exception;
	void update(User user) throws Exception;
	void deleteById(String id) throws Exception;
	User findOneById(String id);
	User findOneByEmail(String email);
	List<User> findAll();
}
