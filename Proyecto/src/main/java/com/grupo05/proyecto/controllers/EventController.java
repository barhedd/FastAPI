package com.grupo05.proyecto.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo05.proyecto.models.dtos.ErrorsDTO;
import com.grupo05.proyecto.models.dtos.MessageDTO;
import com.grupo05.proyecto.models.dtos.SaveEventDTO;
import com.grupo05.proyecto.models.entities.Category;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.Place;
import com.grupo05.proyecto.services.CategoryService;
import com.grupo05.proyecto.services.EventService;
import com.grupo05.proyecto.services.PlaceService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/event")
@CrossOrigin("*")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@GetMapping("/")
	public ResponseEntity<?> findAllEvents(String categoryCode, String title){
		List<Event> events = new ArrayList<>();
		
		if(categoryCode == null && title == null) {
			events = eventService.findAll();
		} else if (categoryCode != null && title == null) {
			Category category = categoryService.findOneById(categoryCode);
			events = eventService.findByCategory(category);
		} else if (categoryCode == null && title != null) {
			events = eventService.findByTtitleLike(title);
		} else {
			Category category = categoryService.findOneById(categoryCode);
			events = eventService.findByCategory(category);
			
			List<Event> eventsTitle = eventService.findByTtitleLike(title);
			
			events.retainAll(eventsTitle);
		}
		
		return new ResponseEntity<>(events, HttpStatus.OK);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<?> findOneEventById(@PathVariable("code") String eventCode){
		Event event = eventService.findOneById(eventCode);
		
		if(event == null) {
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(event, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createEvent(@ModelAttribute @Valid SaveEventDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			Category category = categoryService.findOneById(info.getCategory());
			
			if(category == null) {
				return new ResponseEntity<>(new MessageDTO("Category does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			Place place = placeService.findOneById(info.getPlace());
			
			if(place == null) {
				return new ResponseEntity<>(new MessageDTO("Place does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			eventService.create(info, place, category);
			return new ResponseEntity<>(new MessageDTO("Event created"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{code}")
	public ResponseEntity<?> updateEvent(@PathVariable("code") String eventCode, 
			@ModelAttribute @Valid SaveEventDTO info,
			BindingResult validations){
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		try {			
			Event event = eventService.findOneById(eventCode);
			
			if(event == null) {
				return new ResponseEntity<>(new MessageDTO("Event does not exists"), HttpStatus.NOT_FOUND);
			}
			
			Category category = categoryService.findOneById(info.getCategory());
			
			if(category == null) {
				return new ResponseEntity<>(new MessageDTO("Category does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			Place place = placeService.findOneById(info.getPlace());
			
			if(place == null) {
				return new ResponseEntity<>(new MessageDTO("Place does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			event.setTitle(info.getTitle());
			event.setImage(info.getImage());
			event.setDate(info.getDate());
			event.setDuration(info.getDuration());
			event.setPlace(place);
			event.setCategory(category);
			
			eventService.update(event);
			return new ResponseEntity<>(new MessageDTO("Event updated"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{code}")
	public ResponseEntity<?> deleteEvent(@PathVariable("code") String eventCode){
		try {
			Event event = eventService.findOneById(eventCode);
			
			if(event == null) {
				return new ResponseEntity<>(new MessageDTO("Event does not exists"), HttpStatus.NOT_FOUND);
			}
			
			if(!event.getLocalities().isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("Event could not be deleted because has asignated localities"), HttpStatus.CONFLICT);
			}

			eventService.deleteById(eventCode);			
			return new ResponseEntity<>(new MessageDTO("Event deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/toggle-active/{code}")
	public ResponseEntity<?> toggleActive(@PathVariable("code") String eventCode){
		try {
			Event event = eventService.findOneById(eventCode);
			
			if(event == null) {
				return new ResponseEntity<>(new MessageDTO("Event does not exists"), HttpStatus.NOT_FOUND);
			}
			
			if(event.getIsActive()) {
				event.setIsActive(false);
			} else {
				event.setIsActive(true);
			}
			
			eventService.update(event);			
			return new ResponseEntity<>(new MessageDTO("Active event's status updated successfully"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
