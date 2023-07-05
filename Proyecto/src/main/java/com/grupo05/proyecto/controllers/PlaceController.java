package com.grupo05.proyecto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo05.proyecto.utils.RequestErrorHandler;
import com.grupo05.proyecto.models.dtos.ErrorsDTO;
import com.grupo05.proyecto.models.dtos.MessageDTO;
import com.grupo05.proyecto.models.dtos.SavePlaceDTO;
import com.grupo05.proyecto.models.entities.Place;
import com.grupo05.proyecto.services.PlaceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/place")
@CrossOrigin("*")
public class PlaceController {

	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@GetMapping("/")
	public ResponseEntity<?> findAllPlaces(){
		List<Place> places = placeService.findAll();
		
		return new ResponseEntity<>(places, HttpStatus.OK);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<?> findOnePlaceById(@PathVariable("code") String placeCode){
		Place place = placeService.findOneById(placeCode);
		
		if(place == null) {
			return new ResponseEntity<>(new MessageDTO("Place not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(place, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createPlace(@ModelAttribute @Valid SavePlaceDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			Place place = placeService.findOneById(info.getCode());
			
			if(place != null) {
				return new ResponseEntity<>(new MessageDTO("Place already exists"), HttpStatus.BAD_REQUEST);
			}
			
			placeService.create(info);
			return new ResponseEntity<>(new MessageDTO("Place created"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{code}")
	public ResponseEntity<?> updatePlace(@PathVariable("code") String placeCode, 
			@ModelAttribute @Valid SavePlaceDTO info,
			BindingResult validations){
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			Place place = placeService.findOneById(placeCode);
			
			if(place == null) {
				return new ResponseEntity<>(new MessageDTO("Place does not exists"), HttpStatus.NOT_FOUND);
			}
		
			place.setName(info.getName());
			
			placeService.update(place);
			return new ResponseEntity<>(new MessageDTO("Place updated"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{code}")
	public ResponseEntity<?> deletePlace(@PathVariable("code") String placeCode){
		try {
			Place place = placeService.findOneById(placeCode);
			
			if(place == null) {
				return new ResponseEntity<>(new MessageDTO("Place does not exists"), HttpStatus.NOT_FOUND);
			}
			
			if(!place.getEvents().isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("Place could not be deleted because has asignated events"), HttpStatus.CONFLICT);
			}
			
			placeService.deleteById(placeCode);			
			return new ResponseEntity<>(new MessageDTO("Place deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
