package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Sala;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SalaRepository;

@Service
public class SalaService {
	@Autowired
	private SalaRepository salaRepository;

	// DEVUELVE TODAS LAS SALAS
	public List<Sala> getSalas() {
		return salaRepository.findAll();
	}

	// DEVUELVE UNA SALA POR ID
	public Sala getSalaById(Long salaId) throws ResourceNotFoundException {
		Sala sala = salaRepository.findById(salaId)
				.orElseThrow(() -> new ResourceNotFoundException("Sala not found on :: " + salaId));
		return sala;
	}

	// CREA UNA NUEVA SALA
	public Sala saveSala(Sala sala) {
		if (validarSala(sala)) {
			return salaRepository.save(sala);
		} else {
			return null;
		}
	}

	// ACTUALIZA UNA SALA
	public Sala updateSala(Long salaId, Sala salaDetails) throws ResourceNotFoundException {
		if (validarSala(salaDetails)) {
			Sala sala = getSalaById(salaId);
			sala.setNumero(salaDetails.getNumero());
			sala.setAforoMax(salaDetails.getAforoMax());
			final Sala updatedSala = salaRepository.save(sala);
			return updatedSala;
		} else {
			return null;
		}
	}

	// BORRAR UNA SALA
	public Map<String, Boolean> deleteSala(Long salaId) throws Exception {
		Sala sala = getSalaById(salaId);
		salaRepository.delete(sala);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// VALIDAR
	public boolean validarSala(Sala sala) {
		if (sala.getNumero() != null) {
			if (sala.getAforoMax() != null) {
				return true;
			}
		}
		return false;
	}
}
