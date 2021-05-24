package com.fc.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fc.domain.Factura;
import com.fc.domain.Suscripcion;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.FacturaRepository;

@Service
public class FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private SuscripcionService suscripcionService;

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
		if (validarFactura(factura)) {
			return facturaRepository.save(factura);
		} else {
			return null;
		}
	}

	// ACTUALIZA UNA FACTURA
	public Factura updateFactura(Long facturaId, Factura facturaDetails) throws ResourceNotFoundException {
		if (validarFactura(facturaDetails)) {
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
		} else {
			return null;
		}
	}

	// BORRAR UNA FACTURA
	public Map<String, Boolean> deleteFactura(Long facturaId) throws Exception {
		Factura factura = getFacturaById(facturaId);
		List<Factura> facturas = (List<Factura>) factura.getUsuario().getFacturas();
		facturas.remove(factura);
		factura.getUsuario().setFacturas(facturas);
		factura.setUsuario(null);
		facturaRepository.delete(factura);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// DEVUELVE UNA LISTA DE FACTURAS CORRESPONDIENTES A UN USUARIO
	public List<Factura> getFacturasByUsuario(Long usuarioId) throws ResourceNotFoundException {
		return (List<Factura>) usuarioService.getUsuarioById(usuarioId).getFacturas();
	}

	// CREA UNA FACTURA A PARTIR DE UNA SUSCRIPCION
	public void cobrarSuscripcion(Suscripcion suscripcion) throws ResourceNotFoundException {
		Factura factura = new Factura();
		Usuario usuario = usuarioService.getUsuarioById(suscripcion.getUsuario().getId());
		factura.setFecha(new Date());
		factura.setImporte(suscripcion.getTipoSuscripcion().getTarifa());
		factura.setPagado(false);

		Factura last = facturaRepository.findTopByOrderByIdDesc();
		if (last != null) {
			factura.setNumero(last.getNumero() + 1);
		} else {
			factura.setNumero(1L);
		}

		usuario.getFacturas().add(factura);
		factura.setUsuario(usuario);
	}

	@Scheduled(cron = "0 0 2 1 * ?")
	public void cobroMensual() throws ResourceNotFoundException {
		for (Suscripcion suscripcion : suscripcionService.getSuscripciones()) {
			if (suscripcion.isRecurrente()) {
				cobrarSuscripcion(suscripcion);
			}
		}
	}

	// VALIDAR
	public boolean validarFactura(Factura factura) {
		if (factura.getFecha() != null) {
			if (factura.getNumero() != null) {
				if (factura.getImporte() != null) {
					if (factura.getPagado() != null) {
						if (factura.getUsuario() != null) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}