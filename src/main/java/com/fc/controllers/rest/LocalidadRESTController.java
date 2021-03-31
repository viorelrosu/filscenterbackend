package com.fc.controllers.rest;

import com.fc.domain.Localidad;
import com.fc.domain.Provincia;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.LocalidadRepository;
import com.fc.repositories.ProvinciaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webservice")
public class LocalidadRESTController {

	@Autowired
	private LocalidadRepository localidadRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;

	// LISTAR
	@GetMapping("/localidad")
	public List<Localidad> getAllLocalidades() {
		return localidadRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/localidad/{id}")
	public ResponseEntity<Localidad> getLocalidadById(@PathVariable(value = "id") Long localidadId)
			throws ResourceNotFoundException {
		Localidad localidad = encontrarLocalidadPorId(localidadId);
		return ResponseEntity.ok().body(localidad);
	}

	// CREAR
	@PostMapping("/localidad")
	public Localidad createLocalidad(@Valid @RequestBody Localidad localidad) throws ResourceNotFoundException {
		Provincia provincia = encontrarProvinciaPorId(localidad.getProvincia().getId());
		localidad.setProvincia(provincia);
		provincia.getLocalidades().add(localidad);
		return localidadRepository.save(localidad);
	}

	// ACTUALIZAR
	@PutMapping("/localidad/{id}")
	public ResponseEntity<Localidad> updateLocalidad(@PathVariable(value = "id") Long localidadId,
			@Valid @RequestBody Localidad localidadDetails) throws ResourceNotFoundException {

		Localidad localidad = encontrarLocalidadPorId(localidadId);
		localidad.setNombre(localidadDetails.getNombre());
		Provincia provincia = localidad.getProvincia();
		provincia.getLocalidades().remove(localidad);
		provincia = encontrarProvinciaPorId(localidadDetails.getProvincia().getId());
		localidad.setProvincia(provincia);
		provincia.getLocalidades().add(localidad);
		final Localidad updatedLocalidad = localidadRepository.save(localidad);
		return ResponseEntity.ok(updatedLocalidad);
	}

	// BORRAR
	@DeleteMapping("/localidad/{id}")
	public Map<String, Boolean> deleteLocalidad(@PathVariable(value = "id") Long localidadId) throws Exception {
		Localidad localidad = encontrarLocalidadPorId(localidadId);
		localidadRepository.delete(localidad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Localidad encontrarLocalidadPorId(Long localidadId) throws ResourceNotFoundException {
		Localidad localidad = localidadRepository.findById(localidadId)
				.orElseThrow(() -> new ResourceNotFoundException("Localidad not found on :: " + localidadId));
		return localidad;
	}

	public Provincia encontrarProvinciaPorId(Long provinciaId) throws ResourceNotFoundException {
		Provincia provincia = provinciaRepository.findById(provinciaId)
				.orElseThrow(() -> new ResourceNotFoundException("Provincia not found on :: " + provinciaId));
		return provincia;
	}

}