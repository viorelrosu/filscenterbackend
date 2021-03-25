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

import com.fc.domain.Taquilla;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TaquillaRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TaquillaController {

	@Autowired
	private TaquillaRepository taquillaRepository;

	// LISTAR
	@GetMapping("/taquilla")
	public List<Taquilla> getAllTaquillas() {
		System.out.println(taquillaRepository.findAll());
		return taquillaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/taquilla/{id}")
	public ResponseEntity<Taquilla> getTaquillaById(@PathVariable(value = "id") Long taquillaId)
			throws ResourceNotFoundException {
		Taquilla taquilla = taquillaRepository.findById(taquillaId)
				.orElseThrow(() -> new ResourceNotFoundException("Taquilla not found on :: " + taquillaId));
		return ResponseEntity.ok().body(taquilla);
	}

	// BORRAR
	@DeleteMapping("/taquilla/{id}")
	public Map<String, Boolean> deleteTaquilla(@PathVariable(value = "id") Long taquillaId) throws Exception {
		Taquilla taquilla = taquillaRepository.findById(taquillaId)
				.orElseThrow(() -> new ResourceNotFoundException("Taquilla not found on :: " + taquillaId));

		taquillaRepository.delete(taquilla);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
