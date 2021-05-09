package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Actividad;
import com.fc.domain.TipoActividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ActividadRepository;

@Service
public class ActividadService {
	@Autowired
	private ActividadRepository actividadRepository;
	@Autowired
	private TipoActividadService tipoActividadService;

	// DEVUELVE TODAS LAS ACTIVIDADES
	public List<Actividad> getAllActividades() {
		return actividadRepository.findAll();
	}

	// DEVUELVE UNA ACTIVIDAD POR ID
	public Actividad getActividadById(Long actividadId) throws ResourceNotFoundException {
		Actividad actividad = actividadRepository.findById(actividadId)
				.orElseThrow(() -> new ResourceNotFoundException("Actividad not found on :: " + actividadId));
		return actividad;
	}

	// CREA UNA NUEVA ACTIVIDAD
	public Actividad createActividad(Actividad actividad) throws ResourceNotFoundException {
		TipoActividad tipoActividad = tipoActividadService.getTipoActividadById(actividad.getTipoActividad().getId());
		actividad.setTipoActividad(tipoActividad);
		tipoActividad.getActividades().add(actividad);
		return actividadRepository.save(actividad);
	}

	// ACTUALIZA UNA ACTIVIDAD
	public Actividad updateActividad(Long actividadId, Actividad actividadDetails) throws ResourceNotFoundException {
		Actividad actividad = getActividadById(actividadId);
		actividad.setNombre(actividadDetails.getNombre());
		actividad.setDescripcion(actividadDetails.getDescripcion());
		actividad.setDificultad(actividadDetails.getDificultad());
		actividad.setColor(actividadDetails.getColor());
		TipoActividad tipoActividad = actividad.getTipoActividad();
		tipoActividad.getActividades().remove(actividad);
		tipoActividad = tipoActividadService.getTipoActividadById(actividadDetails.getTipoActividad().getId());
		actividad.setTipoActividad(tipoActividad);
		tipoActividad.getActividades().add(actividad);
		final Actividad updatedActividad = actividadRepository.save(actividad);
		return updatedActividad;
	}

	// BORRAR UNA ACTIVIDAD
	public Map<String, Boolean> deleteActividad(Long actividadId) throws Exception {
		Actividad actividad = getActividadById(actividadId);
		List<Actividad> actividades = (List<Actividad>) actividad.getTipoActividad().getActividades();
		actividades.remove(actividad);
		actividad.getTipoActividad().setActividades(actividades);
		actividadRepository.delete(actividad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
