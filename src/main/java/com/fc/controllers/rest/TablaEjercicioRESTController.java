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

import com.fc.domain.TablaEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.TablaEjercicioService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TablaEjercicioRESTController {

	@Autowired
	private TablaEjercicioService tablaEjercicioService;

	// LISTAR
	@GetMapping("/tablaEjercicio")
	public List<TablaEjercicio> getAllTablaEjercicioes() {
		return tablaEjercicioService.getAllTablaEjercicioes();
	}

	// RECUPERAR POR ID
	@GetMapping("/tablaEjercicio/{id}")
	public TablaEjercicio getTablaEjercicioById(@PathVariable(value = "id") Long tablaEjercicioId)
			throws ResourceNotFoundException {
		return tablaEjercicioService.getTablaEjercicioById(tablaEjercicioId);
	}

	// CREAR
	@PostMapping("/tablaEjercicio")
	public TablaEjercicio createTablaEjercicio(@Valid @RequestBody TablaEjercicio tablaEjercicio)
			throws ResourceNotFoundException {
		return tablaEjercicioService.createTablaEjercicio(tablaEjercicio);
	}

	// ACTUALIZAR
	@PutMapping("/tablaEjercicio/{id}")
	public TablaEjercicio updateTablaEjercicio(@PathVariable(value = "id") Long tablaEjercicioId,
			@Valid @RequestBody TablaEjercicio tablaEjercicioDetails) throws ResourceNotFoundException {

		return tablaEjercicioService.updateTablaEjercicio(tablaEjercicioId, tablaEjercicioDetails);
	}

	// BORRAR
	@DeleteMapping("/tablaEjercicio/{id}")
	public Map<String, Boolean> deleteTablaEjercicio(@PathVariable(value = "id") Long tablaEjercicioId)
			throws Exception {
		return tablaEjercicioService.deleteTablaEjercicio(tablaEjercicioId);
	}

}
