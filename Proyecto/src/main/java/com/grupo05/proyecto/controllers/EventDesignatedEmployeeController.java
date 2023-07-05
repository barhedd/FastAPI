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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo05.proyecto.models.dtos.ErrorsDTO;
import com.grupo05.proyecto.models.dtos.MessageDTO;
import com.grupo05.proyecto.models.dtos.SaveEventDesignatedEmployeeDTO;
import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventDesignatedEmployee;
import com.grupo05.proyecto.models.entities.User;
import com.grupo05.proyecto.services.EventDesignatedEmployeeService;
import com.grupo05.proyecto.services.EventService;
import com.grupo05.proyecto.services.UserService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventDesignatedEmployee")
@CrossOrigin("*")
public class EventDesignatedEmployeeController {
	
	@Autowired
	private EventDesignatedEmployeeService eventDesignatedEmployeeService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@GetMapping("/")
	public ResponseEntity<?> findAll(){	
		List<EventDesignatedEmployee> items = eventDesignatedEmployeeService.findAll();
		
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<?> findOneEventDesignatedEmployeeById(@PathVariable("code") String code){
		EventDesignatedEmployee eventDesignatedEmployee = eventDesignatedEmployeeService.findOneById(code);
		
		if(eventDesignatedEmployee == null) {
			return new ResponseEntity<>(new MessageDTO("Entity not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(eventDesignatedEmployee, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createEventDesignatedEmployee(@ModelAttribute @Valid SaveEventDesignatedEmployeeDTO info, BindingResult validations){
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
			
			User user = userService.findOneById(info.getEmployee());
			
			if(user == null) {
				return new ResponseEntity<>(new MessageDTO("Employee does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			eventDesignatedEmployeeService.create(event, user);
			return new ResponseEntity<>(new MessageDTO("Employee asignated to event"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{code}")
	public ResponseEntity<?> deleteEventDesignatedEmployee(@PathVariable("code") String code){
		try {
			EventDesignatedEmployee entity = eventDesignatedEmployeeService.findOneById(code);
			
			if(entity == null) {
				return new ResponseEntity<>(new MessageDTO("Entity does not exists"), HttpStatus.NOT_FOUND);
			}
			
			eventDesignatedEmployeeService.delete(code);			
			return new ResponseEntity<>(new MessageDTO("Event designated employee deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}