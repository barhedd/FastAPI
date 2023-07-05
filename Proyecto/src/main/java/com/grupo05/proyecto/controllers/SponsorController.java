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
import com.grupo05.proyecto.models.dtos.SaveSponsorDTO;
import com.grupo05.proyecto.models.dtos.UpdateSponsorDTO;
import com.grupo05.proyecto.models.entities.Sponsor;
import com.grupo05.proyecto.services.SponsorService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sponsor")
@CrossOrigin("*")
public class SponsorController {
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	// Obtener la lista de todos los sponsor
	@GetMapping("/")
	public ResponseEntity<?> findAllSponsors(){
		List<Sponsor> sponsors = sponsorService.findAll();
		return new ResponseEntity<>(sponsors, HttpStatus.OK);
	}
	
	// Obtener un Sponsor Por Id
	@GetMapping("/{id}")
	public ResponseEntity<?> findSponsoById(@PathVariable("id") String idSponsor){
		Sponsor sponsor = sponsorService.findOneById(idSponsor);
		if(sponsor == null) {
			return new ResponseEntity<>(new MessageDTO("Sponsor Not Found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(sponsor, HttpStatus.FOUND);
	}
	
	//Crear un nuevo Sponsor 
	@PostMapping("/create")
	public ResponseEntity<?> createSponsor(
			@ModelAttribute @Valid SaveSponsorDTO info, 
			BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			Sponsor sponsor = sponsorService.findOneById(info.getCode());
			if(sponsor != null) {
				return new ResponseEntity<>(new MessageDTO(
						"Sponsor Already Exists"), HttpStatus.NOT_FOUND);
			}
			sponsorService.create(info);
			return new ResponseEntity<>(new MessageDTO("Sponsor Created"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Actualizando la informacion de un sponsor Por identificador
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateSponsorById(@PathVariable("id") String idSponsor,
			@ModelAttribute @Valid UpdateSponsorDTO updateSponsor, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())),
					HttpStatus.BAD_GATEWAY);
		}
		
		try {
			Sponsor sponsor = sponsorService.findOneById(idSponsor);
			if(sponsor == null) {
				return new ResponseEntity<>(new MessageDTO("Sponsor does not exists"), HttpStatus.NOT_FOUND);
			}
			sponsor.setName(updateSponsor.getName());
			sponsorService.update(sponsor);
			return new ResponseEntity<>(new MessageDTO("Sponsor Updated"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Eliminar el dato de un sponsor por Identificador
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSponsorById(@PathVariable("id") String idSponsor){
		try {
			Sponsor sponsor = sponsorService.findOneById(idSponsor);
			if(sponsor == null){
				return new ResponseEntity<>(new MessageDTO("Sponsor Not Found"), HttpStatus.NOT_FOUND);
			}
			if(!sponsor.getEventSponsors().isEmpty()) {
				return new ResponseEntity<>(new MessageDTO(
						"Sponsor could not be deleted because has asignated events"), 
						HttpStatus.CONFLICT);
			}
			sponsorService.deleteById(idSponsor);
			return new ResponseEntity<>(new MessageDTO("Sponsor Deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
