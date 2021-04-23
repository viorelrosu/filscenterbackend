package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Taquilla;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TaquillaRepository;

@Service
public class TaquillaService {

	@Autowired
	private TaquillaRepository taquillaRepository;

	// DEVUELVE TODAS LAS TAQUILLAS
	public List<Taquilla> getTaquillas() {
		return taquillaRepository.findAll();
	}

	// DEVUELVE UNA TAQUILLA POR ID
	public Taquilla getTaquillaById(Long taquillaId) throws ResourceNotFoundException {
		Taquilla taquilla = taquillaRepository.findById(taquillaId)
				.orElseThrow(() -> new ResourceNotFoundException("Taquilla not found on :: " + taquillaId));
		return taquilla;
	}

	// CREA UNA NUEVA TAQUILLA
	public Taquilla saveTaquilla(Taquilla taquilla) {
		return taquillaRepository.save(taquilla);
	}

	// ACTUALIZA UNA TAQUILLA
	public Taquilla updateTaquilla(Long taquillaId, Taquilla taquillaDetails) throws ResourceNotFoundException {
		Taquilla taquilla = getTaquillaById(taquillaId);
		taquilla.setNumero(taquillaDetails.getNumero());
		final Taquilla updatedTaquilla = saveTaquilla(taquilla);
		return updatedTaquilla;
	}

	// BORRAR UNA TAQUILLA
	public Map<String, Boolean> deleteTaquilla(Long taquillaId) throws Exception {
		Taquilla taquilla = getTaquillaById(taquillaId);
		taquillaRepository.delete(taquilla);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}