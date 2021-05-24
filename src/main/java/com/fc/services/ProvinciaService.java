package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Provincia;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	private ProvinciaRepository provinciaRepository;

	// DEVUELVE TODAS LAS PROVINCIAS
	public List<Provincia> getProvincias() {
		return provinciaRepository.findAll();
	}

	// DEVUELVE UNA PROVINCIA POR ID
	public Provincia getProvinciaById(Long provinciaId) throws ResourceNotFoundException {
		Provincia provincia = provinciaRepository.findById(provinciaId)
				.orElseThrow(() -> new ResourceNotFoundException("Provincia not found on :: " + provinciaId));
		return provincia;
	}

	// CREA UNA NUEVA PROVINCIA
	public Provincia saveProvincia(Provincia provincia) {
		if (validarProvincia(provincia)) {
			return provinciaRepository.save(provincia);
		} else {
			return null;
		}
	}

	// ACTUALIZA UNA PROVINCIA
	public Provincia updateProvincia(Long provinciaId, Provincia provinciaDetails) throws ResourceNotFoundException {
		if (validarProvincia(provinciaDetails)) {
			Provincia provincia = getProvinciaById(provinciaId);
			provincia.setNombre(provinciaDetails.getNombre());
			final Provincia updatedProvincia = saveProvincia(provincia);
			return updatedProvincia;
		} else {
			return null;
		}
	}

	// BORRAR UNA PROVINCIA
	public Map<String, Boolean> deleteProvincia(Long provinciaId) throws ResourceNotFoundException {
		Provincia provincia = getProvinciaById(provinciaId);
		provinciaRepository.delete(provincia);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// VALIDAR
	public boolean validarProvincia(Provincia provincia) {
		if (provincia.getNombre() != null && !provincia.getNombre().contentEquals("")) {
			return true;
		}
		return false;
	}
}
