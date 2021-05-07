package com.fc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Direccion;
import com.fc.domain.Localidad;
import com.fc.domain.Provincia;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.DireccionRepository;

@Service
public class DireccionService {
	@Autowired
	private DireccionRepository direccionRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private LocalidadService localidadService;
	@Autowired
	private ProvinciaService provinciaService;

	// DEVUELVE TODAS LAS DIRECCIONES
	public List<Direccion> getAllDirecciones() {
		return direccionRepository.findAll();
	}

	// DEVUELVE UNA DIRECCION POR ID
	public Direccion getDireccionById(Long direccionId) throws ResourceNotFoundException {
		return direccionRepository.findById(direccionId)
				.orElseThrow(() -> new ResourceNotFoundException("Direccion not found on :: " + direccionId));
	}

	// CREA UNA NUEVA DIRECCION
	public Direccion saveDireccion(Direccion direccion) throws ResourceNotFoundException {
		Usuario usuario = usuarioService.getUsuarioById(direccion.getUsuario().getId());
		if (usuario.getDireccion() == null) {
			Localidad localidad = localidadService.getLocalidadByByNombreAndProvinciaId(
					direccion.getLocalidad().getNombre(), direccion.getLocalidad().getProvincia().getId());
			if (localidad == null) {
				localidad = new Localidad();
				localidad.setNombre(direccion.getLocalidad().getNombre());
				Provincia provincia = provinciaService
						.getProvinciaById(direccion.getLocalidad().getProvincia().getId());
				localidad.setProvincia(provincia);
				localidad.setDirecciones(new ArrayList<Direccion>());
				provincia.getLocalidades().add(localidad);
				localidad = localidadService.saveLocalidad(localidad);
			}
			direccion.setUsuario(usuario);
			direccion.setLocalidad(localidad);
			usuario.setDireccion(direccion);
			localidad.getDirecciones().add(direccion);
			return direccionRepository.save(direccion);
		} else {
			return usuario.getDireccion();
		}
	}

	// ACTUALIZA UNA DIRECCION
	public Direccion updateDireccion(Long direccionId, Direccion direccionDetails) throws ResourceNotFoundException {

		Direccion direccion = getDireccionById(direccionId);
		direccion.setCalle(direccionDetails.getCalle());
		direccion.setNumero(direccionDetails.getNumero());
		direccion.setBloque(direccionDetails.getBloque());
		direccion.setEscalera(direccionDetails.getEscalera());
		direccion.setPiso(direccionDetails.getPiso());
		direccion.setPuerta(direccionDetails.getPuerta());
		direccion.setCodigoPostal(direccionDetails.getCodigoPostal());

		Localidad localidad = localidadService.getLocalidadByByNombreAndProvinciaId(
				direccionDetails.getLocalidad().getNombre(), direccionDetails.getLocalidad().getProvincia().getId());
		if (localidad == null) {
			localidad = new Localidad();
			localidad.setNombre(direccion.getLocalidad().getNombre());
			Provincia provincia = provinciaService.getProvinciaById(direccion.getLocalidad().getProvincia().getId());
			localidad.setProvincia(provincia);
			provincia.getLocalidades().add(localidad);
			localidad = localidadService.saveLocalidad(localidad);
		}
		localidad.getDirecciones().add(direccion);
		direccion.setLocalidad(localidad);

		final Direccion updatedDireccion = direccionRepository.save(direccion);
		return updatedDireccion;
	}

	// BORRAR UNA DIRECCION
	public Map<String, Boolean> deleteDireccion(Long direccionId) throws Exception {
		Direccion direccion = getDireccionById(direccionId);
		if(direccion.getUsuario()!=null) {
			direccion.getUsuario().setDireccion(null);
			direccion.setUsuario(null);
		}
		List<Direccion> direcciones =(List<Direccion>) direccion.getLocalidad().getDirecciones();
		direcciones.remove(direccion);
		direccion.getLocalidad().setDirecciones(direcciones);
		direccion.setLocalidad(null);
		direccionRepository.delete(direccion);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
