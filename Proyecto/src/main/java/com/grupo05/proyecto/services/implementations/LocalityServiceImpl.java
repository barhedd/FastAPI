package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.SaveLocalityDTO;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.Locality;
import com.grupo05.proyecto.repositories.LocalityRepository;
import com.grupo05.proyecto.services.LocalityService;

import jakarta.transaction.Transactional;

@Service
public class LocalityServiceImpl implements LocalityService {

	@Autowired
	private LocalityRepository localityRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(SaveLocalityDTO info, Event event) throws Exception {
		Locality locality = new Locality(
					info.getName(),
					info.getPrice(),
					info.getCapacity(),
					event
				);
		
		localityRepository.save(locality);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Locality locality) throws Exception {
		localityRepository.save(locality);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);
		localityRepository.deleteById(code);
	}

	@Override
	public Locality findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return localityRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Locality> findAll() {
		return localityRepository.findAll();
	}

	@Override
	public List<Locality> findByNameLike(String name) {
		return localityRepository.findByNameContaining(name);
	}
	
	@Override
	public List<Locality> findByEvent(Event event) {
		return localityRepository.findByEvent(event);
	}
	

}
