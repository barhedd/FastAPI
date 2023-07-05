package com.grupo05.proyecto.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo05.proyecto.models.entities.Event;
import com.grupo05.proyecto.models.entities.EventDesignatedEmployee;
import com.grupo05.proyecto.models.entities.User;
import com.grupo05.proyecto.repositories.EventDesignatedEmployeeRepository;
import com.grupo05.proyecto.services.EventDesignatedEmployeeService;

import jakarta.transaction.Transactional;

@Service
public class EventDesignatedEmployeeServiceImpl implements EventDesignatedEmployeeService {

	@Autowired
	private EventDesignatedEmployeeRepository eventDesignatedEmployeeRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void create(Event event, User employee) throws Exception {
		EventDesignatedEmployee eventDesignatedEmployee = new EventDesignatedEmployee(
					event,
					employee
				);
		
		eventDesignatedEmployeeRepository.save(eventDesignatedEmployee);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void delete(String id) throws Exception {
		UUID code = UUID.fromString(id);
		eventDesignatedEmployeeRepository.deleteById(code);
	}

	@Override
	public EventDesignatedEmployee findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return eventDesignatedEmployeeRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<EventDesignatedEmployee> findAll() {
		return eventDesignatedEmployeeRepository.findAll();
	}

	@Override
	public List<EventDesignatedEmployee> findByEvent(Event event) {
		return eventDesignatedEmployeeRepository.findByEvent(event);
	}

}
