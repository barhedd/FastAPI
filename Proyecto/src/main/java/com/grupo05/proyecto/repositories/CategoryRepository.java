package com.grupo05.proyecto.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.grupo05.proyecto.models.entities.Category;

public interface CategoryRepository 
	extends ListCrudRepository<Category, String>{

}
