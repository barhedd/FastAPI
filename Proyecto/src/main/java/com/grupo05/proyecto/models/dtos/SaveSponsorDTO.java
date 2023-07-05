package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SaveSponsorDTO {
	@NotEmpty(message = "Code is required")
	@Pattern(regexp = "^SP[0-9][0-9][0-9]$", message = "Sponsor code must be formed the letters SP and three digits")
	private String code;
	
	@NotEmpty(message = "Name is required")
	private String name;
}
