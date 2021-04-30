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

import com.fc.domain.Sala;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.SalaService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SalaRESTController {

	@Autowired
	private SalaService salaService;

	// LISTAR
	@GetMapping("/sala")
	public List<Sala> getAllSalas() {
		return salaService.getSalas();
	}

	// RECUPERAR POR ID
	@GetMapping("/sala/{id}")
	public Sala getSalaById(@PathVariable(value = "id") Long salaId) throws ResourceNotFoundException {
		return salaService.getSalaById(salaId);
	}

	// CREAR
	@PostMapping("/sala")
	public Sala createSala(@Valid @RequestBody Sala sala) {
		return salaService.saveSala(sala);
	}

	// ACTUALIZAR
	@PutMapping("/sala/{id}")
	public Sala updateSala(@PathVariable(value = "id") Long salaId, @Valid @RequestBody Sala salaDetails)
			throws ResourceNotFoundException {
		return salaService.updateSala(salaId, salaDetails);
	}

	// BORRAR
	@DeleteMapping("/sala/{id}")
	public Map<String, Boolean> deleteSala(@PathVariable(value = "id") Long salaId) throws Exception {
		return salaService.deleteSala(salaId);
	}

}
