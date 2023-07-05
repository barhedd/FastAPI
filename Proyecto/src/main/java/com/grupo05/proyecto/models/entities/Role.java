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
@ToString(exclude = {"roleUsers"})
@Entity
@Table(name = "role")
public class Role {
	@Id
	@Column(name = "id_role")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	// relación N-N con la tabla "user"
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<RoleUser> roleUsers;
	
	public Role(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
}
