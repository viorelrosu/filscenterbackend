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

import com.fc.domain.Suscripcion;
import com.fc.exceptions.ResourceNotFoundException;

import com.fc.services.SuscripcionService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SuscripcionRESTController {

	@Autowired
	private SuscripcionService suscripcionService;

	// LISTAR
	@GetMapping("/suscripcion")
	public List<Suscripcion> getAllSuscripcions() {
		return suscripcionService.getSuscripciones();
	}

	// RECUPERAR POR ID
	@GetMapping("/suscripcion/{id}")
	public Suscripcion getSuscripcionById(@PathVariable(value = "id") Long suscripcionId)
			throws ResourceNotFoundException {
		return suscripcionService.getSuscripcionById(suscripcionId);
	}

	// CREAR
	@PostMapping("/suscripcion")
	public Suscripcion createSuscripcion(@Valid @RequestBody Suscripcion suscripcion) throws ResourceNotFoundException {
		return suscripcionService.saveSuscripcion(suscripcion);
	}

	// ACTUALIZAR
	@PutMapping("/suscripcion/{id}")
	public Suscripcion updateSuscripcion(@PathVariable(value = "id") Long suscripcionId,
			@Valid @RequestBody Suscripcion suscripcionDetails) throws ResourceNotFoundException {
		return suscripcionService.updateSuscripcion(suscripcionId, suscripcionDetails);
	}

	// BORRAR
	@DeleteMapping("/suscripcion/{id}")
	public Map<String, Boolean> deleteSuscripcion(@PathVariable(value = "id") Long suscripcionId) throws Exception {
		return suscripcionService.deleteSuscripcion(suscripcionId);
	}
	
	// LISTAR SUSCRIPCIONES POR USUARIO
	@GetMapping("/suscripcion/usuario/{id}")
	public List<Suscripcion> getSuscripcionesByUsuario(@PathVariable(value = "id") Long usuarioId)
			throws ResourceNotFoundException {
		return suscripcionService.getSuscripcionesByUsuario(usuarioId);
	}

}