package com.fc.controllers.rest;

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

import com.fc.domain.Reserva;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ReservaRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ReservaRESTController {

	@Autowired
	private ReservaRepository reservaRepository;

	// LISTAR
	@GetMapping("/reserva")
	public List<Reserva> getAllReservas() {
		return reservaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/reserva/{id}")
	public ResponseEntity<Reserva> getReservaById(@PathVariable(value = "id") Long reservaId)
			throws ResourceNotFoundException {
		Reserva reserva = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new ResourceNotFoundException("Reserva not found on :: " + reservaId));
		return ResponseEntity.ok().body(reserva);
	}

	// BORRAR
	@DeleteMapping("/reserva/{id}")
	public Map<String, Boolean> deleteReserva(@PathVariable(value = "id") Long reservaId) throws Exception {
		Reserva reserva = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new ResourceNotFoundException("Reserva not found on :: " + reservaId));

		reservaRepository.delete(reserva);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}