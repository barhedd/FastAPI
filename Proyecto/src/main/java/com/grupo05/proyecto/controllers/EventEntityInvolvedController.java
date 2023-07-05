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
import com.grupo05.proyecto.models.dtos.SaveEventEntityInvolvedDTO;
import com.grupo05.proyecto.models.dtos.UpdateEventEntityInvolvedDTO;
import com.grupo05.proyecto.models.entities.EntityInvolved;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventEntityInvolved;
import com.grupo05.proyecto.services.EntityInvolvedService;
import com.grupo05.proyecto.services.EventEntityInvolvedService;
import com.grupo05.proyecto.services.EventService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/evententityinvolved")
@CrossOrigin("*")
public class EventEntityInvolvedController {
	
	@Autowired
	private EventEntityInvolvedService eventEntityInvolvedService;
	@Autowired
	private EventService eventService;
	@Autowired
	private EntityInvolvedService entityInvolvedService;
	@Autowired
	private RequestErrorHandler errorHandler;
	
	// Obtener la lista de todos los eventos de las entidades
	@GetMapping("/")
	public ResponseEntity<?> findAllEventEntities(){
		List<EventEntityInvolved> eventEntities = eventEntityInvolvedService.findAll();
		return new ResponseEntity<>(eventEntities, HttpStatus.FOUND);
	}
	
	// Obtener un evento por identificador
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneEventEntityById(@PathVariable("id") String idEventEntity){
		EventEntityInvolved eventEntity = eventEntityInvolvedService.findOneById(idEventEntity);
		
		if(eventEntity == null) {
			return new ResponseEntity<>(new MessageDTO("Event Entity not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eventEntity, HttpStatus.FOUND);
	}
	
	//Crear un nuevo evento con entidades envueltas
	@PostMapping("/create")
	public ResponseEntity<?> saveEventEntity(
			@ModelAttribute @Valid SaveEventEntityInvolvedDTO info, 
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
		EntityInvolved entity = entityInvolvedService.findOneById(info.getIdEntityInvolved());
		if(entity == null) {
			return new ResponseEntity<>(new MessageDTO("Entity Not found"), HttpStatus.NOT_FOUND);
		}
		// Al verificar si existe el evento y la entidad, se procede a guardar
		try {
			eventEntityInvolvedService.create(event, entity);
			return new ResponseEntity<>(new MessageDTO("Event-Entity save"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO(
					"Internal Server Error"), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Actualizar la relacion de uno de las entidades
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEventEntityById(@PathVariable("id") String idEventEntity,
			@ModelAttribute @Valid UpdateEventEntityInvolvedDTO updateEventEntity, 
			BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())),
					HttpStatus.BAD_REQUEST);
		}
		try {
			EventEntityInvolved eventEntity = eventEntityInvolvedService.findOneById(idEventEntity);
			if(eventEntity == null) {
				return new ResponseEntity<>(new MessageDTO(
						"EventEntity does not exists"), 
						HttpStatus.NOT_FOUND);
			}
			Event event = eventService.findOneById(updateEventEntity.getIdEvent());
			if(event == null) {
				return new ResponseEntity<>(new MessageDTO("Event Not Found"), HttpStatus.NOT_FOUND);
			}
			EntityInvolved entity = entityInvolvedService.findOneById(updateEventEntity.getIdEntityInvolved());
			if(entity == null) {
				return new ResponseEntity<>(new MessageDTO("Entity not found"), HttpStatus.NOT_FOUND);
			}
			eventEntity.setEvent(event);
			eventEntity.setEntityInvolved(entity);
			eventEntityInvolvedService.update(eventEntity);
			return new ResponseEntity<>(new MessageDTO("EventEntity updated"), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Eliminar un elemento con su identificador
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEventEntityById(@PathVariable("id") String idEventEntity){
		try {
			EventEntityInvolved eventEntity = eventEntityInvolvedService.findOneById(idEventEntity);
			if(eventEntity == null) {
				return new ResponseEntity<>(new MessageDTO(
						"EventEntity not found"), 
						HttpStatus.NOT_FOUND);
			}
			// POSIBLEMENTE GENERE FALLOS
			eventEntityInvolvedService.delete(idEventEntity);
			return new ResponseEntity<>(new MessageDTO("EventEntity Deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
