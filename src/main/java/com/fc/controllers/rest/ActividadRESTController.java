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

import com.fc.domain.Actividad;
import com.fc.domain.TipoActividad;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ActividadRepository;
import com.fc.repositories.TipoActividadRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ActividadRESTController {

	@Autowired
	private ActividadRepository actividadRepository;
	@Autowired
	private TipoActividadRepository tipoActividadRepository;

	// LISTAR
	@GetMapping("/actividad")
	public List<Actividad> getAllActividades() {
		return actividadRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/actividad/{id}")
	public ResponseEntity<Actividad> getActividadById(@PathVariable(value = "id") Long actividadId)
			throws ResourceNotFoundException {
		Actividad actividad = encontrarActividadPorId(actividadId);
		return ResponseEntity.ok().body(actividad);
	}

	// CREAR
	@PostMapping("/actividad")
	public Actividad createActividad(@Valid @RequestBody Actividad actividad) throws ResourceNotFoundException {
		TipoActividad tipoActividad = encontrarTipoActividadPorId(actividad.getTipoActividad().getId());
		actividad.setTipoActividad(tipoActividad);
		tipoActividad.getActividades().add(actividad);
		return actividadRepository.save(actividad);
	}

	// ACTUALIZAR
	@PutMapping("/actividad/{id}")
	public ResponseEntity<Actividad> updateActividad(@PathVariable(value = "id") Long actividadId,
			@Valid @RequestBody Actividad actividadDetails) throws ResourceNotFoundException {

		Actividad actividad = encontrarActividadPorId(actividadId);
		actividad.setNombre(actividadDetails.getNombre());
		actividad.setDescripcion(actividadDetails.getDescripcion());
		actividad.setDificultad(actividadDetails.getDificultad());
		TipoActividad tipoActividad = actividad.getTipoActividad();
		tipoActividad.getActividades().remove(actividad);
		tipoActividad = encontrarTipoActividadPorId(actividadDetails.getTipoActividad().getId());
		actividad.setTipoActividad(tipoActividad);
		tipoActividad.getActividades().add(actividad);
		final Actividad updatedActividad = actividadRepository.save(actividad);
		return ResponseEntity.ok(updatedActividad);
	}

	// BORRAR
	@DeleteMapping("/actividad/{id}")
	public Map<String, Boolean> deleteActividad(@PathVariable(value = "id") Long actividadId) throws Exception {
		Actividad actividad = encontrarActividadPorId(actividadId);
		actividadRepository.delete(actividad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Actividad encontrarActividadPorId(Long actividadId) throws ResourceNotFoundException {
		Actividad actividad = actividadRepository.findById(actividadId)
				.orElseThrow(() -> new ResourceNotFoundException("Actividad not found on :: " + actividadId));
		return actividad;
	}

	public TipoActividad encontrarTipoActividadPorId(Long tipoActividadId) throws ResourceNotFoundException {
		TipoActividad tipoActividad = tipoActividadRepository.findById(tipoActividadId)
				.orElseThrow(() -> new ResourceNotFoundException("TipoActividad not found on :: " + tipoActividadId));
		return tipoActividad;
	}

}
