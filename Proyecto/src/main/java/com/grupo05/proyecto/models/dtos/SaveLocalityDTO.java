package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveLocalityDTO {
	@NotEmpty(message = "Name is required")
	private String name;
	
	@NotEmpty(message = "Price is required")
	private Float price;
	
	@NotEmpty(message = "Capacity is required")
	private Short capacity;
	
	@NotEmpty(message = "Event is required")
	private String event;
}
