package com.grupo05.proyecto.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = {"ticketsBuyed", "ticketsOwn", "roleUsers", "eventDesignatedEmployees", 
		"transferredTickets", "ticketsReceived", "ticketsExchanged", "ticketsValidated"})
@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "isActive")
	private Boolean isActive;
	
	@Column(name = "validated_account")
	private Boolean validatedAccount;
	
	// relacion N-N con la tabla "role"
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<RoleUser> roleUsers;
	
	// relación N-N con la tabla "event"
	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EventDesignatedEmployee> eventDesignatedEmployees;
	
	// relación 1-N con la tabla "ticket"
	@OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> ticketsBuyed;
	
	// relación 1-N con la tabla "ticket"
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> ticketsOwn;
	
	// relación 1-N con la tabla "ticket_transfer"
	@OneToMany(mappedBy = "oldOwner", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TicketTransfer> transferredTickets;
	
	// relación 1-N con la tabla "ticket_transfer"
	@OneToMany(mappedBy = "newOwner", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TicketTransfer> ticketsReceived;
	
	// relación 1-N con la tabla "ticket_exchange"
	@OneToMany(mappedBy = "ticketOwner", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TicketExchange> ticketsExchanged;
	
	// relación 1-N con la tabla "ticket_exchange"
	@OneToMany(mappedBy = "validatorEmployee", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TicketExchange> ticketsValidated;
	
	public User(String name, String email) {
		this.name = name;
		this.email = email;
		this.password = null;
		this.isActive = false;
		this.validatedAccount = false;
	}
}
