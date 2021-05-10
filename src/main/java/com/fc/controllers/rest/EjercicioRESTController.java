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

import com.fc.domain.Ejercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.EjercicioService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class EjercicioRESTController {

	@Autowired
	private EjercicioService ejercicioService;

	// LISTAR
	@GetMapping("/ejercicio")
	public List<Ejercicio> getAllEjercicioes() {
		return ejercicioService.getAllEjercicioes();
	}

	// RECUPERAR POR ID
	@GetMapping("/ejercicio/{id}")
	public Ejercicio getEjercicioById(@PathVariable(value = "id") Long ejercicioId) throws ResourceNotFoundException {
		return ejercicioService.getEjercicioById(ejercicioId);
	}

	// CREAR
	@PostMapping("/ejercicio")
	public Ejercicio createEjercicio(@Valid @RequestBody Ejercicio ejercicio) throws ResourceNotFoundException {
		return ejercicioService.createEjercicio(ejercicio);
	}

	// ACTUALIZAR
	@PutMapping("/ejercicio/{id}")
	public Ejercicio updateEjercicio(@PathVariable(value = "id") Long ejercicioId,
			@Valid @RequestBody Ejercicio ejercicioDetails) throws ResourceNotFoundException {

		return ejercicioService.updateEjercicio(ejercicioId, ejercicioDetails);
	}

	// BORRAR
	@DeleteMapping("/ejercicio/{id}")
	public Map<String, Boolean> deleteEjercicio(@PathVariable(value = "id") Long ejercicioId) throws Exception {
		return ejercicioService.deleteEjercicio(ejercicioId);
	}
	
	// DEVUELVE UNA LISTA DE EJERCICIOS CORRESPONDIENTES A UN TIPOEJERCICIO
	@GetMapping("/ejercicios/tipoEjercicio/{id}")
		public List<Ejercicio> getEjerciciosByTipoEjercicios(Long tipoEjercicioId) throws ResourceNotFoundException {
		return ejercicioService.getEjerciciosByTipoEJercicio(tipoEjercicioId);
		}

}