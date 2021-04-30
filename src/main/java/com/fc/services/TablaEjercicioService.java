package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.TablaEjercicio;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TablaEjercicioRepository;

@Service
public class TablaEjercicioService {

	@Autowired
	private TablaEjercicioRepository tablaEjercicioRepository;
	@Autowired
	private UsuarioService usuarioService;

	// DEVUELVE TODAS LAS TABLAEJERCICIO
	public List<TablaEjercicio> getAllTablaEjercicioes() {
		return tablaEjercicioRepository.findAll();
	}

	// DEVUELVE UNA TABLAEJERCICIO POR ID
	public TablaEjercicio getTablaEjercicioById(Long tablaEjercicioId) throws ResourceNotFoundException {
		TablaEjercicio tablaEjercicio = tablaEjercicioRepository.findById(tablaEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TablaEjercicio not found on :: " + tablaEjercicioId));
		return tablaEjercicio;
	}

	// CREA UNA NUEVA TABLAEJERCICIO
	public TablaEjercicio createTablaEjercicio(TablaEjercicio tablaEjercicio) throws ResourceNotFoundException {
		Usuario monitor = usuarioService.getUsuarioById(tablaEjercicio.getMonitor().getId());
		tablaEjercicio.setMonitor(monitor);
		monitor.getTablasEjercicioMonitor().add(tablaEjercicio);
		Usuario suscriptor = usuarioService.getUsuarioById(tablaEjercicio.getSuscriptor().getId());
		tablaEjercicio.setSuscriptor(suscriptor);
		suscriptor.getTablasEjercicioSuscriptor().add(tablaEjercicio);
		return tablaEjercicioRepository.save(tablaEjercicio);
	}

	// ACTUALIZA UNA TABLAEJERCICIO
	public TablaEjercicio updateTablaEjercicio(Long tablaEjercicioId, TablaEjercicio tablaEjercicioDetails)
			throws ResourceNotFoundException {

		TablaEjercicio tablaEjercicio = getTablaEjercicioById(tablaEjercicioId);
		tablaEjercicio.setFechaInicio(tablaEjercicioDetails.getFechaInicio());
		tablaEjercicio.setFechaFin(tablaEjercicioDetails.getFechaFin());
		tablaEjercicio.setActiva(tablaEjercicioDetails.getActiva());
		Usuario monitor = tablaEjercicio.getMonitor();
		monitor.getTablasEjercicioMonitor().remove(tablaEjercicio);
		monitor = usuarioService.getUsuarioById(tablaEjercicioDetails.getMonitor().getId());
		tablaEjercicio.setMonitor(monitor);
		monitor.getTablasEjercicioMonitor().add(tablaEjercicio);
		Usuario suscriptor = tablaEjercicio.getSuscriptor();
		suscriptor.getTablasEjercicioSuscriptor().remove(tablaEjercicio);
		suscriptor = usuarioService.getUsuarioById(tablaEjercicioDetails.getSuscriptor().getId());
		tablaEjercicio.setSuscriptor(suscriptor);
		suscriptor.getTablasEjercicioSuscriptor().add(tablaEjercicio);
		final TablaEjercicio updatedTablaEjercicio = tablaEjercicioRepository.save(tablaEjercicio);
		return updatedTablaEjercicio;
	}

	// BORRAR UNA TABLAEJERCICIO
	public Map<String, Boolean> deleteTablaEjercicio(Long tablaEjercicioId) throws Exception {
		TablaEjercicio tablaEjercicio = getTablaEjercicioById(tablaEjercicioId);
		tablaEjercicioRepository.delete(tablaEjercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
