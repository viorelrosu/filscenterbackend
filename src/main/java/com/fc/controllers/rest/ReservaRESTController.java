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

import com.fc.domain.Slot;
import com.fc.domain.Reserva;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SlotRepository;
import com.fc.repositories.ReservaRepository;
import com.fc.repositories.UsuarioRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ReservaRESTController {

	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;

	// LISTAR
	@GetMapping("/reserva")
	public List<Reserva> getAllReservaes() {
		return reservaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/reserva/{id}")
	public ResponseEntity<Reserva> getReservaById(@PathVariable(value = "id") Long reservaId)
			throws ResourceNotFoundException {
		Reserva reserva = encontrarReservaPorId(reservaId);
		return ResponseEntity.ok().body(reserva);
	}

	// CREAR
	@PostMapping("/reserva")
	public Reserva createReserva(@Valid @RequestBody Reserva reserva) throws ResourceNotFoundException {
		Slot slot = encontrarSlotPorId(reserva.getSlot().getId());
		reserva.setSlot(slot);
		slot.getReservas().add(reserva);
		Usuario usuario = encontrarUsuarioPorId(reserva.getUsuario().getId());
		reserva.setUsuario(usuario);
		usuario.getReservas().add(reserva);
		return reservaRepository.save(reserva);
	}

	// ACTUALIZAR
	@PutMapping("/reserva/{id}")
	public ResponseEntity<Reserva> updateReserva(@PathVariable(value = "id") Long reservaId,
			@Valid @RequestBody Reserva reservaDetails) throws ResourceNotFoundException {

		Reserva reserva = encontrarReservaPorId(reservaId);
		reserva.setRecurrente(reservaDetails.getRecurrente());
		reserva.setFechaInicio(reservaDetails.getFechaInicio());
		Slot slot = reserva.getSlot();
		slot.getReservas().remove(reserva);
		slot = encontrarSlotPorId(reservaDetails.getSlot().getId());
		reserva.setSlot(slot);
		slot.getReservas().add(reserva);
		Usuario usuario = reserva.getUsuario();
		usuario.getReservas().remove(reserva);
		usuario = encontrarUsuarioPorId(reservaDetails.getUsuario().getId());
		reserva.setUsuario(usuario);
		usuario.getReservas().add(reserva);
		final Reserva updatedReserva = reservaRepository.save(reserva);
		return ResponseEntity.ok(updatedReserva);
	}

	// BORRAR
	@DeleteMapping("/reserva/{id}")
	public Map<String, Boolean> deleteReserva(@PathVariable(value = "id") Long reservaId) throws Exception {
		Reserva reserva = encontrarReservaPorId(reservaId);
		reservaRepository.delete(reserva);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Reserva encontrarReservaPorId(Long reservaId) throws ResourceNotFoundException {
		Reserva reserva = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new ResourceNotFoundException("Reserva not found on :: " + reservaId));
		return reserva;
	}

	public Slot encontrarSlotPorId(Long slotId) throws ResourceNotFoundException {
		Slot slot = slotRepository.findById(slotId)
				.orElseThrow(() -> new ResourceNotFoundException("Slot not found on :: " + slotId));
		return slot;
	}
	
	public Usuario encontrarUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return usuario;
	}

}