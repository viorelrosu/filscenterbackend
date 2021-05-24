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
		if (validarReserva(reserva)) {
			Slot slot = slotService.getSlotById(reserva.getSlot().getId());
			reserva.setSlot(slot);
			slot.getReservas().add(reserva);
			Usuario usuario = usuarioService.getUsuarioById(reserva.getUsuario().getId());
			reserva.setUsuario(usuario);
			usuario.getReservas().add(reserva);
			return reservaRepository.save(reserva);
		} else {
			return null;
		}
	}

	// ACTUALIZAR
	public Reserva updateReserva(Long reservaId, Reserva reservaDetails) throws ResourceNotFoundException {
		if (validarReserva(reservaDetails)) {
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
		} else {
			return null;
		}
	}

	// BORRAR
	public Map<String, Boolean> deleteReserva(Long reservaId) throws Exception {
		Reserva reserva = getReservaById(reservaId);
		List<Reserva> reservas = (List<Reserva>) reserva.getUsuario().getReservas();
		reservas.remove(reserva);
		reserva.getUsuario().setReservas(reservas);
		reserva.setUsuario(null);
		reservas = (List<Reserva>) reserva.getSlot().getReservas();
		reservas.remove(reserva);
		reserva.getSlot().setReservas(reservas);
		reserva.setSlot(null);
		reservaRepository.delete(reserva);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// DEVUELVE UNA LISTA DE RESERVAS CORRESPONDIENTES A UN SLOT
	public List<Reserva> getReservasBySlot(Long slotId) throws ResourceNotFoundException {
		return (List<Reserva>) slotService.getSlotById(slotId).getReservas();
	}

	// DEVUELVE UNA LISTA DE RESERVAS CORRESPONDIENTES A UN USUARIO
	public List<Reserva> getReservasByUsuario(Long usuarioId) throws ResourceNotFoundException {
		return (List<Reserva>) usuarioService.getUsuarioById(usuarioId).getReservas();
	}

	// DEVUELVE UNA LISTA DE RESERVAS CORRESPONDIENTES A UN USUARIO Y UN SLOT
	public List<Reserva> getReservasBySlotAndUsuario(Long slotId, Long usuarioId) {
		return reservaRepository.findBySlotIdAndUsuarioId(slotId, usuarioId);
	}

	// VALIDAR
	public boolean validarReserva(Reserva reserva) {
		if (reserva.getRecurrente() != null) {
			if (reserva.getFechaInicio() != null) {
				if (reserva.getUsuario() != null) {
					if (reserva.getSlot() != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
