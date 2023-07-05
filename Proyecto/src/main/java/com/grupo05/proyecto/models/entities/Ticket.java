package com.grupo05.proyecto.models.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = {"ticketTransfers", "ticketExchanges"})
@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@Column(name = "id_ticket")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "is_sold")
	private Boolean isSold;
	
	@Column(name = "date_purcharse")
	private Date datePurchase;
	
	// relación N-1 con la tabla "locality"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_locality")
	private Locality locality;
	
	// relación N-1 con la tabla "user"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_buyer")
	private User buyer;
	
	// relación N-1 con la tabla "user"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_owner")
	private User owner;
	
	// relación 1-N con la tabla "ticket_transfer"
	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TicketTransfer> ticketTransfers;
	
	// relación 1-N con la tabla "ticket_exchange"
	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TicketExchange> ticketExchanges;
	
	public Ticket(Locality locality) {
		super();
		this.isSold = false;
		this.locality = locality;
		this.buyer = null;
		this.owner = null;
		this.datePurchase = null;
	}
}
