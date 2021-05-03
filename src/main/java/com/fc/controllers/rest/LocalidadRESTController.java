package com.fc.controllers.rest;

import com.fc.domain.Localidad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.LocalidadService;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class LocalidadRESTController {

	@Autowired
	private LocalidadService localidadService;

	// LISTAR
	@GetMapping("/localidad")
	public List<Localidad> getAllLocalidades() {
		return localidadService.getAllLocalidades();
	}

	// RECUPERAR POR ID
	@GetMapping("/localidad/{id}")
	public Localidad getLocalidadById(@PathVariable(value = "id") Long localidadId) throws ResourceNotFoundException {
		return localidadService.getLocalidadById(localidadId);
	}

	// CREAR
	@PostMapping("/localidad")
	public Localidad createLocalidad(@Valid @RequestBody Localidad localidad) throws ResourceNotFoundException {
		return localidadService.saveLocalidad(localidad);
	}

	// ACTUALIZAR
	@PutMapping("/localidad/{id}")
	public Localidad updateLocalidad(@PathVariable(value = "id") Long localidadId,
			@Valid @RequestBody Localidad localidadDetails) throws ResourceNotFoundException {
		return localidadService.updateLocalidad(localidadId, localidadDetails);
	}

	// BORRAR
	@DeleteMapping("/localidad/{id}")
	public Map<String, Boolean> deleteLocalidad(@PathVariable(value = "id") Long localidadId) throws Exception {
		return localidadService.deleteLocalidad(localidadId);
	}

	// LISTAR PROVINCIAS EN UNA LOCALIDAD
	@GetMapping("/localidades/{id}")
		public List<Localidad> getLocalidadesByProvincia(@PathVariable(value = "id") Long provinciaId) throws ResourceNotFoundException {
			return localidadService.getLocalidadesByProvincia(provinciaId);
		}
	
	
}