package com.fc.controllers.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.TablaEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TablaEjercicioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TablaEjercicioRESTController {

	@Autowired
	private TablaEjercicioRepository tablaEjercicioRepository;

	// LISTAR
	@GetMapping("/tablaEjercicio")
	public List<TablaEjercicio> getAllTablasEjercicio() {
		return tablaEjercicioRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/tablaEjercicio/{id}")
	public ResponseEntity<TablaEjercicio> getTablaEjercicioById(@PathVariable(value = "id") Long tablaEjercicioId)
			throws ResourceNotFoundException {
		TablaEjercicio tablaEjercicio = tablaEjercicioRepository.findById(tablaEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TablaEjercicio not found on :: " + tablaEjercicioId));
		return ResponseEntity.ok().body(tablaEjercicio);
	}

	// BORRAR
	@DeleteMapping("/tablaEjercicio/{id}")
	public Map<String, Boolean> deleteTablaEjercicio(@PathVariable(value = "id") Long tablaEjercicioId) throws Exception {
		TablaEjercicio tablaEjercicio = tablaEjercicioRepository.findById(tablaEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TablaEjercicio not found on :: " + tablaEjercicioId));

		tablaEjercicioRepository.delete(tablaEjercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
