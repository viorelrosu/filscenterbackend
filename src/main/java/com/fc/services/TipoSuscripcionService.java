package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.TipoSuscripcion;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TipoSuscripcionRepository;

@Service
public class TipoSuscripcionService {

	@Autowired
	private TipoSuscripcionRepository tipoSuscripcionRepository;

	// DEVUELVE TODOS LOS TIPOSSUSCRIPCION
	public List<TipoSuscripcion> getTipoSuscripcions() {
		return tipoSuscripcionRepository.findAll();
	}

	// DEVUELVE UN TIPOSUSCRIPCION POR ID
	public TipoSuscripcion getTipoSuscripcionById(Long tipoSuscripcionId) throws ResourceNotFoundException {
		TipoSuscripcion tipoSuscripcion = tipoSuscripcionRepository.findById(tipoSuscripcionId).orElseThrow(
				() -> new ResourceNotFoundException("TipoSuscripcion not found on :: " + tipoSuscripcionId));
		return tipoSuscripcion;
	}

	// CREA UN NUEVO TIPOSUSCRIPCION
	public TipoSuscripcion saveTipoSuscripcion(TipoSuscripcion tipoSuscripcion) {
		return tipoSuscripcionRepository.save(tipoSuscripcion);
	}

	// ACTUALIZA UN TIPOSUSCRIPCION
	public TipoSuscripcion updateTipoSuscripcion(Long tipoSuscripcionId, TipoSuscripcion tipoSuscripcionDetails)
			throws ResourceNotFoundException {
		TipoSuscripcion tipoSuscripcion = getTipoSuscripcionById(tipoSuscripcionId);
		tipoSuscripcion.setNombre(tipoSuscripcionDetails.getNombre());
		tipoSuscripcion.setDuracion(tipoSuscripcionDetails.getDuracion());
		tipoSuscripcion.setTarifa(tipoSuscripcionDetails.getTarifa());
		final TipoSuscripcion updatedTipoSuscripcion = saveTipoSuscripcion(tipoSuscripcion);
		return updatedTipoSuscripcion;
	}

	// BORRAR UN TIPOSUSCRIPCION
	public Map<String, Boolean> deleteTipoSuscripcion(Long tipoSuscripcionId) throws Exception {
		TipoSuscripcion tipoSuscripcion = getTipoSuscripcionById(tipoSuscripcionId);
		tipoSuscripcionRepository.delete(tipoSuscripcion);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
