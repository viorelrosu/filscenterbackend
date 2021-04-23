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

import com.fc.domain.TipoSuscripcion;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.TipoSuscripcionService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoSuscripcionRESTController {

	@Autowired
	private TipoSuscripcionService tipoSuscripcionService;

	// LISTAR
	@GetMapping("/tipoSuscripcion")
	public List<TipoSuscripcion> getAllTipoSuscripcions() {
		return tipoSuscripcionService.getTipoSuscripcions();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoSuscripcion/{id}")
	public TipoSuscripcion getTipoSuscripcionById(@PathVariable(value = "id") Long tipoSuscripcionId)
			throws ResourceNotFoundException {
		return tipoSuscripcionService.getTipoSuscripcionById(tipoSuscripcionId);
	}

	// CREAR
	@PostMapping("/tipoSuscripcion")
	public TipoSuscripcion createTipoSuscripcion(@Valid @RequestBody TipoSuscripcion tipoSuscripcion) {
		return tipoSuscripcionService.saveTipoSuscripcion(tipoSuscripcion);
	}

	// ACTUALIZAR
	@PutMapping("/tipoSuscripcion/{id}")
	public TipoSuscripcion updateTipoSuscripcion(@PathVariable(value = "id") Long tipoSuscripcionId,
			@Valid @RequestBody TipoSuscripcion tipoSuscripcionDetails) throws ResourceNotFoundException {
		return tipoSuscripcionService.updateTipoSuscripcion(tipoSuscripcionId, tipoSuscripcionDetails);
	}

	// BORRAR
	@DeleteMapping("/tipoSuscripcion/{id}")
	public Map<String, Boolean> deleteTipoSuscripcion(@PathVariable(value = "id") Long tipoSuscripcionId)
			throws Exception {
		return tipoSuscripcionService.deleteTipoSuscripcion(tipoSuscripcionId);
	}

}
