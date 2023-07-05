package com.grupo05.proyecto.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveEventDesignatedEmployeeDTO {
	@NotEmpty(message = "Event's code is required")
	private String event;
	
	@NotEmpty(message = "Employee's code is required")
	private String employee;
}
