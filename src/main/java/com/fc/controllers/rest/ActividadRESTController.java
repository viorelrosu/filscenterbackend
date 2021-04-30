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

import com.fc.domain.Actividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.ActividadService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ActividadRESTController {

	@Autowired
	private ActividadService actividadService;

	// LISTAR
	@GetMapping("/actividad")
	public List<Actividad> getAllActividades() {
		return actividadService.getAllActividades();
	}

	// RECUPERAR POR ID
	@GetMapping("/actividad/{id}")
	public Actividad getActividadById(@PathVariable(value = "id") Long actividadId) throws ResourceNotFoundException {
		return actividadService.getActividadById(actividadId);
	}

	// CREAR
	@PostMapping("/actividad")
	public Actividad createActividad(@Valid @RequestBody Actividad actividad) throws ResourceNotFoundException {
		return actividadService.createActividad(actividad);
	}

	// ACTUALIZAR
	@PutMapping("/actividad/{id}")
	public Actividad updateActividad(@PathVariable(value = "id") Long actividadId,
			@Valid @RequestBody Actividad actividadDetails) throws ResourceNotFoundException {
		return actividadService.updateActividad(actividadId, actividadDetails);
	}

	// BORRAR
	@DeleteMapping("/actividad/{id}")
	public Map<String, Boolean> deleteActividad(@PathVariable(value = "id") Long actividadId) throws Exception {
		return actividadService.deleteActividad(actividadId);
	}

}
