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

import com.fc.domain.Factura;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.FacturaService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class FacturaRESTController {

	@Autowired
	private FacturaService facturaService;

	// LISTAR
	@GetMapping("/factura")
	public List<Factura> getAllFacturaes() {
		return facturaService.getFacturas();
	}

	// RECUPERAR POR ID
	@GetMapping("/factura/{id}")
	public Factura getFacturaById(@PathVariable(value = "id") Long facturaId) throws ResourceNotFoundException {
		return facturaService.getFacturaById(facturaId);
	}

	// CREAR
	@PostMapping("/factura")
	public Factura createFactura(@Valid @RequestBody Factura factura) throws ResourceNotFoundException {
		return facturaService.saveFactura(factura);
	}

	// ACTUALIZAR
	@PutMapping("/factura/{id}")
	public Factura updateFactura(@PathVariable(value = "id") Long facturaId, @Valid @RequestBody Factura facturaDetails)
			throws ResourceNotFoundException {
		return facturaService.updateFactura(facturaId, facturaDetails);
	}

	// BORRAR
	@DeleteMapping("/factura/{id}")
	public Map<String, Boolean> deleteFactura(@PathVariable(value = "id") Long facturaId) throws Exception {
		return facturaService.deleteFactura(facturaId);
	}

}