package com.fc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.Actividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ActividadRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ActividadController {

	@Autowired
	private ActividadRepository actividadRepository;

	// LISTAR
	@GetMapping("/actividad")
	public List<Actividad> getAllActividades() {
		System.out.println(actividadRepository.findAll());
		return actividadRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/actividad/{id}")
	public ResponseEntity<Actividad> getActividadById(@PathVariable(value = "id") Long actividadId)
			throws ResourceNotFoundException {
		Actividad actividad = actividadRepository.findById(actividadId)
				.orElseThrow(() -> new ResourceNotFoundException("Actividad not found on :: " + actividadId));
		return ResponseEntity.ok().body(actividad);
	}

	// BORRAR
	@DeleteMapping("/actividad/{id}")
	public Map<String, Boolean> deleteActividad(@PathVariable(value = "id") Long actividadId) throws Exception {
		Actividad actividad = actividadRepository.findById(actividadId)
				.orElseThrow(() -> new ResourceNotFoundException("Actividad not found on :: " + actividadId));

		actividadRepository.delete(actividad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
