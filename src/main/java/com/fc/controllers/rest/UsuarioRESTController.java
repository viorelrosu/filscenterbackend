package com.fc.controllers.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.UsuarioService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class UsuarioRESTController {

	@Autowired
	private UsuarioService usuarioService;

	// LISTAR
	@GetMapping("/usuario")
	public List<Usuario> getAllUsuarioes() {
		return usuarioService.getUsuarios();
	}

	// RECUPERAR POR ID
	@GetMapping("/usuario/{id}")
	public Usuario getUsuarioById(@PathVariable(value = "id") Long usuarioId) throws ResourceNotFoundException {
		return usuarioService.getUsuarioById(usuarioId);
	}

	// RECUPERAR POR EMAIL
	@GetMapping("/usuario/email/{email}")
	public Usuario getUsuarioByEmail(@PathVariable(value = "email") String usuarioEmail)
			throws ResourceNotFoundException {
		return usuarioService.getUsuarioByEmail(usuarioEmail);
	}

	// CREAR
	@PostMapping("/usuario")
	public Usuario createUsuario(@Valid @RequestBody Usuario usuario) throws ResourceNotFoundException {
		return usuarioService.saveUsuario(usuario);
	}

	// CREAR
	@PostMapping("/altaUsuario")
	public Usuario altaUsuario(@Valid @RequestBody Usuario usuario) throws ResourceNotFoundException {
		return usuarioService.altaUsuario(usuario);
	}

	// ACTUALIZAR
	@PutMapping("/usuario/{id}")
	public Usuario updateUsuario(@PathVariable(value = "id") Long usuarioId, @Valid @RequestBody Usuario usuarioDetails)
			throws ResourceNotFoundException {
		return usuarioService.updateUsuario(usuarioId, usuarioDetails);
	}

	// BORRAR
	@DeleteMapping("/usuario/{id}")
	public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") Long usuarioId) throws Exception {
		return usuarioService.deleteUsuario(usuarioId);
	}

}