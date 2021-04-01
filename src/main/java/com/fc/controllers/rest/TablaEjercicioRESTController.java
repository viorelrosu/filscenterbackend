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

import com.fc.domain.TablaEjercicio;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TablaEjercicioRepository;
import com.fc.repositories.UsuarioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TablaEjercicioRESTController {

	@Autowired
	private TablaEjercicioRepository tablaEjercicioRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

	// LISTAR
	@GetMapping("/tablaEjercicio")
	public List<TablaEjercicio> getAllTablaEjercicioes() {
		return tablaEjercicioRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/tablaEjercicio/{id}")
	public ResponseEntity<TablaEjercicio> getTablaEjercicioById(@PathVariable(value = "id") Long tablaEjercicioId)
			throws ResourceNotFoundException {
		TablaEjercicio tablaEjercicio = encontrarTablaEjercicioPorId(tablaEjercicioId);
		return ResponseEntity.ok().body(tablaEjercicio);
	}

	// CREAR
	@PostMapping("/tablaEjercicio")
	public TablaEjercicio createTablaEjercicio(@Valid @RequestBody TablaEjercicio tablaEjercicio) throws ResourceNotFoundException {
		Usuario monitor = encontrarUsuarioPorId(tablaEjercicio.getMonitor().getId());
		tablaEjercicio.setMonitor(monitor);
		monitor.getTablasEjercicioMonitor().add(tablaEjercicio);
		Usuario suscriptor = encontrarUsuarioPorId(tablaEjercicio.getSuscriptor().getId());
		tablaEjercicio.setSuscriptor(suscriptor);
		suscriptor.getTablasEjercicioSuscriptor().add(tablaEjercicio);
		return tablaEjercicioRepository.save(tablaEjercicio);
	}

	// ACTUALIZAR
	@PutMapping("/tablaEjercicio/{id}")
	public ResponseEntity<TablaEjercicio> updateTablaEjercicio(@PathVariable(value = "id") Long tablaEjercicioId,
			@Valid @RequestBody TablaEjercicio tablaEjercicioDetails) throws ResourceNotFoundException {

		TablaEjercicio tablaEjercicio = encontrarTablaEjercicioPorId(tablaEjercicioId);
		tablaEjercicio.setFechaInicio(tablaEjercicioDetails.getFechaInicio());
		tablaEjercicio.setFechaFin(tablaEjercicioDetails.getFechaFin());
		tablaEjercicio.setActiva(tablaEjercicioDetails.getActiva());
		Usuario monitor = tablaEjercicio.getMonitor();
		monitor.getTablasEjercicioMonitor().remove(tablaEjercicio);
		monitor = encontrarUsuarioPorId(tablaEjercicioDetails.getMonitor().getId());
		tablaEjercicio.setMonitor(monitor);
		monitor.getTablasEjercicioMonitor().add(tablaEjercicio);
		Usuario suscriptor = tablaEjercicio.getSuscriptor();
		suscriptor.getTablasEjercicioSuscriptor().remove(tablaEjercicio);
		suscriptor = encontrarUsuarioPorId(tablaEjercicioDetails.getSuscriptor().getId());
		tablaEjercicio.setSuscriptor(suscriptor);
		suscriptor.getTablasEjercicioSuscriptor().add(tablaEjercicio);
		final TablaEjercicio updatedTablaEjercicio = tablaEjercicioRepository.save(tablaEjercicio);
		return ResponseEntity.ok(updatedTablaEjercicio);
	}

	// BORRAR
	@DeleteMapping("/tablaEjercicio/{id}")
	public Map<String, Boolean> deleteTablaEjercicio(@PathVariable(value = "id") Long tablaEjercicioId) throws Exception {
		TablaEjercicio tablaEjercicio = encontrarTablaEjercicioPorId(tablaEjercicioId);
		tablaEjercicioRepository.delete(tablaEjercicio);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public TablaEjercicio encontrarTablaEjercicioPorId(Long tablaEjercicioId) throws ResourceNotFoundException {
		TablaEjercicio tablaEjercicio = tablaEjercicioRepository.findById(tablaEjercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("TablaEjercicio not found on :: " + tablaEjercicioId));
		return tablaEjercicio;
	}

	public Usuario encontrarUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return usuario;
	}

}
