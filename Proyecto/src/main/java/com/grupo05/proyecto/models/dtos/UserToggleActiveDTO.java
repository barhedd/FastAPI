package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserToggleActiveDTO {
	// Id user or email
	@NotEmpty(message = "Identifier is required")
	private String identifier;
}
