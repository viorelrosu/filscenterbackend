package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Localidad;
import com.fc.domain.Provincia;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.LocalidadRepository;

@Service
public class LocalidadService {

	@Autowired
	private LocalidadRepository localidadRepository;

	@Autowired
	private ProvinciaService provinciaService;

	// DEVUELVE TODAS LAS LOCALIDADES
	public List<Localidad> getAllLocalidades() {
		return localidadRepository.findAll();
	}

	// DEVUELVE UNA LOCALIDAD POR ID
	public Localidad getLocalidadById(Long localidadId) throws ResourceNotFoundException {
		return localidadRepository.findById(localidadId)
				.orElseThrow(() -> new ResourceNotFoundException("Localidad not found on :: " + localidadId));
	}

	// DEVUELVE UNA LOCALIDAD POR NOMBRE Y PROVINCIA
	public Localidad getLocalidadByByNombreAndProvinciaId(String nombre, Long provinciaId) {
		return localidadRepository.findOneByNombreAndProvinciaId(nombre, provinciaId);
	}

	// CREA UNA NUEVA LOCALIDAD
	public Localidad saveLocalidad(Localidad localidad) throws ResourceNotFoundException {
		if (validarLocalidad(localidad)) {
			Provincia provincia = provinciaService.getProvinciaById(localidad.getProvincia().getId());
			localidad.setProvincia(provincia);
			provincia.getLocalidades().add(localidad);
			return localidadRepository.save(localidad);
		} else {
			return null;
		}
	}

	// ACTUALIZA UNA LOCALIDAD
	public Localidad updateLocalidad(Long localidadId, Localidad localidadDetails) throws ResourceNotFoundException {
		if (validarLocalidad(localidadDetails)) {
			Localidad localidad = getLocalidadById(localidadId);
			localidad.setNombre(localidadDetails.getNombre());
			Provincia provincia = localidad.getProvincia();
			provincia.getLocalidades().remove(localidad);
			provincia = provinciaService.getProvinciaById(localidadDetails.getProvincia().getId());
			localidad.setProvincia(provincia);
			provincia.getLocalidades().add(localidad);
			final Localidad updatedLocalidad = localidadRepository.save(localidad);
			return updatedLocalidad;
		} else {
			return null;
		}
	}

	// BORRAR UNA LOCALIDAD
	public Map<String, Boolean> deleteLocalidad(Long localidadId) throws Exception {
		Localidad localidad = getLocalidadById(localidadId);
		List<Localidad> localidades = (List<Localidad>) localidad.getProvincia().getLocalidades();
		localidades.remove(localidad);
		localidad.getProvincia().setLocalidades(localidades);
		localidad.setProvincia(null);
		localidadRepository.delete(localidad);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// DEVUELVE UNA LISTA DE LOCALIDADES CORRESPONDIENTES A UNA PROVINCIA
	public List<Localidad> getLocalidadesByProvincia(Long provinciaId) throws ResourceNotFoundException {
		return (List<Localidad>) provinciaService.getProvinciaById(provinciaId).getLocalidades();
	}

	// VALIDAR
	public boolean validarLocalidad(Localidad localidad) {
		if (localidad.getNombre() != null && !localidad.getNombre().contentEquals("")) {
			if (localidad.getProvincia() != null) {
				return true;
			}
		}
		return false;
	}

}
