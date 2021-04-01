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

import com.fc.domain.Ejercicio;
import com.fc.domain.TipoEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.EjercicioRepository;
import com.fc.repositories.TipoEjercicioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class EjercicioRESTController {

	@Autowired
	private EjercicioRepository ejercicioRepository;
	@Autowired
	private TipoEjercicioRepository tipoEjercicioRepository;

	// LISTAR
	@GetMapping("/ejercicio")
	public List<Ejercicio> getAllEjercicioes() {
		return ejercicioRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/ejercicio/{id}")
	public ResponseEntity<Ejercicio> getEjercicioById(@PathVariable(value = "id") Long ejercicioId)
			throws ResourceNotFoundException {
		Ejercicio ejercicio = encontrarEjercicioPorId(ejercicioId);
		return ResponseEntity.ok().body(ejercicio);
	}

	// CREAR
	@PostMapping("/ejercicio")
	public Ejercicio createEjercicio(@Valid @RequestBody Ejercicio ejercicio) throws ResourceNotFoundException {
		TipoEjercicio tipoEjercicio = encontrarTipoEjercicioPorId(ejercicio.getTipoEjercicio().getId());
		ejercicio.setTipoEjercicio(tipoEjercicio);
		tipoEjercicio.getEjercicios().add(ejercicio);
		return ejercicioRepository.save(ejercicio);
	}

	// ACTUALIZAR
	@PutMapping("/ejercicio/{id}")
	public ResponseEntity<Ejercicio> updateEjercicio(@PathVariable(value = "id") Long ejercicioId,
			@Valid @RequestBody Ejercicio ejercicioDetails) throws ResourceNotFoundException {

		Ejercicio ejercicio = encontrarEjercicioPorId(ejercicioId);
		ejercicio.setNombre(ejercicioDetails.getNombre());
		TipoEjercicio tipoEjercicio = ejercicio.getTipoEjercicio();
		tipoEjercicio.getEjercicios().remove(ejercicio);
		tipoEjercicio = encontrarTipoEjercicioPorId(ejercicioDetails.getTipoEjercicio().getId());
		ejercicio.setTipoEjercicio(tipoEjercicio);
		tipoEjercicio.getEjercicios().add(ejercicio);
		final Ejercicio updatedEjercicio = ejercicioRepository.save(ejercicio);
		return ResponseEntity.ok(updatedEjercicio);
	}

	// BORRAR
	@DeleteMapping("/ejercicio/{id}")
	public Map<String, Boolean> deleteEjercicio(@PathVariable(value = "id") Long ejercicioId) throws Exception {
		Ejercicio ejercicio = encontrarEjercicioPorId(ejercicioId);
		ejercicioRepository.delete(ejercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Ejercicio encontrarEjercicioPorId(Long ejercicioId) throws ResourceNotFoundException {
		Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("Ejercicio not found on :: " + ejercicioId));
		return ejercicio;
	}

	public TipoEjercicio encontrarTipoEjercicioPorId(Long tipoEjercicioId) throws ResourceNotFoundException {
		TipoEjercicio tipoEjercicio = tipoEjercicioRepository.findById(tipoEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoEjercicio not found on :: " + tipoEjercicioId));
		return tipoEjercicio;
	}

}