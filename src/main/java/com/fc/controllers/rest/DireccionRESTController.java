package com.fc.controllers.rest;

import java.util.ArrayList;
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

import com.fc.domain.Direccion;
import com.fc.domain.Localidad;
import com.fc.domain.Provincia;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.DireccionRepository;
import com.fc.repositories.LocalidadRepository;
import com.fc.repositories.ProvinciaRepository;
import com.fc.repositories.UsuarioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class DireccionRESTController {
	@Autowired
	private DireccionRepository direccionRepository;
	@Autowired
	private LocalidadRepository localidadRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

	// LISTAR
	@GetMapping("/direccion")
	public List<Direccion> getAllDirecciones() {
		return direccionRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/direccion/{id}")
	public ResponseEntity<Direccion> getDireccionById(@PathVariable(value = "id") Long direccionId)
			throws ResourceNotFoundException {
		Direccion direccion = encontrarDireccionPorId(direccionId);
		return ResponseEntity.ok().body(direccion);
	}

	// CREAR
	@PostMapping("/direccion")
	public Direccion createDireccion(@Valid @RequestBody Direccion direccion) throws ResourceNotFoundException {

		Usuario usuario = encontrarUsuarioPorId(direccion.getUsuario().getId());
		if (usuario.getDireccion() == null) {
			Localidad localidad = encontrarLocalidadIdProvinciaYNombreLocalidad(direccion.getLocalidad().getNombre(),
					direccion.getLocalidad().getProvincia().getId());
			if (localidad == null) {
				localidad = new Localidad();
				localidad.setNombre(direccion.getLocalidad().getNombre());
				Provincia provincia = encontrarProvinciaPorId(direccion.getLocalidad().getProvincia().getId());
				localidad.setProvincia(provincia);
				localidad.setDirecciones(new ArrayList<Direccion>());
				provincia.getLocalidades().add(localidad);
				localidad = localidadRepository.save(localidad);
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

	// ACTUALIZAR
	@PutMapping("/direccion/{id}")
	public ResponseEntity<Direccion> updateDireccion(@PathVariable(value = "id") Long direccionId,
			@Valid @RequestBody Direccion direccionDetails) throws ResourceNotFoundException {

		Direccion direccion = encontrarDireccionPorId(direccionId);
		direccion.setCalle(direccionDetails.getCalle());
		direccion.setNumero(direccionDetails.getNumero());
		direccion.setBloque(direccionDetails.getBloque());
		direccion.setEscalera(direccionDetails.getEscalera());
		direccion.setPiso(direccionDetails.getPiso());
		direccion.setPuerta(direccionDetails.getPuerta());
		direccion.setCodigoPostal(direccionDetails.getCodigoPostal());

		Localidad localidad = encontrarLocalidadIdProvinciaYNombreLocalidad(direccionDetails.getLocalidad().getNombre(),
				direccionDetails.getLocalidad().getProvincia().getId());
		if (localidad == null) {
			localidad = new Localidad();
			localidad.setNombre(direccion.getLocalidad().getNombre());
			Provincia provincia = encontrarProvinciaPorId(direccion.getLocalidad().getProvincia().getId());
			localidad.setProvincia(provincia);
			provincia.getLocalidades().add(localidad);
			localidad = localidadRepository.save(localidad);
		}
		localidad.getDirecciones().add(direccion);
		direccion.setLocalidad(localidad);

		final Direccion updatedDireccion = direccionRepository.save(direccion);
		return ResponseEntity.ok(updatedDireccion);
	}

	// BORRAR
	@DeleteMapping("/direccion/{id}")
	public Map<String, Boolean> deleteDireccion(@PathVariable(value = "id") Long direccionId) throws Exception {
		Direccion direccion = encontrarDireccionPorId(direccionId);
		direccionRepository.delete(direccion);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Localidad encontrarLocalidadPorId(Long localidadId) throws ResourceNotFoundException {
		Localidad localidad = localidadRepository.findById(localidadId)
				.orElseThrow(() -> new ResourceNotFoundException("Localidad not found on :: " + localidadId));
		return localidad;
	}

	public Localidad encontrarLocalidadIdProvinciaYNombreLocalidad(String localidadNombre, Long provinciaId) {
		Localidad localidad = localidadRepository.findOneByNombreAndProvinciaId(localidadNombre, provinciaId);
		return localidad;
	}

	public Provincia encontrarProvinciaPorId(Long provinciaId) throws ResourceNotFoundException {
		Provincia provincia = provinciaRepository.findById(provinciaId)
				.orElseThrow(() -> new ResourceNotFoundException("Provincia not found on :: " + provinciaId));
		return provincia;
	}

	public Direccion encontrarDireccionPorId(Long direccionId) throws ResourceNotFoundException {
		Direccion direccion = direccionRepository.findById(direccionId)
				.orElseThrow(() -> new ResourceNotFoundException("Direccion not found on :: " + direccionId));
		return direccion;
	}

	public Usuario encontrarUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return usuario;
	}
}
