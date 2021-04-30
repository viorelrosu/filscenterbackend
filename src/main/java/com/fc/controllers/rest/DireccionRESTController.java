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

import com.fc.domain.Direccion;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.DireccionService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class DireccionRESTController {
	@Autowired
	DireccionService direccionService;

	// LISTAR
	@GetMapping("/direccion")
	public List<Direccion> getAllDirecciones() {
		return direccionService.getAllDirecciones();
	}

	// RECUPERAR POR ID
	@GetMapping("/direccion/{id}")
	public Direccion getDireccionById(@PathVariable(value = "id") Long direccionId) throws ResourceNotFoundException {
		return direccionService.getDireccionById(direccionId);
	}

	// CREAR
	@PostMapping("/direccion")
	public Direccion createDireccion(@Valid @RequestBody Direccion direccion) throws ResourceNotFoundException {
		return direccionService.saveDireccion(direccion);
	}

	// ACTUALIZAR
	@PutMapping("/direccion/{id}")
	public Direccion updateDireccion(@PathVariable(value = "id") Long direccionId,
			@Valid @RequestBody Direccion direccionDetails) throws ResourceNotFoundException {
		return direccionService.updateDireccion(direccionId, direccionDetails);
	}

	// BORRAR
	@DeleteMapping("/direccion/{id}")
	public Map<String, Boolean> deleteDireccion(@PathVariable(value = "id") Long direccionId) throws Exception {
		return direccionService.deleteDireccion(direccionId);
	}

}
