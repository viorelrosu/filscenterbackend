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

import com.fc.domain.Factura;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.FacturaRepository;
import com.fc.repositories.UsuarioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class FacturaRESTController {

	@Autowired
	private FacturaRepository facturaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

	// LISTAR
	@GetMapping("/factura")
	public List<Factura> getAllFacturaes() {
		return facturaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/factura/{id}")
	public ResponseEntity<Factura> getFacturaById(@PathVariable(value = "id") Long facturaId)
			throws ResourceNotFoundException {
		Factura factura = encontrarFacturaPorId(facturaId);
		return ResponseEntity.ok().body(factura);
	}

	// CREAR
	@PostMapping("/factura")
	public Factura createFactura(@Valid @RequestBody Factura factura) throws ResourceNotFoundException {
		Usuario usuario = encontrarUsuarioPorId(factura.getUsuario().getId());
		factura.setUsuario(usuario);
		usuario.getFacturas().add(factura);
		return facturaRepository.save(factura);
	}

	// ACTUALIZAR
	@PutMapping("/factura/{id}")
	public ResponseEntity<Factura> updateFactura(@PathVariable(value = "id") Long facturaId,
			@Valid @RequestBody Factura facturaDetails) throws ResourceNotFoundException {

		Factura factura = encontrarFacturaPorId(facturaId);
		factura.setFecha(facturaDetails.getFecha());
		factura.setNumero(facturaDetails.getNumero());
		factura.setImporte(facturaDetails.getImporte());
		factura.setPagado(facturaDetails.getPagado());
		Usuario usuario = factura.getUsuario();
		usuario.getFacturas().remove(factura);
		usuario = encontrarUsuarioPorId(facturaDetails.getUsuario().getId());
		factura.setUsuario(usuario);
		usuario.getFacturas().add(factura);
		final Factura updatedFactura = facturaRepository.save(factura);
		return ResponseEntity.ok(updatedFactura);
	}

	// BORRAR
	@DeleteMapping("/factura/{id}")
	public Map<String, Boolean> deleteFactura(@PathVariable(value = "id") Long facturaId) throws Exception {
		Factura factura = encontrarFacturaPorId(facturaId);
		facturaRepository.delete(factura);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Factura encontrarFacturaPorId(Long facturaId) throws ResourceNotFoundException {
		Factura factura = facturaRepository.findById(facturaId)
				.orElseThrow(() -> new ResourceNotFoundException("Factura not found on :: " + facturaId));
		return factura;
	}

	public Usuario encontrarUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return usuario;
	}

}