package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SaveCategoryDTO {
	@NotEmpty(message = "Code is required")
	@Pattern(regexp = "^CT[0-9][0-9][0-9]$", message = "Category code must be formed the letters CT and three digits")
	private String code;
	
	@NotEmpty(message = "Name is required")
	private String name;
}
