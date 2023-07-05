package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveTicketDTO {
	@NotEmpty(message = "Locality is required")
	private String locality;
}
