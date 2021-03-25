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

import com.fc.domain.TipoActividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoActividadRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoActividadController {

	@Autowired
	private TipoActividadRepository tipoActividadRepository;

	// LISTAR
	@GetMapping("/tipoActividad")
	public List<TipoActividad> getAllTiposActividades() {
		System.out.println(tipoActividadRepository.findAll());
		return tipoActividadRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoActividad/{id}")
	public ResponseEntity<TipoActividad> getTipoActividadById(@PathVariable(value = "id") Long tipoActividadId)
			throws ResourceNotFoundException {
		TipoActividad tipoActividad = tipoActividadRepository.findById(tipoActividadId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoActividad not found on :: " + tipoActividadId));
		return ResponseEntity.ok().body(tipoActividad);
	}

	// BORRAR
	@DeleteMapping("/tipoActividad/{id}")
	public Map<String, Boolean> deleteTipoActividad(@PathVariable(value = "id") Long tipoActividadId) throws Exception {
		TipoActividad tipoActividad = tipoActividadRepository.findById(tipoActividadId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoActividad not found on :: " + tipoActividadId));

		tipoActividadRepository.delete(tipoActividad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}