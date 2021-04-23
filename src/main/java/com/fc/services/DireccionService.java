package com.fc.services;

import java.util.ArrayList;

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
	
	// CREA UNA NUEVA DIRECCION
		public Direccion saveDireccion(Direccion direccion) throws ResourceNotFoundException {
			Usuario usuario = usuarioService.getUsuarioById(direccion.getUsuario().getId());
			if (usuario.getDireccion() == null) {
				Localidad localidad = localidadService.getLocalidadByByNombreAndProvinciaId(direccion.getLocalidad().getNombre(),
						direccion.getLocalidad().getProvincia().getId());
				if (localidad == null) {
					localidad = new Localidad();
					localidad.setNombre(direccion.getLocalidad().getNombre());
					Provincia provincia = provinciaService.getProvinciaById(direccion.getLocalidad().getProvincia().getId());
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

}
