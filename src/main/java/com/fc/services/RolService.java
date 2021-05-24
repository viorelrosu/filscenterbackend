package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Rol;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.RolRepository;

@Service
public class RolService {

	@Autowired
	private RolRepository rolRepository;

	// DEVUELVE TODOS LOS ROLES
	public List<Rol> getRols() {
		return rolRepository.findAll();
	}

	// DEVUELVE UN ROL POR ID
	public Rol getRolById(Long rolId) throws ResourceNotFoundException {
		Rol rol = rolRepository.findById(rolId)
				.orElseThrow(() -> new ResourceNotFoundException("Rol not found on :: " + rolId));
		return rol;
	}

	// CREA UN NUEVO ROL
	public Rol saveRol(Rol rol) {
		if (validarRol(rol)) {
			return rolRepository.save(rol);
		} else {
			return null;
		}
	}

	// ACTUALIZA UN ROL
	public Rol updateRol(Long rolId, Rol rolDetails) throws ResourceNotFoundException {
		if (validarRol(rolDetails)) {
			Rol rol = getRolById(rolId);
			rol.setNombre(rolDetails.getNombre());
			final Rol updatedRol = saveRol(rol);
			return updatedRol;
		} else {
			return null;
		}
	}

	// BORRAR UN ROL
	public Map<String, Boolean> deleteRol(Long rolId) throws Exception {
		Rol rol = getRolById(rolId);
		rolRepository.delete(rol);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// VALIDAR
	public boolean validarRol(Rol rol) {
		if (rol.getNombre() != null && !rol.getNombre().contentEquals("")) {
			return true;
		}
		return false;
	}
}
