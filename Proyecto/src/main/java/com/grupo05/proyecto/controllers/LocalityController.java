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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo05.proyecto.models.dtos.ErrorsDTO;
import com.grupo05.proyecto.models.dtos.MessageDTO;
import com.grupo05.proyecto.models.dtos.SaveLocalityDTO;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.Locality;
import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.services.EventService;
import com.grupo05.proyecto.services.LocalityService;
import com.grupo05.proyecto.services.TicketService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/locality")
@CrossOrigin("*")
public class LocalityController {
	@Autowired
	private EventService eventService;
	
	@Autowired
	private LocalityService localityService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@GetMapping("/")
	public ResponseEntity<?> findAllLocalities(String eventCode, String name){
		List<Locality> localities = new ArrayList<>();
		
		if(eventCode == null && name == null) {
			localities = localityService.findAll();
		} else if (eventCode != null && name == null) {
			Event event = eventService.findOneById(eventCode);
			localities = localityService.findByEvent(event);
		} else if (eventCode == null && name != null) {
			localities = localityService.findByNameLike(name);
		} else {
			Event event = eventService.findOneById(eventCode);
			localities = localityService.findByEvent(event);
			
			List<Locality> localitiesTitle = localityService.findByNameLike(name);
			
			localities.retainAll(localitiesTitle);
		}
		
		return new ResponseEntity<>(localities, HttpStatus.OK);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<?> findOneLocalityById(@PathVariable("code") String localityCode){
		Locality locality = localityService.findOneById(localityCode);
		
		if(locality == null) {
			return new ResponseEntity<>(new MessageDTO("Locality not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(locality, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createLocality(@ModelAttribute @Valid SaveLocalityDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			Event event = eventService.findOneById(info.getEvent());
			
			if(event == null) {
				return new ResponseEntity<>(new MessageDTO("Event does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			localityService.create(info, event);
			return new ResponseEntity<>(new MessageDTO("Locality created"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{code}")
	public ResponseEntity<?> updateLocality(@PathVariable("code") String localityCode, 
			@ModelAttribute @Valid SaveLocalityDTO info,
			BindingResult validations){
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		try {			
			Locality locality = localityService.findOneById(localityCode);
			
			if(locality == null) {
				return new ResponseEntity<>(new MessageDTO("Locality does not exists"), HttpStatus.NOT_FOUND);
			}
			
			Event event = eventService.findOneById(info.getEvent());
			
			if(event == null) {
				return new ResponseEntity<>(new MessageDTO("Event does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			locality.setName(info.getName());
			locality.setPrice(info.getPrice());
			locality.setCapacity(info.getCapacity());
			locality.setEvent(event);
			
			localityService.update(locality);
			return new ResponseEntity<>(new MessageDTO("Locality updated"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{code}")
	public ResponseEntity<?> deleteLocality(@PathVariable("code") String localityCode){
		try {
			Locality locality = localityService.findOneById(localityCode);
			
			if(locality == null) {
				return new ResponseEntity<>(new MessageDTO("Locality does not exists"), HttpStatus.NOT_FOUND);
			}
			
			List<Ticket> tickets = locality.getTickets();
				
			if(!tickets.isEmpty()) {
				Boolean ticketSold = false;
				
				for(int i = 0; i < tickets.size(); i++) {
					if(tickets.get(i).getIsSold()) {
						ticketSold = true;
						break;
					}
				}
				
				if(ticketSold) {
					return new ResponseEntity<>(new MessageDTO("Locality could not be deleted because has sold tickets"), HttpStatus.CONFLICT);
				}
			}
			
			ticketService.deleteByLocality(locality);
			localityService.deleteById(localityCode);			
			return new ResponseEntity<>(new MessageDTO("Locality deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
