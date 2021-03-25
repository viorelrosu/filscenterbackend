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

import com.fc.domain.TipoSuscripcion;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoSuscripcionRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoSuscripcionRESTController {

	@Autowired
	private TipoSuscripcionRepository tipoSuscripcionRepository;

	// LISTAR
	@GetMapping("/tipoSuscripcion")
	public List<TipoSuscripcion> getAllTiposSuscripciones() {
		System.out.println(tipoSuscripcionRepository.findAll());
		return tipoSuscripcionRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoSuscripcion/{id}")
	public ResponseEntity<TipoSuscripcion> getTipoSuscripcionById(@PathVariable(value = "id") Long tipoSuscripcionId)
			throws ResourceNotFoundException {
		TipoSuscripcion tipoSuscripcion = tipoSuscripcionRepository.findById(tipoSuscripcionId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoSuscripcion not found on :: " + tipoSuscripcionId));
		return ResponseEntity.ok().body(tipoSuscripcion);
	}

	// BORRAR
	@DeleteMapping("/tipoSuscripcion/{id}")
	public Map<String, Boolean> deleteTipoSuscripcion(@PathVariable(value = "id") Long tipoSuscripcionId) throws Exception {
		TipoSuscripcion tipoSuscripcion = tipoSuscripcionRepository.findById(tipoSuscripcionId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoSuscripcion not found on :: " + tipoSuscripcionId));

		tipoSuscripcionRepository.delete(tipoSuscripcion);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
