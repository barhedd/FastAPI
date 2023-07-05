package com.grupo05.proyecto.models.entities;

import java.util.Date;
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
@Table(name = "ticket_exchange")
public class TicketExchange {
	@Id
	@Column(name = "id_ticket_exchange")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "validation_hash")
	private String validationHash;
	
	@Column(name = "hash_validity_date")
	private Date hashValidityDate;
	
	@Column(name = "date_exchange")
	private Date dateExchange;
	
	@Column(name = "executed_exchange")
	private Boolean executedExchange;
	
	// relación N-1 con la tabla "ticket"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ticket")
	private Ticket ticket;
	
	// relación N-1 con la tabla "user"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ticket_owner")
	private User ticketOwner;
	
	// relación N-1 con la tabla "user"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_validator_employee")
	private User validatorEmployee;
	
	public TicketExchange(String validationHash, Date hashValidityDate, Ticket ticket, User ticketOwner) {
		super();
		this.validationHash = validationHash;
		this.hashValidityDate = hashValidityDate;
		this.dateExchange = null;
		this.executedExchange = false;
		this.ticket = ticket;
		this.ticketOwner = ticketOwner;
		this.validatorEmployee = null;
	}
}
