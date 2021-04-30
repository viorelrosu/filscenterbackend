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

import com.fc.domain.TipoActividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.TipoActividadService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoActividadRESTController {

	@Autowired
	private TipoActividadService tipoActividadService;

	// LISTAR
	@GetMapping("/tipoActividad")
	public List<TipoActividad> getAllTipoActividads() {
		return tipoActividadService.getTipoActividads();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoActividad/{id}")
	public TipoActividad getTipoActividadById(@PathVariable(value = "id") Long tipoActividadId)
			throws ResourceNotFoundException {
		return tipoActividadService.getTipoActividadById(tipoActividadId);
	}

	// CREAR
	@PostMapping("/tipoActividad")
	public TipoActividad createTipoActividad(@Valid @RequestBody TipoActividad tipoActividad) {
		return tipoActividadService.saveTipoActividad(tipoActividad);
	}

	// ACTUALIZAR
	@PutMapping("/tipoActividad/{id}")
	public TipoActividad updateTipoActividad(@PathVariable(value = "id") Long tipoActividadId,
			@Valid @RequestBody TipoActividad tipoActividadDetails) throws ResourceNotFoundException {
		return tipoActividadService.updateTipoActividad(tipoActividadId, tipoActividadDetails);
	}

	// BORRAR
	@DeleteMapping("/tipoActividad/{id}")
	public Map<String, Boolean> deleteTipoActividad(@PathVariable(value = "id") Long tipoActividadId) throws Exception {
		return tipoActividadService.deleteTipoActividad(tipoActividadId);
	}

}