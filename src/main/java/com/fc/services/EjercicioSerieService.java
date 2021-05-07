package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.fc.domain.Ejercicio;
import com.fc.domain.EjercicioSerie;
import com.fc.domain.TablaEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.EjercicioSerieRepository;

@Service
public class EjercicioSerieService {
	@Autowired
	private EjercicioSerieRepository ejercicioSerieRepository;
	@Autowired
	private EjercicioService ejercicioService;
	@Autowired
	private TablaEjercicioService tablaEjercicioService;

	// DEVUELVE TODOS LOS EJERCICIOS
	@GetMapping("/ejercicioSerie")
	public List<EjercicioSerie> getAllEjercicioSeries() {
		return ejercicioSerieRepository.findAll();
	}

	// DEVUELVE UN EJERCICIO POR ID
	@GetMapping("/ejercicioSerie/{id}")
	public EjercicioSerie getEjercicioSerieById(Long ejercicioSerieId) throws ResourceNotFoundException {
		EjercicioSerie ejercicioSerie = ejercicioSerieRepository.findById(ejercicioSerieId)
				.orElseThrow(() -> new ResourceNotFoundException("EjercicioSerie not found on :: " + ejercicioSerieId));
		return ejercicioSerie;
	}

	// CREA UN NUEVO EJERCICIO
	@PostMapping("/ejercicioSerie")
	public EjercicioSerie createEjercicioSerie(EjercicioSerie ejercicioSerie) throws ResourceNotFoundException {
		Ejercicio ejercicio = ejercicioService.getEjercicioById(ejercicioSerie.getEjercicio().getId());
		TablaEjercicio tablaEjercicio = tablaEjercicioService
				.getTablaEjercicioById(ejercicioSerie.getTablaEjercicio().getId());
		ejercicioSerie.setEjercicio(ejercicio);
		ejercicioSerie.setTablaEjercicio(tablaEjercicio);
		ejercicio.getEjercicioSeries().add(ejercicioSerie);
		tablaEjercicio.getEjercicioSeries().add(ejercicioSerie);
		return ejercicioSerieRepository.save(ejercicioSerie);
	}

	// ACTUALIZA UN EJERCICIO
	@PutMapping("/ejercicioSerie/{id}")
	public EjercicioSerie updateEjercicioSerie(Long ejercicioSerieId, EjercicioSerie ejercicioSerieDetails)
			throws ResourceNotFoundException {

		EjercicioSerie ejercicioSerie = getEjercicioSerieById(ejercicioSerieId);
		ejercicioSerie.setSeries(ejercicioSerieDetails.getSeries());
		ejercicioSerie.setRepeticiones(ejercicioSerieDetails.getRepeticiones());
		ejercicioSerie.setPorSemana(ejercicioSerieDetails.getPorSemana());
		Ejercicio ejercicio = ejercicioSerie.getEjercicio();
		ejercicio.getEjercicioSeries().remove(ejercicioSerie);
		ejercicio = ejercicioService.getEjercicioById(ejercicioSerieDetails.getEjercicio().getId());
		ejercicioSerie.setEjercicio(ejercicio);
		ejercicio.getEjercicioSeries().add(ejercicioSerie);
		TablaEjercicio tablaEjercicio = ejercicioSerie.getTablaEjercicio();
		tablaEjercicio.getEjercicioSeries().remove(ejercicioSerie);
		tablaEjercicio = tablaEjercicioService.getTablaEjercicioById(ejercicioSerieDetails.getTablaEjercicio().getId());
		ejercicioSerie.setTablaEjercicio(tablaEjercicio);
		tablaEjercicio.getEjercicioSeries().add(ejercicioSerie);
		final EjercicioSerie updatedEjercicioSerie = ejercicioSerieRepository.save(ejercicioSerie);
		return updatedEjercicioSerie;
	}

	// BORRAR UN EJERCICIO
	@DeleteMapping("/ejercicioSerie/{id}")
	public Map<String, Boolean> deleteEjercicioSerie(Long ejercicioSerieId) throws Exception {
		EjercicioSerie ejercicioSerie = getEjercicioSerieById(ejercicioSerieId);
		List<EjercicioSerie> ejercicioSeries =(List<EjercicioSerie>) ejercicioSerie.getEjercicio().getEjercicioSeries();
		ejercicioSeries.remove(ejercicioSerie);
		ejercicioSerie.getEjercicio().setEjercicioSeries(ejercicioSeries);
		ejercicioSeries = (List<EjercicioSerie>) ejercicioSerie.getTablaEjercicio().getEjercicioSeries();
		ejercicioSeries.remove(ejercicioSerie);
		ejercicioSerie.getTablaEjercicio().setEjercicioSeries(ejercicioSeries);
		ejercicioSerieRepository.delete(ejercicioSerie);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// DEVUELVE UNA LISTA DE LOCALIDADES CORRESPONDIENTES A UNA PROVINCIA
	public List<EjercicioSerie> getEjerciciosSerieByTablaEjercicio(Long tablaEjercicioId) throws ResourceNotFoundException {
		return (List<EjercicioSerie>) tablaEjercicioService.getTablaEjercicioById(tablaEjercicioId).getEjercicioSeries();
	}
}
