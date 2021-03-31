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

import com.fc.domain.TipoSuscripcion;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoSuscripcionRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoSuscripcionRESTController {

	@Autowired
	private TipoSuscripcionRepository tipoSuscripcionRepository;

	// LISTAR
	@GetMapping("/tipoSuscripcion")
	public List<TipoSuscripcion> getAllTiposSuscripciones() {
		return tipoSuscripcionRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoSuscripcion/{id}")
	public ResponseEntity<TipoSuscripcion> getTipoSuscripcionById(@PathVariable(value = "id") Long tipoSuscripcionId)
			throws ResourceNotFoundException {
		TipoSuscripcion tipoSuscripcion = encontrarTipoSuscripcionPorId(tipoSuscripcionId);
		return ResponseEntity.ok().body(tipoSuscripcion);
	}

	// CREAR
	@PostMapping("/tipoSuscripcion")
	public TipoSuscripcion createTipoSuscripcion(@Valid @RequestBody TipoSuscripcion tipoSuscripcion) {
		return tipoSuscripcionRepository.save(tipoSuscripcion);
	}

	// ACTUALIZAR
	@PutMapping("/tipoSuscripcion/{id}")
	public ResponseEntity<TipoSuscripcion> updateTipoSuscripcion(@PathVariable(value = "id") Long tipoSuscripcionId,
			@Valid @RequestBody TipoSuscripcion tipoSuscripcionDetails) throws ResourceNotFoundException {
		TipoSuscripcion tipoSuscripcion = encontrarTipoSuscripcionPorId(tipoSuscripcionId);
		tipoSuscripcion.setNombre(tipoSuscripcionDetails.getNombre());
		tipoSuscripcion.setDuracion(tipoSuscripcionDetails.getDuracion());
		tipoSuscripcion.setTarifa(tipoSuscripcionDetails.getTarifa());
		final TipoSuscripcion updatedTipoSuscripcion = tipoSuscripcionRepository.save(tipoSuscripcion);
		return ResponseEntity.ok(updatedTipoSuscripcion);
	}

	// BORRAR
	@DeleteMapping("/tipoSuscripcion/{id}")
	public Map<String, Boolean> deleteTipoSuscripcion(@PathVariable(value = "id") Long tipoSuscripcionId)
			throws Exception {
		TipoSuscripcion tipoSuscripcion = encontrarTipoSuscripcionPorId(tipoSuscripcionId);
		tipoSuscripcionRepository.delete(tipoSuscripcion);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public TipoSuscripcion encontrarTipoSuscripcionPorId(Long tipoSuscripcionId) throws ResourceNotFoundException {
		TipoSuscripcion tipoSuscripcion = tipoSuscripcionRepository.findById(tipoSuscripcionId).orElseThrow(
				() -> new ResourceNotFoundException("TipoSuscripcion not found on :: " + tipoSuscripcionId));
		return tipoSuscripcion;
	}

}
