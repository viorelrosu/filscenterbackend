package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.TipoActividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoActividadRepository;

@Service
public class TipoActividadService {

	@Autowired
	private TipoActividadRepository tipoActividadRepository;

	// DEVUELVE TODOS LOS TIPOACTIVIDAD
	public List<TipoActividad> getTipoActividads() {
		return tipoActividadRepository.findAll();
	}

	// DEVUELVE UN TIPOACTIVIDAD POR ID
	public TipoActividad getTipoActividadById(Long tipoActividadId) throws ResourceNotFoundException {
		TipoActividad tipoActividad = tipoActividadRepository.findById(tipoActividadId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoActividad not found on :: " + tipoActividadId));
		return tipoActividad;
	}

	// CREA UN NUEVO TIPOACTIVIDAD
	public TipoActividad saveTipoActividad(TipoActividad tipoActividad) {
		if (validarTipoActividad(tipoActividad)) {
			return tipoActividadRepository.save(tipoActividad);
		} else {
			return null;
		}
	}

	// ACTUALIZA UN TIPOACTIVIDAD
	public TipoActividad updateTipoActividad(Long tipoActividadId, TipoActividad tipoActividadDetails)
			throws ResourceNotFoundException {
		if (validarTipoActividad(tipoActividadDetails)) {
			TipoActividad tipoActividad = getTipoActividadById(tipoActividadId);
			tipoActividad.setNombre(tipoActividadDetails.getNombre());
			final TipoActividad updatedTipoActividad = tipoActividadRepository.save(tipoActividad);
			return updatedTipoActividad;
		} else {
			return null;
		}
	}

	// BORRAR UN TIPOACTIVIDAD
	public Map<String, Boolean> deleteTipoActividad(Long tipoActividadId) throws Exception {
		TipoActividad tipoActividad = getTipoActividadById(tipoActividadId);
		tipoActividadRepository.delete(tipoActividad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// VALIDAR
	public boolean validarTipoActividad(TipoActividad tipoActividad) {
		if (tipoActividad.getNombre() != null && !tipoActividad.getNombre().contentEquals("")) {
			return true;
		}
		return false;
	}

}