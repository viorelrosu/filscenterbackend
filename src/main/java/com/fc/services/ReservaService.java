package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Reserva;
import com.fc.domain.Slot;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ReservaRepository;

@Service
public class ReservaService {
	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private SlotService slotService;
	@Autowired
	private UsuarioService usuarioService;

	// LISTAR
	public List<Reserva> getAllReservas() {
		return reservaRepository.findAll();
	}

	// RECUPERAR POR ID
	public Reserva getReservaById(Long reservaId) throws ResourceNotFoundException {
		Reserva reserva = reservaRepository.findById(reservaId)
				.orElseThrow(() -> new ResourceNotFoundException("Reserva not found on :: " + reservaId));
		return reserva;
	}

	// CREAR
	public Reserva createReserva(Reserva reserva) throws ResourceNotFoundException {
		Slot slot = slotService.getSlotById(reserva.getSlot().getId());
		reserva.setSlot(slot);
		slot.getReservas().add(reserva);
		Usuario usuario = usuarioService.getUsuarioById(reserva.getUsuario().getId());
		reserva.setUsuario(usuario);
		usuario.getReservas().add(reserva);
		return reservaRepository.save(reserva);
	}

	// ACTUALIZAR
	public Reserva updateReserva(Long reservaId, Reserva reservaDetails) throws ResourceNotFoundException {

		Reserva reserva = getReservaById(reservaId);
		reserva.setRecurrente(reservaDetails.getRecurrente());
		reserva.setFechaInicio(reservaDetails.getFechaInicio());
		Slot slot = reserva.getSlot();
		slot.getReservas().remove(reserva);
		slot = slotService.getSlotById(reservaDetails.getSlot().getId());
		reserva.setSlot(slot);
		slot.getReservas().add(reserva);
		Usuario usuario = reserva.getUsuario();
		usuario.getReservas().remove(reserva);
		usuario = usuarioService.getUsuarioById(reservaDetails.getUsuario().getId());
		reserva.setUsuario(usuario);
		usuario.getReservas().add(reserva);
		final Reserva updatedReserva = reservaRepository.save(reserva);
		return updatedReserva;
	}

	// BORRAR
	public Map<String, Boolean> deleteReserva(Long reservaId) throws Exception {
		Reserva reserva = getReservaById(reservaId);
		reservaRepository.delete(reserva);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
