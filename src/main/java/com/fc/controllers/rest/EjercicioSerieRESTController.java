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

import com.fc.domain.EjercicioSerie;
import com.fc.domain.TablaEjercicio;
import com.fc.domain.Ejercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.EjercicioSerieRepository;
import com.fc.repositories.EjercicioRepository;
import com.fc.repositories.TablaEjercicioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class EjercicioSerieRESTController {

	@Autowired
	private EjercicioSerieRepository ejercicioSerieRepository;
	@Autowired
	private EjercicioRepository ejercicioRepository;
	@Autowired
	private TablaEjercicioRepository tablaEjercicioRepository;

	// LISTAR
	@GetMapping("/ejercicioSerie")
	public List<EjercicioSerie> getAllEjercicioSeriees() {
		return ejercicioSerieRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/ejercicioSerie/{id}")
	public ResponseEntity<EjercicioSerie> getEjercicioSerieById(@PathVariable(value = "id") Long ejercicioSerieId)
			throws ResourceNotFoundException {
		EjercicioSerie ejercicioSerie = encontrarEjercicioSeriePorId(ejercicioSerieId);
		return ResponseEntity.ok().body(ejercicioSerie);
	}

	// CREAR
	@PostMapping("/ejercicioSerie")
	public EjercicioSerie createEjercicioSerie(@Valid @RequestBody EjercicioSerie ejercicioSerie)
			throws ResourceNotFoundException {
		Ejercicio ejercicio = encontrarEjercicioPorId(ejercicioSerie.getEjercicio().getId());
		TablaEjercicio tablaEjercicio = encontrarTablaEjercicioPorId(ejercicioSerie.getTablaEjercicio().getId());
		ejercicioSerie.setEjercicio(ejercicio);
		ejercicioSerie.setTablaEjercicio(tablaEjercicio);
		ejercicio.getEjercicioSeries().add(ejercicioSerie);
		tablaEjercicio.getEjercicioSeries().add(ejercicioSerie);
		return ejercicioSerieRepository.save(ejercicioSerie);
	}

	// ACTUALIZAR
	@PutMapping("/ejercicioSerie/{id}")
	public ResponseEntity<EjercicioSerie> updateEjercicioSerie(@PathVariable(value = "id") Long ejercicioSerieId,
			@Valid @RequestBody EjercicioSerie ejercicioSerieDetails) throws ResourceNotFoundException {

		EjercicioSerie ejercicioSerie = encontrarEjercicioSeriePorId(ejercicioSerieId);
		ejercicioSerie.setSeries(ejercicioSerieDetails.getSeries());
		ejercicioSerie.setRepeticiones(ejercicioSerieDetails.getRepeticiones());
		ejercicioSerie.setPorSemana(ejercicioSerieDetails.getPorSemana());
		Ejercicio ejercicio = ejercicioSerie.getEjercicio();
		ejercicio.getEjercicioSeries().remove(ejercicioSerie);
		ejercicio = encontrarEjercicioPorId(ejercicioSerieDetails.getEjercicio().getId());
		ejercicioSerie.setEjercicio(ejercicio);
		ejercicio.getEjercicioSeries().add(ejercicioSerie);
		TablaEjercicio tablaEjercicio = ejercicioSerie.getTablaEjercicio();
		tablaEjercicio.getEjercicioSeries().remove(ejercicioSerie);
		tablaEjercicio = encontrarTablaEjercicioPorId(ejercicioSerieDetails.getTablaEjercicio().getId());
		ejercicioSerie.setTablaEjercicio(tablaEjercicio);
		tablaEjercicio.getEjercicioSeries().add(ejercicioSerie);
		final EjercicioSerie updatedEjercicioSerie = ejercicioSerieRepository.save(ejercicioSerie);
		return ResponseEntity.ok(updatedEjercicioSerie);
	}

	// BORRAR
	@DeleteMapping("/ejercicioSerie/{id}")
	public Map<String, Boolean> deleteEjercicioSerie(@PathVariable(value = "id") Long ejercicioSerieId)
			throws Exception {
		EjercicioSerie ejercicioSerie = encontrarEjercicioSeriePorId(ejercicioSerieId);
		ejercicioSerieRepository.delete(ejercicioSerie);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public EjercicioSerie encontrarEjercicioSeriePorId(Long ejercicioSerieId) throws ResourceNotFoundException {
		EjercicioSerie ejercicioSerie = ejercicioSerieRepository.findById(ejercicioSerieId)
				.orElseThrow(() -> new ResourceNotFoundException("EjercicioSerie not found on :: " + ejercicioSerieId));
		return ejercicioSerie;
	}

	public Ejercicio encontrarEjercicioPorId(Long ejercicioId) throws ResourceNotFoundException {
		Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("Ejercicio not found on :: " + ejercicioId));
		return ejercicio;
	}

	public TablaEjercicio encontrarTablaEjercicioPorId(Long tablaEjercicioId) throws ResourceNotFoundException {
		TablaEjercicio tablaEjercicio = tablaEjercicioRepository.findById(tablaEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TablaEjercicio not found on :: " + tablaEjercicioId));
		return tablaEjercicio;
	}
}
