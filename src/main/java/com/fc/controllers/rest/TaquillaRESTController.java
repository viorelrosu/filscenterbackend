package com.fc.controllers.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.Taquilla;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.TaquillaService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TaquillaRESTController {

	@Autowired
	private TaquillaService taquillaService;

	// LISTAR
	@GetMapping("/taquilla")
	public List<Taquilla> getAllTaquillas() {
		return taquillaService.getTaquillas();
	}

	// RECUPERAR POR ID
	@GetMapping("/taquilla/{id}")
	public Taquilla getTaquillaById(@PathVariable(value = "id") Long taquillaId) throws ResourceNotFoundException {
		return taquillaService.getTaquillaById(taquillaId);
	}

	// CREAR
	@PostMapping("/taquilla")
	public Taquilla createTaquilla(@Valid @RequestBody Taquilla taquilla) {
		return taquillaService.saveTaquilla(taquilla);
	}

	// ACTUALIZAR
	@PutMapping("/taquilla/{id}")
	public Taquilla updateTaquilla(@PathVariable(value = "id") Long taquillaId,
			@Valid @RequestBody Taquilla taquillaDetails) throws ResourceNotFoundException {
		return taquillaService.updateTaquilla(taquillaId, taquillaDetails);
	}

	// BORRAR
	@DeleteMapping("/taquilla/{id}")
	public Map<String, Boolean> deleteTaquilla(@PathVariable(value = "id") Long taquillaId) throws Exception {
		return taquillaService.deleteTaquilla(taquillaId);
	}

}
