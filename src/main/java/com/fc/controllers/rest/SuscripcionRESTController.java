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

import com.fc.domain.Suscripcion;
import com.fc.domain.TipoSuscripcion;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SuscripcionRepository;
import com.fc.repositories.TipoSuscripcionRepository;
import com.fc.repositories.UsuarioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SuscripcionRESTController {

	@Autowired
	private SuscripcionRepository suscripcionRepository;
	
	@Autowired
	private TipoSuscripcionRepository tipoSuscripcionRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	// LISTAR
		@GetMapping("/suscripcion")
		public List<Suscripcion> getAllSuscripciones() {
			return suscripcionRepository.findAll();
		}

		// RECUPERAR POR ID
		@GetMapping("/suscripcion/{id}")
		public ResponseEntity<Suscripcion> getSuscripcionById(@PathVariable(value = "id") Long suscripcionId)
				throws ResourceNotFoundException {
			Suscripcion suscripcion = encontrarSuscripcionPorId(suscripcionId);
			return ResponseEntity.ok().body(suscripcion);
		}

		// CREAR
		@PostMapping("/suscripcion")
		public Suscripcion createSuscripcion(@Valid @RequestBody Suscripcion suscripcion) throws ResourceNotFoundException {
			TipoSuscripcion tipoSuscripcion = encontrarTipoSuscripcionPorId(suscripcion.getTipoSuscripcion().getId());
			suscripcion.setTipoSuscripcion(tipoSuscripcion);
			tipoSuscripcion.getSuscripciones().add(suscripcion);
			Usuario usuario = encontrarUsuarioPorId(suscripcion.getUsuario().getId());
			suscripcion.setUsuario(usuario);
			usuario.getSuscripciones().add(suscripcion);
			return suscripcionRepository.save(suscripcion);
		}

		// ACTUALIZAR
		@PutMapping("/suscripcion/{id}")
		public ResponseEntity<Suscripcion> updateSuscripcion(@PathVariable(value = "id") Long suscripcionId,
				@Valid @RequestBody Suscripcion suscripcionDetails) throws ResourceNotFoundException {

			Suscripcion suscripcion = encontrarSuscripcionPorId(suscripcionId);
			suscripcion.setFechaAlta(suscripcionDetails.getFechaAlta());
			suscripcion.setFechaBaja(suscripcionDetails.getFechaBaja());
			TipoSuscripcion tipoSuscripcion = suscripcion.getTipoSuscripcion();
			tipoSuscripcion.getSuscripciones().remove(suscripcion);
			tipoSuscripcion = encontrarTipoSuscripcionPorId(suscripcionDetails.getTipoSuscripcion().getId());
			suscripcion.setTipoSuscripcion(tipoSuscripcion);
			tipoSuscripcion.getSuscripciones().add(suscripcion);
			Usuario usuario = suscripcion.getUsuario();
			usuario.getSuscripciones().remove(suscripcion);
			usuario = encontrarUsuarioPorId(suscripcionDetails.getUsuario().getId());
			suscripcion.setUsuario(usuario);
			usuario.getSuscripciones().add(suscripcion);
			final Suscripcion updatedSuscripcion = suscripcionRepository.save(suscripcion);
			return ResponseEntity.ok(updatedSuscripcion);
		}

		// BORRAR
		@DeleteMapping("/suscripcion/{id}")
		public Map<String, Boolean> deleteSuscripcion(@PathVariable(value = "id") Long suscripcionId) throws Exception {
			Suscripcion suscripcion = encontrarSuscripcionPorId(suscripcionId);
			suscripcionRepository.delete(suscripcion);
			Map<String, Boolean> response = new HashMap<>();
			response.put("deleted", Boolean.TRUE);
			return response;
		}

		// METODOS

		public Suscripcion encontrarSuscripcionPorId(Long suscripcionId) throws ResourceNotFoundException {
			Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId)
					.orElseThrow(() -> new ResourceNotFoundException("Suscripcion not found on :: " + suscripcionId));
			return suscripcion;
		}

		public TipoSuscripcion encontrarTipoSuscripcionPorId(Long tipoSuscripcionId) throws ResourceNotFoundException {
			TipoSuscripcion tipoSuscripcion = tipoSuscripcionRepository.findById(tipoSuscripcionId)
					.orElseThrow(() -> new ResourceNotFoundException("TipoSuscripcion not found on :: " + tipoSuscripcionId));
			return tipoSuscripcion;
		}
		
		public Usuario encontrarUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
			Usuario usuario = usuarioRepository.findById(usuarioId)
					.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
			return usuario;
		}

}