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

import com.fc.domain.Ejercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.EjercicioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class EjercicioController {

	@Autowired
	private EjercicioRepository ejercicioRepository;

	// LISTAR
	@GetMapping("/ejercicio")
	public List<Ejercicio> getAllEjercicios() {
		System.out.println(ejercicioRepository.findAll());
		return ejercicioRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/ejercicio/{id}")
	public ResponseEntity<Ejercicio> getEjercicioById(@PathVariable(value = "id") Long ejercicioId)
			throws ResourceNotFoundException {
		Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("Ejercicio not found on :: " + ejercicioId));
		return ResponseEntity.ok().body(ejercicio);
	}

	// BORRAR
	@DeleteMapping("/ejercicio/{id}")
	public Map<String, Boolean> deleteEjercicio(@PathVariable(value = "id") Long ejercicioId) throws Exception {
		Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("Ejercicio not found on :: " + ejercicioId));

		ejercicioRepository.delete(ejercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}