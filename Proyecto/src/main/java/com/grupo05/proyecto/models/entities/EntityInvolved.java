package com.grupo05.proyecto.models.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = {"eventEntitiesInvolved"})
@Entity
@Table(name = "entity_involved")
public class EntityInvolved {
	@Id
	@Column(name = "id_entity_involved")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	// relación N-N con la tabla "event"
	@OneToMany(mappedBy = "entityInvolved", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EventEntityInvolved> eventEntitiesInvolved;
	
	public EntityInvolved(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
}
