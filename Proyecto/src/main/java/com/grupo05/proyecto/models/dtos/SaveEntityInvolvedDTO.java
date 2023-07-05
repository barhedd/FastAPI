package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SaveEntityInvolvedDTO {
	@NotEmpty(message = "Code is required")
	@Pattern(regexp = "^EI[0-9][0-9][0-9]$", message = "Entity code must be formed the letters EI and three digits")
	private String code;
	
	@NotEmpty(message = "Name is required")
	private String name;
}
