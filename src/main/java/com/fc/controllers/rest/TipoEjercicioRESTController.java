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

import com.fc.domain.TipoEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoEjercicioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoEjercicioRESTController {

	@Autowired
	private TipoEjercicioRepository tipoEjercicioRepository;

	// LISTAR
	@GetMapping("/tipoEjercicio")
	public List<TipoEjercicio> getAllTipoEjercicios() {
		return tipoEjercicioRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoEjercicio/{id}")
	public ResponseEntity<TipoEjercicio> getTipoEjercicioById(@PathVariable(value = "id") Long tipoEjercicioId)
			throws ResourceNotFoundException {
		TipoEjercicio tipoEjercicio = encontrarTipoEjercicioPorId(tipoEjercicioId);
		return ResponseEntity.ok().body(tipoEjercicio);
	}

	// CREAR
	@PostMapping("/tipoEjercicio")
	public TipoEjercicio createTipoEjercicio(@Valid @RequestBody TipoEjercicio tipoEjercicio) {
		return tipoEjercicioRepository.save(tipoEjercicio);
	}

	// ACTUALIZAR
	@PutMapping("/tipoEjercicio/{id}")
	public ResponseEntity<TipoEjercicio> updateTipoEjercicio(@PathVariable(value = "id") Long tipoEjercicioId,
			@Valid @RequestBody TipoEjercicio tipoEjercicioDetails) throws ResourceNotFoundException {
		TipoEjercicio tipoEjercicio = encontrarTipoEjercicioPorId(tipoEjercicioId);
		tipoEjercicio.setNombre(tipoEjercicioDetails.getNombre());
		final TipoEjercicio updatedTipoEjercicio = tipoEjercicioRepository.save(tipoEjercicio);
		return ResponseEntity.ok(updatedTipoEjercicio);
	}

	// BORRAR
	@DeleteMapping("/tipoEjercicio/{id}")
	public Map<String, Boolean> deleteTipoEjercicio(@PathVariable(value = "id") Long tipoEjercicioId) throws Exception {
		TipoEjercicio tipoEjercicio = encontrarTipoEjercicioPorId(tipoEjercicioId);
		tipoEjercicioRepository.delete(tipoEjercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public TipoEjercicio encontrarTipoEjercicioPorId(Long tipoEjercicioId) throws ResourceNotFoundException {
		TipoEjercicio tipoEjercicio = tipoEjercicioRepository.findById(tipoEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoEjercicio not found on :: " + tipoEjercicioId));
		return tipoEjercicio;
	}

}
