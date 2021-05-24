package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Ejercicio;
import com.fc.domain.TipoEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.EjercicioRepository;

@Service
public class EjercicioService {

	@Autowired
	private EjercicioRepository ejercicioRepository;
	@Autowired
	private TipoEjercicioService tipoEjercicioService;

	// DEVUELVE TODOS LOS EJERCICIOS
	public List<Ejercicio> getAllEjercicioes() {
		return ejercicioRepository.findAll();
	}

	// DEVUELVE UN EJERCICIO POR ID
	public Ejercicio getEjercicioById(Long ejercicioId) throws ResourceNotFoundException {
		Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("Ejercicio not found on :: " + ejercicioId));
		return ejercicio;
	}

	// CREA UN NUEVO EJERCICIO
	public Ejercicio createEjercicio(Ejercicio ejercicio) throws ResourceNotFoundException {
		if (validarEjercicio(ejercicio)) {
			TipoEjercicio tipoEjercicio = tipoEjercicioService
					.getTipoEjercicioById(ejercicio.getTipoEjercicio().getId());
			ejercicio.setTipoEjercicio(tipoEjercicio);
			tipoEjercicio.getEjercicios().add(ejercicio);
			return ejercicioRepository.save(ejercicio);
		} else {
			return null;
		}
	}

	// ACTUALIZA UN EJERCICIO
	public Ejercicio updateEjercicio(Long ejercicioId, Ejercicio ejercicioDetails) throws ResourceNotFoundException {
		if (validarEjercicio(ejercicioDetails)) {
			Ejercicio ejercicio = getEjercicioById(ejercicioId);
			ejercicio.setNombre(ejercicioDetails.getNombre());
			TipoEjercicio tipoEjercicio = ejercicio.getTipoEjercicio();
			tipoEjercicio.getEjercicios().remove(ejercicio);
			tipoEjercicio = tipoEjercicioService.getTipoEjercicioById(ejercicioDetails.getTipoEjercicio().getId());
			ejercicio.setTipoEjercicio(tipoEjercicio);
			tipoEjercicio.getEjercicios().add(ejercicio);
			final Ejercicio updatedEjercicio = ejercicioRepository.save(ejercicio);
			return updatedEjercicio;
		} else {
			return null;
		}
	}

	// BORRAR UN EJERCICIO
	public Map<String, Boolean> deleteEjercicio(Long ejercicioId) throws Exception {
		Ejercicio ejercicio = getEjercicioById(ejercicioId);
		List<Ejercicio> ejercicios = (List<Ejercicio>) ejercicio.getTipoEjercicio().getEjercicios();
		ejercicios.remove(ejercicio);
		ejercicio.getTipoEjercicio().setEjercicios(ejercicios);
		ejercicio.setTipoEjercicio(null);
		ejercicioRepository.delete(ejercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// LISTAR EJERCICIOS DE UN MISMO TIPO
	public List<Ejercicio> getEjerciciosByTipoEjercicio(Long tipoEjercicioId) throws ResourceNotFoundException {
		// return (List<Ejercicio>)
		// tipoEjercicioService.getTipoEjercicioById(tipoEjercicioId).getEjercicios();
		return ejercicioRepository.findAllByTipoEjercicioId(tipoEjercicioId);
	}

	// VALIDAR
	public boolean validarEjercicio(Ejercicio ejercicio) {
		if (ejercicio.getNombre() != null && !ejercicio.getNombre().contentEquals("")) {
			if (ejercicio.getTipoEjercicio() != null) {
				return true;
			}
		}
		return false;
	}
}
