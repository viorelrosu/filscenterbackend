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

import com.fc.domain.Sala;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SalaRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SalaController {

	@Autowired
	private SalaRepository salaRepository;

	// LISTAR
	@GetMapping("/sala")
	public List<Sala> getAllSalas() {
		System.out.println(salaRepository.findAll());
		return salaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/sala/{id}")
	public ResponseEntity<Sala> getSalaById(@PathVariable(value = "id") Long salaId)
			throws ResourceNotFoundException {
		Sala sala = salaRepository.findById(salaId)
				.orElseThrow(() -> new ResourceNotFoundException("Sala not found on :: " + salaId));
		return ResponseEntity.ok().body(sala);
	}

	// BORRAR
	@DeleteMapping("/sala/{id}")
	public Map<String, Boolean> deleteSala(@PathVariable(value = "id") Long salaId) throws Exception {
		Sala sala = salaRepository.findById(salaId)
				.orElseThrow(() -> new ResourceNotFoundException("Sala not found on :: " + salaId));

		salaRepository.delete(sala);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
