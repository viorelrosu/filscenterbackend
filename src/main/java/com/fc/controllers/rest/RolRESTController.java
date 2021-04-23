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

import com.fc.domain.Rol;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.RolService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class RolRESTController {

	@Autowired
	private RolService rolService;

	// LISTAR
	@GetMapping("/rol")
	public List<Rol> getAllRols() {
		return rolService.getRols();
	}

	// RECUPERAR POR ID
	@GetMapping("/rol/{id}")
	public Rol getRolById(@PathVariable(value = "id") Long rolId) throws ResourceNotFoundException {
		return rolService.getRolById(rolId);
	}

	// CREAR
	@PostMapping("/rol")
	public Rol createRol(@Valid @RequestBody Rol rol) {
		return rolService.saveRol(rol);
	}

	// ACTUALIZAR
	@PutMapping("/rol/{id}")
	public Rol updateRol(@PathVariable(value = "id") Long rolId, @Valid @RequestBody Rol rolDetails)
			throws ResourceNotFoundException {
		return rolService.updateRol(rolId, rolDetails);
	}

	// BORRAR
	@DeleteMapping("/rol/{id}")
	public Map<String, Boolean> deleteRol(@PathVariable(value = "id") Long rolId) throws Exception {
		return rolService.deleteRol(rolId);
	}

}