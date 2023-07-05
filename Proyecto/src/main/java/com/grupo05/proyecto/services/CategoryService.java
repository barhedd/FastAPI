package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.dtos.SaveCategoryDTO;
import com.grupo05.proyecto.models.entities.Category;

public interface CategoryService {
	void create(SaveCategoryDTO info) throws Exception;
	void update(Category category) throws Exception;
	void deleteById(String id) throws Exception;
	Category findOneById(String id);
	List<Category> findAll();
}
