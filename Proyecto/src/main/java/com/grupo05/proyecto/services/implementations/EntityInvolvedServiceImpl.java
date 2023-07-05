package com.grupo05.proyecto.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.SaveEntityInvolvedDTO;
import com.grupo05.proyecto.models.entities.EntityInvolved;
import com.grupo05.proyecto.repositories.EntityInvolvedRepository;
import com.grupo05.proyecto.services.EntityInvolvedService;

import jakarta.transaction.Transactional;

@Service
public class EntityInvolvedServiceImpl implements EntityInvolvedService {
	
	@Autowired
	private EntityInvolvedRepository entityInvolvedRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(SaveEntityInvolvedDTO info) throws Exception {
		EntityInvolved entityInvolved = new EntityInvolved(
					info.getCode(),
					info.getName()
				);
		
		entityInvolvedRepository.save(entityInvolved);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(EntityInvolved entityInvolved) throws Exception {
		entityInvolvedRepository.save(entityInvolved);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		entityInvolvedRepository.deleteById(id);	
	}

	@Override
	public EntityInvolved findOneById(String id) {
		try {
			return entityInvolvedRepository.findById(id)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<EntityInvolved> findAll() {
		return entityInvolvedRepository.findAll();
	}
}
