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

import com.fc.domain.Rol;
import com.fc.domain.Suscripcion;
import com.fc.domain.Taquilla;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.RolRepository;
import com.fc.repositories.SuscripcionRepository;
import com.fc.repositories.TaquillaRepository;
import com.fc.repositories.UsuarioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class UsuarioRESTController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private TaquillaRepository taquillaRepository;
	@Autowired
	private SuscripcionRepository suscripcionRepository;

	// LISTAR
	@GetMapping("/usuario")
	public List<Usuario> getAllUsuarioes() {
		return usuarioRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") Long usuarioId)
			throws ResourceNotFoundException {
		Usuario usuario = encontrarUsuarioPorId(usuarioId);
		return ResponseEntity.ok().body(usuario);
	}

	// CREAR
	@PostMapping("/usuario")
	public Usuario createUsuario(@Valid @RequestBody Usuario usuario) throws ResourceNotFoundException {
		Rol rol = encontrarRolPorId(usuario.getRol().getId());
		usuario.setRol(rol);
		rol.getUsuarios().add(usuario);
		return usuarioRepository.save(usuario);
	}

	// ACTUALIZAR
	@PutMapping("/usuario/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable(value = "id") Long usuarioId,
			@Valid @RequestBody Usuario usuarioDetails) throws ResourceNotFoundException {

		Usuario usuario = encontrarUsuarioPorId(usuarioId);
		usuario.setNombre(usuarioDetails.getNombre());
		usuario.setApellidos(usuarioDetails.getApellidos());
		usuario.setDni(usuarioDetails.getDni());
		usuario.setEmail(usuarioDetails.getEmail());
		usuario.setPassword(usuarioDetails.getPassword());
		usuario.setTelefono(usuarioDetails.getTelefono());
		usuario.setFechaNacimiento(usuarioDetails.getFechaNacimiento());
		usuario.setCuentaBancaria(usuarioDetails.getCuentaBancaria());
		usuario.setBiografia(usuarioDetails.getBiografia());
		Rol rol = usuario.getRol();
		rol.getUsuarios().remove(usuario);
		rol = encontrarRolPorId(usuarioDetails.getRol().getId());
		usuario.setRol(rol);
		rol.getUsuarios().add(usuario);
		Taquilla taquilla = usuario.getTaquilla();
		if(taquilla != null) {
			taquilla.getUsuarios().remove(usuario);
		}
		taquilla = encontrarTaquillaPorId(usuarioDetails.getTaquilla().getId());
		usuario.setTaquilla(taquilla);
		taquilla.getUsuarios().add(usuario);
		final Usuario updatedUsuario = usuarioRepository.save(usuario);
		return ResponseEntity.ok(updatedUsuario);
	}

	// BORRAR
	@DeleteMapping("/usuario/{id}")
	public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") Long usuarioId) throws Exception {
		Usuario usuario = encontrarUsuarioPorId(usuarioId);
		usuarioRepository.delete(usuario);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Usuario encontrarUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return usuario;
	}

	public Rol encontrarRolPorId(Long rolId) throws ResourceNotFoundException {
		Rol rol = rolRepository.findById(rolId)
				.orElseThrow(() -> new ResourceNotFoundException("Rol not found on :: " + rolId));
		return rol;
	}
	
	public Taquilla encontrarTaquillaPorId(Long taquillaId) throws ResourceNotFoundException {
		Taquilla taquilla = taquillaRepository.findById(taquillaId)
				.orElseThrow(() -> new ResourceNotFoundException("Taquilla not found on :: " + taquillaId));
		return taquilla;
	}
	
	public Suscripcion encontrarSuscripcionPorId(Long suscripcionId) throws ResourceNotFoundException {
		Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId)
				.orElseThrow(() -> new ResourceNotFoundException("Suscripcion not found on :: " + suscripcionId));
		return suscripcion;
	}

}