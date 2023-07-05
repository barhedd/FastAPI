package com.grupo05.proyecto.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo05.proyecto.models.dtos.ErrorsDTO;
import com.grupo05.proyecto.models.dtos.MessageDTO;
import com.grupo05.proyecto.models.dtos.SaveTicketDTO;
import com.grupo05.proyecto.models.dtos.TicketToggleOwnerDTO;
import com.grupo05.proyecto.models.entities.Locality;
import com.grupo05.proyecto.models.entities.Ticket;
import com.grupo05.proyecto.models.entities.User;
import com.grupo05.proyecto.services.LocalityService;
import com.grupo05.proyecto.services.TicketService;
import com.grupo05.proyecto.services.UserService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticket")
@CrossOrigin("*")
public class TicketController {
	@Autowired
	private LocalityService localityService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@GetMapping("/")
	public ResponseEntity<?> findAllLocalities(String localityCode){
		List<Ticket> tickets = new ArrayList<>();
		
		if(localityCode != null) {
			Locality locality = localityService.findOneById(localityCode);
			
			if(locality == null) {
				return new ResponseEntity<>(new MessageDTO("Locality not found"), HttpStatus.NOT_FOUND);
			}
			
			tickets = ticketService.findAllByLocality(locality);
		} else {
			tickets = ticketService.findAll();
		}
		
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<?> findOneTicketById(@PathVariable("code") String ticketCode){
		Ticket ticket = ticketService.findOneById(ticketCode);
		
		if(ticket == null) {
			return new ResponseEntity<>(new MessageDTO("Ticket does not exist"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createTicket(@ModelAttribute @Valid SaveTicketDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		try {
			Locality locality = localityService.findOneById(info.getLocality());
			
			if(locality == null) {
				return new ResponseEntity<>(new MessageDTO("Locality does not exists"), HttpStatus.BAD_REQUEST);
			}
			
			ticketService.create(locality);
			return new ResponseEntity<>(new MessageDTO("Ticket created"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/toggle-is-sold/{code}")
	public ResponseEntity<?> toggleIsSold(@PathVariable("code") String ticketCode){
		try {
			if(ticketCode == null) {
				return new ResponseEntity<>(new MessageDTO("Ticket's code could not be null"), HttpStatus.BAD_REQUEST);
			}
						
			Ticket ticket = ticketService.findOneById(ticketCode);
			
			if(ticket.getIsSold()) {
				ticket.setIsSold(false);
			}else {
				ticket.setIsSold(true);
			}
			
			ticketService.update(ticket);
			return new ResponseEntity<>(new MessageDTO("Ticket updated"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/toggle-owner/{code}")
	public ResponseEntity<?> toggleOwner(@PathVariable("code") String ticketCode, @ModelAttribute @Valid TicketToggleOwnerDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
						errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST
					);
		}
		
		if(ticketCode == null) {
			return new ResponseEntity<>(new MessageDTO("Ticket's code could not be null"), HttpStatus.BAD_REQUEST);
		}
		
		try {
			Ticket ticket = ticketService.findOneById(ticketCode);
			
			User user = userService.findOneById(info.getOwner());
			
			if(user == null) {
				return new ResponseEntity<>(new MessageDTO("User does not exist"), HttpStatus.BAD_REQUEST);
			}
			
			ticket.setOwner(user);
			
			ticketService.update(ticket);
			
			return new ResponseEntity<>(new MessageDTO("Ticket updated"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
