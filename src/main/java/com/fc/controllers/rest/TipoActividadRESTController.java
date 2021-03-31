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

import com.fc.domain.TipoActividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoActividadRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoActividadRESTController {

	@Autowired
	private TipoActividadRepository tipoActividadRepository;

	// LISTAR
	@GetMapping("/tipoActividad")
	public List<TipoActividad> getAllTiposActividades() {
		return tipoActividadRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoActividad/{id}")
	public ResponseEntity<TipoActividad> getTipoActividadById(@PathVariable(value = "id") Long tipoActividadId)
			throws ResourceNotFoundException {
		TipoActividad tipoActividad = encontrarTipoActividadPorId(tipoActividadId);
		return ResponseEntity.ok().body(tipoActividad);
	}

	// CREAR
	@PostMapping("/tipoActividad")
	public TipoActividad createTipoActividad(@Valid @RequestBody TipoActividad tipoActividad) {
		return tipoActividadRepository.save(tipoActividad);
	}

	// ACTUALIZAR
	@PutMapping("/tipoActividad/{id}")
	public ResponseEntity<TipoActividad> updateTipoActividad(@PathVariable(value = "id") Long tipoActividadId,
			@Valid @RequestBody TipoActividad tipoActividadDetails) throws ResourceNotFoundException {
		TipoActividad tipoActividad = encontrarTipoActividadPorId(tipoActividadId);
		tipoActividad.setNombre(tipoActividadDetails.getNombre());
		final TipoActividad updatedTipoActividad = tipoActividadRepository.save(tipoActividad);
		return ResponseEntity.ok(updatedTipoActividad);
	}

	// BORRAR
	@DeleteMapping("/tipoActividad/{id}")
	public Map<String, Boolean> deleteTipoActividad(@PathVariable(value = "id") Long tipoActividadId) throws Exception {
		TipoActividad tipoActividad = encontrarTipoActividadPorId(tipoActividadId);
		tipoActividadRepository.delete(tipoActividad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public TipoActividad encontrarTipoActividadPorId(Long tipoActividadId) throws ResourceNotFoundException {
		TipoActividad tipoActividad = tipoActividadRepository.findById(tipoActividadId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoActividad not found on :: " + tipoActividadId));
		return tipoActividad;
	}

}