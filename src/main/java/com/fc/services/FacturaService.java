package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Factura;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.FacturaRepository;


@Service
public class FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;
	@Autowired
	private UsuarioService usuarioService;

	// DEVUELVE TODAS LAS FACTURAS
	public List<Factura> getFacturas() {
		return facturaRepository.findAll();
	}

	// DEVUELVE UNA FACTURA POR ID
	public Factura getFacturaById(Long facturaId) throws ResourceNotFoundException {
		Factura factura = facturaRepository.findById(facturaId)
				.orElseThrow(() -> new ResourceNotFoundException("Factura not found on :: " + facturaId));
		return factura;
	}

	// CREA UNA NUEVA FACTURA
	public Factura saveFactura(Factura factura) {
		return facturaRepository.save(factura);
	}

	// ACTUALIZA UNA FACTURA
	public Factura updateFactura(Long facturaId, Factura facturaDetails) throws ResourceNotFoundException {
		Factura factura = getFacturaById(facturaId);
		factura.setFecha(facturaDetails.getFecha());
		factura.setNumero(facturaDetails.getNumero());
		factura.setImporte(facturaDetails.getImporte());
		factura.setPagado(facturaDetails.getPagado());
		Usuario usuario = factura.getUsuario();
		usuario.getFacturas().remove(factura);
		usuario = usuarioService.getUsuarioById(facturaDetails.getUsuario().getId());
		factura.setUsuario(usuario);
		usuario.getFacturas().add(factura);
		final Factura updatedFactura = facturaRepository.save(factura);
		return updatedFactura;
	}

	// BORRAR UNA FACTURA
	public Map<String, Boolean> deleteFactura(Long facturaId) throws Exception {
		Factura factura = getFacturaById(facturaId);
		facturaRepository.delete(factura);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}