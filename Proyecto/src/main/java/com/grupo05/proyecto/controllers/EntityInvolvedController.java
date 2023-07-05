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
import com.grupo05.proyecto.models.dtos.SaveEntityInvolvedDTO;
import com.grupo05.proyecto.models.dtos.UpdateEntityInvolvdeDTO;
import com.grupo05.proyecto.models.entities.EntityInvolved;
import com.grupo05.proyecto.services.EntityInvolvedService;
import com.grupo05.proyecto.utils.RequestErrorHandler;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/entityinvolved")
@CrossOrigin("*")
public class EntityInvolvedController {
	
	@Autowired
	private EntityInvolvedService entityInvolvedService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	// Obtener la lista de entidades
	@GetMapping("/")
	public ResponseEntity<?> findAllEntityInvolved(){
		List<EntityInvolved> entitiesInv = entityInvolvedService.findAll();
		// Se muestra la lista obtenida
		return new ResponseEntity<>(entitiesInv, HttpStatus.OK);
	}
	
	// Obtener una entidad por identificador
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneEntityById(@PathVariable("id") String entityId){
		EntityInvolved entityInv = entityInvolvedService.findOneById(entityId);
		
		if(entityInv == null) {
			return new ResponseEntity<>(new MessageDTO("Entity not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(entityInv, HttpStatus.FOUND);
	}
	
	// Crear una nueva entidad 
	@PostMapping("/create")
	public ResponseEntity<?> saveEntityInvolved
		(@ModelAttribute @Valid SaveEntityInvolvedDTO info, BindingResult validations ){
			if(validations.hasErrors()) {
				return new ResponseEntity<>(new ErrorsDTO(
							errorHandler.mapErrors(validations.getFieldErrors())),
							HttpStatus.BAD_REQUEST );
			}
			
			try {
				EntityInvolved entity = entityInvolvedService.findOneById(info.getCode());
				if(entity != null) {
					return new ResponseEntity<>(
								new MessageDTO("Entity already existe"), 
								HttpStatus.BAD_GATEWAY);
				}
				
				entityInvolvedService.create(info);
				return new ResponseEntity<>(new MessageDTO("Entiy created"), HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(
							new MessageDTO("Internal Server Error"), 
							HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	// Actualizar una entidad basandose en su identificador
	// Y tomando la informacion pasada por el cuerpo de la peticion
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEntityInvolved(@PathVariable("id") String idEntity,
			@ModelAttribute @Valid UpdateEntityInvolvdeDTO info, 
			BindingResult validations){
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
						new ErrorsDTO(errorHandler.mapErrors(validations.getFieldErrors())),
						HttpStatus.BAD_REQUEST);
		}
		
		try {
			EntityInvolved entity = entityInvolvedService.findOneById(idEntity);
			if(entity == null) {
				return new ResponseEntity<>(new MessageDTO(
							"Entity does not Exists"), 
							HttpStatus.NOT_FOUND);
			}
			
			entity.setName(info.getName());
			entityInvolvedService.update(entity);
			return new ResponseEntity<>(new MessageDTO("Entity updated"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO
						("Internal Server Error"), 
						HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Eliminar entidad dado el identificador
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEntityInvolved(@PathVariable("id") String idEntity){
		try {
			EntityInvolved entity = entityInvolvedService.findOneById(idEntity);
			
			if(entity == null) {
				return new ResponseEntity<>(new MessageDTO(
							"Entity does not exists"), 
							HttpStatus.NOT_FOUND);
			}
			
			if(!entity.getEventEntitiesInvolved().isEmpty()) {
				return new ResponseEntity<>(new MessageDTO(
							"Entity could not be deleted because has asignated events"), 
							HttpStatus.CONFLICT);
			}
			entityInvolvedService.deleteById(idEntity);
			return new ResponseEntity<>(new MessageDTO("Entity deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO(
						"Internal Server Error"), 
						HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
