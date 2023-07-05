package com.grupo05.proyecto.services;

import java.util.List;

import com.grupo05.proyecto.models.dtos.SaveSponsorDTO;
import com.grupo05.proyecto.models.entities.Sponsor;

public interface SponsorService {
	void create(SaveSponsorDTO info) throws Exception;
	void update(Sponsor sponsor) throws Exception;
	void deleteById(String id) throws Exception;
	Sponsor findOneById(String id);
	List<Sponsor> findAll();
}
