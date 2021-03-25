package com.fc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.Factura;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.FacturaRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class FacturaRESTController {

	@Autowired
	private FacturaRepository facturaRepository;

	// LISTAR
	@GetMapping("/factura")
	public List<Factura> getAllFacturas() {
		System.out.println(facturaRepository.findAll());
		return facturaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/factura/{id}")
	public ResponseEntity<Factura> getFacturaById(@PathVariable(value = "id") Long facturaId)
			throws ResourceNotFoundException {
		Factura factura = facturaRepository.findById(facturaId)
				.orElseThrow(() -> new ResourceNotFoundException("Factura not found on :: " + facturaId));
		return ResponseEntity.ok().body(factura);
	}

	// BORRAR
	@DeleteMapping("/factura/{id}")
	public Map<String, Boolean> deleteFactura(@PathVariable(value = "id") Long facturaId) throws Exception {
		Factura factura = facturaRepository.findById(facturaId)
				.orElseThrow(() -> new ResourceNotFoundException("Factura not found on :: " + facturaId));

		facturaRepository.delete(factura);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}