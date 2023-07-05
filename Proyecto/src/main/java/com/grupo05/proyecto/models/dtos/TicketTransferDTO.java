package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TicketTransferDTO {
	@NotEmpty(message = "Ticket is required")
	private String ticket;
	
	@NotEmpty(message = "Old ticket's owner is required")
	private String oldOwner;
	
	@NotEmpty(message = "New ticket's owner is required")
	private String newOwner;
}
