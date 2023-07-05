package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TicketToggleOwnerDTO {
	@NotEmpty(message = "Owner is required")
	private String owner;
}
