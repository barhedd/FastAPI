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
@Table(name = "ticket_transfer")
public class TicketTransfer {
	@Id
	@Column(name = "id_ticket_transfer")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "validation_code")
	private String validationCode;
	
	@Column(name = "code_validity_date")
	private Date codeValidityDate;
	
	@Column(name = "validated_email")
	private Boolean validatedEmail;
	
	@Column(name = "date_transfer")
	private Date dateTransfer;
	
	@Column(name = "executed_transfer")
	private Boolean executedTransfer;
	
	// relación N-1 con la tabla "ticket"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ticket")
	private Ticket ticket;
	
	// relación N-1 con la tabla "user"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "old_owner")
	private User oldOwner;
	
	// relación N-1 con la tabla "user"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "new_owner")
	private User newOwner;
	
	public TicketTransfer(String validationCode, Date codeValidityDate, Ticket ticket, User oldOwner, User newOwner) {
		super();
		this.validationCode = validationCode;
		this.codeValidityDate = codeValidityDate;
		this.validatedEmail = null;
		this.dateTransfer = null;
		this.executedTransfer = false;
		this.ticket = ticket;
		this.oldOwner = oldOwner;
		this.newOwner = newOwner;
	}
}
