package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Suscripcion;
import com.fc.domain.TipoSuscripcion;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SuscripcionRepository;

@Service
public class SuscripcionService {

	@Autowired
	private SuscripcionRepository suscripcionRepository;

	@Autowired
	private TipoSuscripcionService tipoSuscripcionService;
	
	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private UsuarioService usuarioService;

	// DEVUELVE TODAS LAS SUSCRIPCIONES
	public List<Suscripcion> getSuscripciones() {
		return suscripcionRepository.findAll();
	}

	// DEVUELVE UNA SUSCRIPCION POR ID
	public Suscripcion getSuscripcionById(Long suscripcionId) throws ResourceNotFoundException {
		return suscripcionRepository.findById(suscripcionId)
				.orElseThrow(() -> new ResourceNotFoundException("Suscripcion not found on :: " + suscripcionId));
	}

	// CREA UNA NUEVA SUSCRIPCION
	public Suscripcion saveSuscripcion(Suscripcion suscripcion) throws ResourceNotFoundException {
		TipoSuscripcion tipoSuscripcion = tipoSuscripcionService.getTipoSuscripcionById(suscripcion.getTipoSuscripcion().getId());
		suscripcion.setTipoSuscripcion(tipoSuscripcion);
		tipoSuscripcion.getSuscripciones().add(suscripcion);
		facturaService.cobrarSuscripcion(suscripcion);
		return suscripcionRepository.save(suscripcion);
	}

	// ACTUALIZA UNA SUSCRIPCION
	public Suscripcion updateSuscripcion(Long suscripcionId, Suscripcion suscripcionDetails) throws ResourceNotFoundException {
		Suscripcion suscripcion = getSuscripcionById(suscripcionId);
		suscripcion.setFechaAlta(suscripcionDetails.getFechaAlta());
		suscripcion.setFechaBaja(suscripcionDetails.getFechaBaja());
		suscripcion.setRecurrente(suscripcionDetails.isRecurrente());
		TipoSuscripcion tipoSuscripcion = suscripcion.getTipoSuscripcion();
		tipoSuscripcion.getSuscripciones().remove(suscripcion);
		tipoSuscripcion = tipoSuscripcionService.getTipoSuscripcionById(suscripcionDetails.getTipoSuscripcion().getId());
		suscripcion.setTipoSuscripcion(tipoSuscripcion);
		tipoSuscripcion.getSuscripciones().add(suscripcion);
		Usuario usuario = suscripcion.getUsuario();
		usuario.getSuscripciones().remove(suscripcion);
		usuario = usuarioService.getUsuarioById(suscripcionDetails.getUsuario().getId());
		suscripcion.setUsuario(usuario);
		usuario.getSuscripciones().add(suscripcion);
		final Suscripcion updatedSuscripcion = suscripcionRepository.save(suscripcion);
		return updatedSuscripcion;
	}

	// BORRAR UNA SUSCRIPCION
	public Map<String, Boolean> deleteSuscripcion(Long suscripcionId) throws Exception {
		Suscripcion suscripcion = getSuscripcionById(suscripcionId);
		List<Suscripcion> suscripciones = (List<Suscripcion>) suscripcion.getUsuario().getSuscripciones();
		suscripciones.remove(suscripcion);
		suscripcion.getUsuario().setSuscripciones(suscripciones);
		suscripcion.setUsuario(null);
		suscripciones = (List<Suscripcion>) suscripcion.getTipoSuscripcion().getSuscripciones();
		suscripciones.remove(suscripcion);
		suscripcion.getTipoSuscripcion().setSuscripciones(suscripciones);
		suscripcion.setTipoSuscripcion(null);
		suscripcionRepository.delete(suscripcion);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// DEVUELVE UNA LISTA DE SUSCRIPCIONES DE UN USUARIO
	public List<Suscripcion> getSuscripcionesByUsuario(Long usuarioId) throws ResourceNotFoundException {
		return (List<Suscripcion>) usuarioService.getUsuarioById(usuarioId).getSuscripciones();
	}
}
