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
@ToString(exclude = {"localities", "eventEntitiesInvolved", "eventSponsors", "eventDesignatedEmployees"})
@Entity
@Table(name = "event")
public class Event {
	@Id
	@Column(name = "id_event")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "duration")
	private String duration;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	// relación N-1 con la tabla "place"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_place")
	private Place place;
	
	// relación N-1 con la tabla "category"
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_category")
	private Category category;
	
	// relación 1-N con la tabla "locality"
	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Locality> localities;
	
	// relación N-N con la tabla "entity_involved"
	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EventEntityInvolved> eventEntitiesInvolved;
	
	// relación N-N con la tabla "sponsor"
	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EventSponsor> eventSponsors;
	
	// relación N-N con la tabla "user"
	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EventDesignatedEmployee> eventDesignatedEmployees;
	
	public Event(String title, String image, Date date, String duration, Place place, Category category) {
		super();
		this.title = title;
		this.image = image;
		this.date = date;
		this.duration = duration;
		this.place = place;
		this.category = category;
	}
}
