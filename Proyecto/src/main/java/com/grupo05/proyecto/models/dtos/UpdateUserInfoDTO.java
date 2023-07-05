package com.grupo05.proyecto.models.dtos;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateUserInfoDTO {
	// Id user or email
	@NotEmpty(message = "Identifier is required")
	private String identifier;
	
	@NotEmpty(message = "Name is required")
	private String name;
	
	@NotEmpty(message = "Role is required")
	private List<String> roles;
}
