package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.TipoEjercicio;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoEjercicioRepository;

@Service
public class TipoEjercicioService {

	@Autowired
	private TipoEjercicioRepository tipoEjercicioRepository;

	// DEVUELVE TODOS LOS TIPOEJERCICIO
	public List<TipoEjercicio> getTipoEjercicios() {
		return tipoEjercicioRepository.findAll();
	}

	// DEVUELVE UN TIPOEJERCICIO POR ID
	public TipoEjercicio getTipoEjercicioById(Long tipoEjercicioId) throws ResourceNotFoundException {
		TipoEjercicio tipoEjercicio = tipoEjercicioRepository.findById(tipoEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoEjercicio not found on :: " + tipoEjercicioId));
		return tipoEjercicio;
	}

	// CREA UN NUEVO TIPOEJERCICIO
	public TipoEjercicio saveTipoEjercicio(TipoEjercicio tipoEjercicio) {
		if (validarTipoEjercicio(tipoEjercicio)) {
			return tipoEjercicioRepository.save(tipoEjercicio);
		} else {
			return null;
		}
	}

	// ACTUALIZA UN TIPOEJERCICIO
	public TipoEjercicio updateTipoEjercicio(Long tipoEjercicioId, TipoEjercicio tipoEjercicioDetails)
			throws ResourceNotFoundException {
		if (validarTipoEjercicio(tipoEjercicioDetails)) {
			TipoEjercicio tipoEjercicio = getTipoEjercicioById(tipoEjercicioId);
			tipoEjercicio.setNombre(tipoEjercicioDetails.getNombre());
			final TipoEjercicio updatedTipoEjercicio = tipoEjercicioRepository.save(tipoEjercicio);
			return updatedTipoEjercicio;
		} else {
			return null;
		}
	}

	// BORRAR UN TIPOEJERCICIO
	public Map<String, Boolean> deleteTipoEjercicio(Long tipoEjercicioId) throws Exception {
		TipoEjercicio tipoEjercicio = getTipoEjercicioById(tipoEjercicioId);
		tipoEjercicioRepository.delete(tipoEjercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// VALIDAR
	public boolean validarTipoEjercicio(TipoEjercicio tipoEjercicio) {
		if (tipoEjercicio.getNombre() != null && !tipoEjercicio.getNombre().contentEquals("")) {
			return true;
		}
		return false;
	}
}
