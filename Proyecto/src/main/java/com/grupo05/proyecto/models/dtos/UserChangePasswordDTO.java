package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserChangePasswordDTO {
	// Id user or email
	@NotEmpty(message = "Identifier is required")
	private String identifier;
	
	@NotEmpty(message = "Current password is required")
	private String currentPassword;
	
	@NotEmpty(message = "New password is required")
	@Pattern(regexp = "(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,}", message = "La contraseña debe contener al menos una mayuscula, una minuscula, un numero, un caracter especial y tener como minimo 8 caracteres de longitud")
	private String newPassword;
}
