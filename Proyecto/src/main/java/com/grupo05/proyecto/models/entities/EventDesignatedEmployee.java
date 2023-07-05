package com.grupo05.proyecto.models.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "event_designated_employee")
public class EventDesignatedEmployee {
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_event")
	private Event event;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_employee")
	private User employee;
	
	public EventDesignatedEmployee(Event event, User employee) {
		super();
		this.event = event;
		this.employee = employee;
	}
}
