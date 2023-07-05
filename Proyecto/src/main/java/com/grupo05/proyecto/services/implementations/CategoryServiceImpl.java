package com.grupo05.proyecto.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.SaveCategoryDTO;
import com.grupo05.proyecto.models.entities.Category;
import com.grupo05.proyecto.repositories.CategoryRepository;
import com.grupo05.proyecto.services.CategoryService;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(SaveCategoryDTO info) throws Exception {
		Category category = new Category(
							info.getCode(),
							info.getName()
				);
		
		categoryRepository.save(category);
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Category category) throws Exception {
		categoryRepository.save(category);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		categoryRepository.deleteById(id);	
	}

	@Override
	public Category findOneById(String id) {
		try {
			return categoryRepository.findById(id)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
}
