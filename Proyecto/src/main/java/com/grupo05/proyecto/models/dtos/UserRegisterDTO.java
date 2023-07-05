package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegisterDTO {	
	@NotEmpty(message = "Email is required")
	@Email(regexp = ".+[@].+[\\.].+", message = "Please provide a valid email address")
	private String email;
	
	@NotEmpty(message = "User is required")
	private String name;
}
