package com.fc.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Direccion;
import com.fc.domain.Rol;
import com.fc.domain.Taquilla;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolService rolService;

	@Autowired
	private TaquillaService taquillaService;

	@Autowired
	private DireccionService direccionService;

	// DEVUELVE TODOS LOS USUARIOS
	public List<Usuario> getUsuarios() {
		return usuarioRepository.findAll();
	}

	// DEVUELVE UN USUARIO POR ID
	public Usuario getUsuarioById(Long usuarioId) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return usuario;
	}

	// DEVUELVE UN USUARIO POR EMAIL
	public Usuario getUsuarioByEmail(String email) throws ResourceNotFoundException {
		return usuarioRepository.findByEmail(email);
	}

	// CREA UN NUEVO USUARIO
	public Usuario saveUsuario(Usuario usuario) throws ResourceNotFoundException {
		Rol rol = rolService.getRolById(usuario.getRol().getId());
		usuario.setRol(rol);
		rol.getUsuarios().add(usuario);
		return usuarioRepository.save(usuario);
	}

	// DAR DE ALTA UN NUEVO USUARIO
	public Usuario altaUsuario(Usuario usuario) throws ResourceNotFoundException {
		Rol rol = rolService.getRolById(usuario.getRol().getId());
		usuario.setRol(rol);
		rol.getUsuarios().add(usuario);
		Direccion direccion = usuario.getDireccion();
		usuario.setDireccion(null);
		usuario = usuarioRepository.save(usuario);
		usuario.setNumeroTarjeta(usuario.getId());
		usuario = usuarioRepository.save(usuario);
		direccion.setUsuario(usuario);
		direccionService.saveDireccion(direccion);
		return getUsuarioById(usuario.getId());
	}

	// ACTUALIZA UN USUARIO
	public Usuario updateUsuario(Long usuarioId, Usuario usuarioDetails) throws ResourceNotFoundException {
		Usuario usuario = getUsuarioById(usuarioId);
		usuario.setNombre(usuarioDetails.getNombre());
		usuario.setApellidos(usuarioDetails.getApellidos());
		usuario.setDni(usuarioDetails.getDni());
		usuario.setEmail(usuarioDetails.getEmail());
		usuario.setPassword(usuarioDetails.getPassword());
		usuario.setTelefono(usuarioDetails.getTelefono());
		usuario.setFechaNacimiento(usuarioDetails.getFechaNacimiento());
		usuario.setCuentaBancaria(usuarioDetails.getCuentaBancaria());
		usuario.setBiografia(usuarioDetails.getBiografia());
		usuario.setImagen(usuarioDetails.getImagen());
		Direccion direccion = usuario.getDireccion();
		direccion = direccionService.updateDireccion(direccion.getId(), usuarioDetails.getDireccion());
		usuario.setDireccion(direccion);
		Rol rol = usuario.getRol();
		rol.getUsuarios().remove(usuario);
		rol = rolService.getRolById(usuarioDetails.getRol().getId());
		usuario.setRol(rol);
		rol.getUsuarios().add(usuario);
		Taquilla taquilla = usuario.getTaquilla();
		if (taquilla != null) {
			taquilla.getUsuarios().remove(usuario);
		}
		taquilla = taquillaService.getTaquillaById(usuarioDetails.getTaquilla().getId());
		usuario.setTaquilla(taquilla);
		taquilla.getUsuarios().add(usuario);
		final Usuario updatedUsuario = usuarioRepository.save(usuario);
		return updatedUsuario;
	}

	// BORRAR UN USUARIO
	public Map<String, Boolean> deleteUsuario(Long usuarioId) throws Exception {
		Usuario usuario = getUsuarioById(usuarioId);
		List<Usuario> usuarios = (List<Usuario>) usuario.getRol().getUsuarios();
		usuarios.remove(usuario);
		usuario.getRol().setUsuarios(usuarios);
		if (usuario.getTaquilla() != null) {
			usuarios = (List<Usuario>) usuario.getTaquilla().getUsuarios();
			usuarios.remove(usuario);
			usuario.getTaquilla().setUsuarios(usuarios);
		}
		Direccion direccion = usuario.getDireccion();
		direccion.setUsuario(null);
		usuario.setDireccion(null);
		usuarioRepository.delete(usuario);
		direccionService.deleteDireccion(direccion.getId());
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// ASIGNA UNA PASSWORD ALEATORIA A UN USUARIO
	public String resetPassword(Long id) throws ResourceNotFoundException, NoSuchAlgorithmException {
		Usuario usuario = getUsuarioById(id);
		String password = cadenaAleatoria(15);
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(password.getBytes());
	    byte[] digest = md.digest();
	    String passwordHash = DatatypeConverter
	      .printHexBinary(digest);
		usuario.setPassword(passwordHash);
		updateUsuario(id, usuario);
		return password;
	}

	public static String cadenaAleatoria(int longitud) {
		String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String cadena = "";
		for (int x = 0; x < longitud; x++) {
			int indiceAleatorio = ThreadLocalRandom.current().nextInt(0, banco.length() - 1);
			char caracterAleatorio = banco.charAt(indiceAleatorio);
			cadena += caracterAleatorio;
		}
		return cadena;
	}

	// DEVUELVE UNA LISTA DE USUARIOS CORRESPONDIENTES A UN ROL
	public List<Usuario> getUsuariosByRol(Long usuarioId) throws ResourceNotFoundException {
		return (List<Usuario>) rolService.getRolById(usuarioId).getUsuarios();
	}
}
