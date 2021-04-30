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

import com.fc.domain.TipoEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.TipoEjercicioService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TipoEjercicioRESTController {

	@Autowired
	private TipoEjercicioService tipoEjercicioService;

	// LISTAR
	@GetMapping("/tipoEjercicio")
	public List<TipoEjercicio> getAllTipoEjercicios() {
		return tipoEjercicioService.getTipoEjercicios();
	}

	// RECUPERAR POR ID
	@GetMapping("/tipoEjercicio/{id}")
	public TipoEjercicio getTipoEjercicioById(@PathVariable(value = "id") Long tipoEjercicioId)
			throws ResourceNotFoundException {
		return tipoEjercicioService.getTipoEjercicioById(tipoEjercicioId);
	}

	// CREAR
	@PostMapping("/tipoEjercicio")
	public TipoEjercicio createTipoEjercicio(@Valid @RequestBody TipoEjercicio tipoEjercicio) {
		return tipoEjercicioService.saveTipoEjercicio(tipoEjercicio);
	}

	// ACTUALIZAR
	@PutMapping("/tipoEjercicio/{id}")
	public TipoEjercicio updateTipoEjercicio(@PathVariable(value = "id") Long tipoEjercicioId,
			@Valid @RequestBody TipoEjercicio tipoEjercicioDetails) throws ResourceNotFoundException {
		return tipoEjercicioService.updateTipoEjercicio(tipoEjercicioId, tipoEjercicioDetails);
	}

	// BORRAR
	@DeleteMapping("/tipoEjercicio/{id}")
	public Map<String, Boolean> deleteTipoEjercicio(@PathVariable(value = "id") Long tipoEjercicioId) throws Exception {
		return tipoEjercicioService.deleteTipoEjercicio(tipoEjercicioId);
	}

}
