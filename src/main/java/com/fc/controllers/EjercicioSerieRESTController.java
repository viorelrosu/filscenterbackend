package com.fc.controllers;

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

import com.fc.domain.EjercicioSerie;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.EjercicioSerieRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class EjercicioSerieRESTController {

	@Autowired
	private EjercicioSerieRepository ejercicioSerieRepository;

	// LISTAR
	@GetMapping("/ejercicioSerie")
	public List<EjercicioSerie> getAllEjercicioSeries() {
		System.out.println(ejercicioSerieRepository.findAll());
		return ejercicioSerieRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/ejercicioSerie/{id}")
	public ResponseEntity<EjercicioSerie> getEjercicioSerieById(@PathVariable(value = "id") Long ejercicioSerieId)
			throws ResourceNotFoundException {
		EjercicioSerie ejercicioSerie = ejercicioSerieRepository.findById(ejercicioSerieId)
				.orElseThrow(() -> new ResourceNotFoundException("EjercicioSerie not found on :: " + ejercicioSerieId));
		return ResponseEntity.ok().body(ejercicioSerie);
	}

	// BORRAR
	@DeleteMapping("/ejercicioSerie/{id}")
	public Map<String, Boolean> deleteEjercicioSerie(@PathVariable(value = "id") Long ejercicioSerieId) throws Exception {
		EjercicioSerie ejercicioSerie = ejercicioSerieRepository.findById(ejercicioSerieId)
				.orElseThrow(() -> new ResourceNotFoundException("EjercicioSerie not found on :: " + ejercicioSerieId));

		ejercicioSerieRepository.delete(ejercicioSerie);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
