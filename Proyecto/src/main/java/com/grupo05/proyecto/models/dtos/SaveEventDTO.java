package com.grupo05.proyecto.models.dtos;

import java.util.Date;
import java.util.List;

import com.grupo05.proyecto.models.entities.Sponsor;
import com.grupo05.proyecto.models.entities.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveEventDTO {
	@NotEmpty(message = "Title is required")
	private String title;
	
	@NotEmpty(message = "Image is required")
	private String image;
	
	@NotEmpty(message = "Date is required")
	private Date date;
	
	@NotEmpty(message = "Duration is required")
	private String duration;
	
	@NotEmpty(message = "Place is required")
	private String place;	

	@NotEmpty(message = "Category is required")
	private String category;
	
	private List<String> entitiesInvolved;
	
	private List<Sponsor> sponsors;
	
	private List<User> employeesDesignated;
}
