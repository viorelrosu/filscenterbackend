package com.fc.controllers.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.UsuarioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class UsuarioRESTController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// LISTAR
	@GetMapping("/usuario")
	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") Long usuarioId)
			throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return ResponseEntity.ok().body(usuario);
	}

	// BORRAR
	@DeleteMapping("/usuario/{id}")
	public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") Long usuarioId) throws Exception {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));

		usuarioRepository.delete(usuario);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}