package com.fc.controllers;

import com.fc.domain.Localidad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.LocalidadRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webservice")
public class LocalidadController {

    @Autowired
    private LocalidadRepository localidadRepository;
    
	// LISTAR
	@GetMapping("/localidades")
	public List<Localidad> getAllLocalidades() {
		return localidadRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/localidades/{id}")
	public ResponseEntity<Localidad> getLocalidadById(@PathVariable(value = "id") Long localidadId)
			throws ResourceNotFoundException {
		Localidad localidad = localidadRepository.findById(localidadId)
				.orElseThrow(() -> new ResourceNotFoundException("Localidad not found on :: " + localidadId));
		return ResponseEntity.ok().body(localidad);
	}

	// CREAR
	@PostMapping("/localidades")
	public Localidad createLocalidad(@Valid @RequestBody Localidad localidad) {
		return localidadRepository.save(localidad);
	}

	// ACTUALIZAR
	@PutMapping("/localidades/{id}")
	public ResponseEntity<Localidad> updateLocalidad(@PathVariable(value = "id") Long localidadId,
			@Valid @RequestBody Localidad localidadDetails) throws ResourceNotFoundException {

		Localidad localidad = localidadRepository.findById(localidadId)
				.orElseThrow(() -> new ResourceNotFoundException("Localidad not found on :: " + localidadId));

		localidad.setNombre(localidadDetails.getNombre());
		localidad.setProvincia(localidadDetails.getProvincia());
		final Localidad updatedLocalidad = localidadRepository.save(localidad);
 	    return ResponseEntity.ok(updatedLocalidad);
 	  }
 	
 	// BORRAR
 	@DeleteMapping("/localidades/{id}")
 	  public Map<String, Boolean> deleteLocalidad(@PathVariable(value = "id") Long localidadId) throws Exception {
 	    Localidad localidad =
 	        localidadRepository
 	            .findById(localidadId)
 	            .orElseThrow(() -> new ResourceNotFoundException("Localidad not found on :: " + localidadId));

 	    localidadRepository.delete(localidad);
 	    Map<String, Boolean> response = new HashMap<>();
 	    response.put("deleted", Boolean.TRUE);
 	    return response;
 	  }
 }