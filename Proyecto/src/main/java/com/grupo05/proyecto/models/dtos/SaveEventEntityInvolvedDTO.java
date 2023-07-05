package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveEventEntityInvolvedDTO {

	@NotEmpty(message = "Event is required")
	private String idEvent;
	@NotEmpty(message = "Entity is required")
	private String idEntityInvolved;
}
