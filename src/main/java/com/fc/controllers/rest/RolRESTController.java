package com.fc.controllers.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.fc.repositories.RolRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class RolRESTController {

	@Autowired
	private RolRepository rolRepository;

	// LISTAR
	@GetMapping("/rol")
	public List<Rol> getAllRols() {
		return rolRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/rol/{id}")
	public ResponseEntity<Rol> getRolById(@PathVariable(value = "id") Long rolId) throws ResourceNotFoundException {
		Rol rol = encontrarRolPorId(rolId);
		return ResponseEntity.ok().body(rol);
	}

	// CREAR
	@PostMapping("/rol")
	public Rol createRol(@Valid @RequestBody Rol rol) {
		return rolRepository.save(rol);
	}

	// ACTUALIZAR
	@PutMapping("/rol/{id}")
	public ResponseEntity<Rol> updateRol(@PathVariable(value = "id") Long rolId, @Valid @RequestBody Rol rolDetails)
			throws ResourceNotFoundException {
		Rol rol = encontrarRolPorId(rolId);
		rol.setNombre(rolDetails.getNombre());
		final Rol updatedRol = rolRepository.save(rol);
		return ResponseEntity.ok(updatedRol);
	}

	// BORRAR
	@DeleteMapping("/rol/{id}")
	public Map<String, Boolean> deleteRol(@PathVariable(value = "id") Long rolId) throws Exception {
		Rol rol = encontrarRolPorId(rolId);
		rolRepository.delete(rol);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Rol encontrarRolPorId(Long rolId) throws ResourceNotFoundException {
		Rol rol = rolRepository.findById(rolId)
				.orElseThrow(() -> new ResourceNotFoundException("Rol not found on :: " + rolId));
		return rol;
	}

}