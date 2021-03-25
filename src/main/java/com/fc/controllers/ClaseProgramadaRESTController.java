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

import com.fc.domain.ClaseProgramada;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ClaseProgramadaRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ClaseProgramadaRESTController {

	@Autowired
	private ClaseProgramadaRepository claseProgramadaRepository;

	// LISTAR
	@GetMapping("/claseProgramada")
	public List<ClaseProgramada> getAllClasesProgramadas() {
		System.out.println(claseProgramadaRepository.findAll());
		return claseProgramadaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/claseProgramada/{id}")
	public ResponseEntity<ClaseProgramada> getClaseProgramadaById(@PathVariable(value = "id") Long claseProgramadaId)
			throws ResourceNotFoundException {
		ClaseProgramada claseProgramada = claseProgramadaRepository.findById(claseProgramadaId)
				.orElseThrow(() -> new ResourceNotFoundException("ClaseProgramada not found on :: " + claseProgramadaId));
		return ResponseEntity.ok().body(claseProgramada);
	}

	// BORRAR
	@DeleteMapping("/claseProgramada/{id}")
	public Map<String, Boolean> deleteClaseProgramada(@PathVariable(value = "id") Long claseProgramadaId) throws Exception {
		ClaseProgramada claseProgramada = claseProgramadaRepository.findById(claseProgramadaId)
				.orElseThrow(() -> new ResourceNotFoundException("ClaseProgramada not found on :: " + claseProgramadaId));

		claseProgramadaRepository.delete(claseProgramada);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
