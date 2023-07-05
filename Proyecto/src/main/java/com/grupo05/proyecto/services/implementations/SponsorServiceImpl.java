package com.grupo05.proyecto.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.dtos.SaveSponsorDTO;
import com.grupo05.proyecto.models.entities.Sponsor;
import com.grupo05.proyecto.repositories.SponsorRepository;
import com.grupo05.proyecto.services.SponsorService;

import jakarta.transaction.Transactional;

@Service
public class SponsorServiceImpl implements SponsorService {

	@Autowired
	private SponsorRepository sponsorRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(SaveSponsorDTO info) throws Exception {
		Sponsor sponsor = new Sponsor(
					info.getCode(),
					info.getName()
				);
		
		sponsorRepository.save(sponsor);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void update(Sponsor sponsor) throws Exception {
		sponsorRepository.save(sponsor);		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		sponsorRepository.deleteById(id);	
	}

	@Override
	public Sponsor findOneById(String id) {
		try {
			return sponsorRepository.findById(id)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Sponsor> findAll() {
		return sponsorRepository.findAll();
	}
}
