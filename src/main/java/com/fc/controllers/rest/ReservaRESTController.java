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

import com.fc.domain.Reserva;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.ReservaService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ReservaRESTController {

	@Autowired
	private ReservaService reservaService;

	// LISTAR
	@GetMapping("/reserva")
	public List<Reserva> getAllReservas() {
		return reservaService.getAllReservas();
	}

	// RECUPERAR POR ID
	@GetMapping("/reserva/{id}")
	public Reserva getReservaById(@PathVariable(value = "id") Long reservaId) throws ResourceNotFoundException {
		return reservaService.getReservaById(reservaId);
	}

	// CREAR
	@PostMapping("/reserva")
	public Reserva createReserva(@Valid @RequestBody Reserva reserva) throws ResourceNotFoundException {
		return reservaService.createReserva(reserva);
	}

	// ACTUALIZAR
	@PutMapping("/reserva/{id}")
	public Reserva updateReserva(@PathVariable(value = "id") Long reservaId, @Valid @RequestBody Reserva reservaDetails)
			throws ResourceNotFoundException {
		return reservaService.updateReserva(reservaId, reservaDetails);
	}

	// BORRAR
	@DeleteMapping("/reserva/{id}")
	public Map<String, Boolean> deleteReserva(@PathVariable(value = "id") Long reservaId) throws Exception {
		return reservaService.deleteReserva(reservaId);
	}

	// LISTAR RESERVAS EN UN SLOT
	@GetMapping("/reserva/slot/{id}")
	public List<Reserva> getReservasBySlot(@PathVariable(value = "id") Long slotId) throws ResourceNotFoundException {
		return reservaService.getReservasBySlot(slotId);
	}

	// LISTAR RESERVAS EN UN SLOT
	@GetMapping("/reserva/usuario/{id}")
	public List<Reserva> getReservasByUsuario(@PathVariable(value = "id") Long usuarioId) throws ResourceNotFoundException {
		return reservaService.getReservasByUsuario(usuarioId);
	}

}