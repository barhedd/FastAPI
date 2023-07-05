package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TicketExchangeDTO {
	@NotEmpty(message = "Ticket is required")
	private String ticket;
	
	@NotEmpty(message = "Ticket's owner is required")
	private String ticketOwner;
}
