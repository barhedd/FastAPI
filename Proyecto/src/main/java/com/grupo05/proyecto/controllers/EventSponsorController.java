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

import com.grupo05.proyecto.models.dtos.ErrorsDTO;
import com.grupo05.proyecto.models.dtos.MessageDTO;
import com.grupo05.proyecto.models.dtos.SaveEventSponsorDTO;
import com.grupo05.proyecto.models.dtos.UpdateEventSponsorDTO;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventSponsor;
import com.grupo05.proyecto.models.entities.Sponsor;
import com.grupo05.proyecto.services.EventService;
import com.grupo05.proyecto.services.EventSponsorService;
import com.grupo05.proyecto.services.SponsorService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventsponsor")
@CrossOrigin("*")
public class EventSponsorController {

	@Autowired
	private EventSponsorService eventSponsorService;
	@Autowired
	private EventService eventService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private RequestErrorHandler errorHandler;
	
	// obtener la lista de event Sponsor
	@GetMapping("/")
	public ResponseEntity<?> findAllEventSponsor(){
		List<EventSponsor> eventSponsor = eventSponsorService.findAll();
		return new ResponseEntity<>(eventSponsor, HttpStatus.OK);
	}
	
	//Obtener Event Sponsor por Id
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneEventSponsorById(@PathVariable("id") String IdSponsor){
		 EventSponsor eventSponsor = eventSponsorService.findOneById(IdSponsor);
		 if(eventSponsor == null) {
			 return new ResponseEntity<>(new MessageDTO("Sponsor Not found"), HttpStatus.NOT_FOUND);
		 }
		 return new ResponseEntity<>(eventSponsor, HttpStatus.FOUND);
	}
	
	//Crear la entidad Evento Sponsor
	@PostMapping("/create")
	public ResponseEntity<?> createEventSponsor(
			@ModelAttribute @Valid SaveEventSponsorDTO info, 
			BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST);
		}
		// validamos si existen ambas
		Event event = eventService.findOneById(info.getIdEvent());
		if(event == null) {
			return new ResponseEntity<>(new MessageDTO("Event not found"), HttpStatus.NOT_FOUND);
		}
		Sponsor sponsor = sponsorService.findOneById(info.getIdSponsor());
		if(sponsor == null) {
			return new ResponseEntity<>(new MessageDTO("sponsor Not found"), HttpStatus.NOT_FOUND);
		}
		// Al verificar si existe el evento y la entidad, se procede a guardar
		try {
			eventSponsorService.create(event, sponsor);
			return new ResponseEntity<>(new MessageDTO("Event-Sponsor save"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO(
					"Internal Server Error"), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Actualizar la relacion de Event Sponsor
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEventSponsorById(@PathVariable("id") String idEventSponsor,
			@ModelAttribute @Valid UpdateEventSponsorDTO info, 
			BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())),
					HttpStatus.BAD_REQUEST);
		}
		try {
			EventSponsor eventEntity = eventSponsorService.findOneById(idEventSponsor);
			if(eventEntity == null) {
				return new ResponseEntity<>(new MessageDTO(
						"EventEntity does not exists"), 
						HttpStatus.NOT_FOUND);
			}
			Event event = eventService.findOneById(info.getIdEvent());
			if(event == null) {
				return new ResponseEntity<>(new MessageDTO("Event Not Found"), HttpStatus.NOT_FOUND);
			}
			Sponsor sponsor = sponsorService.findOneById(info.getIdSponsor());
			if(sponsor == null) {
				return new ResponseEntity<>(new MessageDTO("sponsor not found"), HttpStatus.NOT_FOUND);
			}
			eventEntity.setEvent(event);
			eventEntity.setSponsor(sponsor);
			eventSponsorService.update(eventEntity);
			return new ResponseEntity<>(new MessageDTO("EventSponsor updated"), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Eliminar la relacion de Evento y sponsor mediante un identificador
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEventSponsorById(@PathVariable("id") String idEventSponsor){
		try {
			EventSponsor eventEntity = eventSponsorService.findOneById(idEventSponsor);
			if(eventEntity == null) {
				return new ResponseEntity<>(new MessageDTO(
						"EventEntity not found"), 
						HttpStatus.NOT_FOUND);
			}
			// POSIBLEMENTE GENERE FALLOS
			eventSponsorService.delete(idEventSponsor);
			return new ResponseEntity<>(new MessageDTO("EventEntity Deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
