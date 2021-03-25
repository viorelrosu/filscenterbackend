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

import com.fc.domain.Suscripcion;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SuscripcionRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SuscripcionRESTController {

	@Autowired
	private SuscripcionRepository suscripcionRepository;

	// LISTAR
	@GetMapping("/suscripcion")
	public List<Suscripcion> getAllSuscripciones() {
		System.out.println(suscripcionRepository.findAll());
		return suscripcionRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/suscripcion/{id}")
	public ResponseEntity<Suscripcion> getSuscripcionById(@PathVariable(value = "id") Long suscripcionId)
			throws ResourceNotFoundException {
		Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId)
				.orElseThrow(() -> new ResourceNotFoundException("Suscripcion not found on :: " + suscripcionId));
		return ResponseEntity.ok().body(suscripcion);
	}

	// BORRAR
	@DeleteMapping("/suscripcion/{id}")
	public Map<String, Boolean> deleteSuscripcion(@PathVariable(value = "id") Long suscripcionId) throws Exception {
		Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId)
				.orElseThrow(() -> new ResourceNotFoundException("Suscripcion not found on :: " + suscripcionId));

		suscripcionRepository.delete(suscripcion);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}