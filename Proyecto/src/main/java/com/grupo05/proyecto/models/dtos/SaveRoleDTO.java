package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SaveRoleDTO {
	@NotEmpty(message = "Code is required")
	@Pattern(regexp = "^RL[0-9][0-9][0-9]$", message = "Role code must be formed the letters RL and three digits")
	private String code;
	
	@NotEmpty(message = "Name is required")
	private String name;
}
