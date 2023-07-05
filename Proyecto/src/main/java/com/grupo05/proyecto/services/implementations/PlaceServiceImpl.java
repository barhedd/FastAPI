package com.grupo05.proyecto.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.SavePlaceDTO;
import com.grupo05.proyecto.models.entities.Place;
import com.grupo05.proyecto.repositories.PlaceRepository;
import com.grupo05.proyecto.services.PlaceService;

import jakarta.transaction.Transactional;

@Service
public class PlaceServiceImpl implements PlaceService {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(SavePlaceDTO info) throws Exception {
		Place place = new Place(
					info.getCode(),
					info.getName()
				);
		
		placeRepository.save(place);
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Place place) throws Exception {	
		placeRepository.save(place);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		placeRepository.deleteById(id);
	}

	@Override
	public Place findOneById(String id) {
		try {
			return placeRepository.findById(id)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Place> findAll() {
		return placeRepository.findAll();
	}

}
