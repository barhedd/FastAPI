package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateEventEntityInvolvedDTO {

	@NotEmpty
	private String idEvent;
	@NotEmpty
	private String idEntityInvolved;
}
