package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SavePlaceDTO {
	@NotEmpty(message = "Code is required")
	@Pattern(regexp = "^PL[0-9][0-9][0-9]$", message = "Place code must be formed the letters PL and three digits")
	private String code;
	
	@NotEmpty(message = "Name is required")
	private String name;
}
