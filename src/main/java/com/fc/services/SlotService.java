package com.fc.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.domain.Actividad;
import com.fc.domain.Sala;
import com.fc.domain.Slot;
import com.fc.domain.Usuario;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SlotRepository;

@Service
public class SlotService {

	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private ActividadService actividadService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private SalaService salaService;

	// DEVUELVE TODOS LOS ROLES
	public List<Slot> getSlots() {
		return slotRepository.findAll();
	}

	// DEVUELVE UN ROL POR ID
	public Slot getSlotById(Long slotId) throws ResourceNotFoundException {
		Slot slot = slotRepository.findById(slotId)
				.orElseThrow(() -> new ResourceNotFoundException("Slot not found on :: " + slotId));
		return slot;
	}

	// CREA UN NUEVO ROL
	public Slot saveSlot(Slot slot) throws ResourceNotFoundException {
		Actividad actividad = actividadService.getActividadById(slot.getActividad().getId());
		slot.setActividad(actividad);
		actividad.getSlots().add(slot);
		Usuario monitor = usuarioService.getUsuarioById(slot.getMonitor().getId());
		slot.setMonitor(monitor);
		monitor.getSlots().add(slot);
		Sala sala = salaService.getSalaById(slot.getSala().getId());
		slot.setSala(sala);
		sala.getSlots().add(slot);
		return slotRepository.save(slot);
	}

	// ACTUALIZA UN ROL
	public Slot updateSlot(Long slotId, Slot slotDetails) throws ResourceNotFoundException {
		Slot slot = getSlotById(slotId);
		slot.setAforoActual(slotDetails.getAforoActual());
		slot.setDiaSemana(slotDetails.getDiaSemana());
		slot.setHoraInicio(slotDetails.getHoraInicio());
		Actividad actividad = slot.getActividad();
		actividad.getSlots().remove(slot);
		actividad = actividadService.getActividadById(slotDetails.getActividad().getId());
		slot.setActividad(actividad);
		actividad.getSlots().add(slot);
		Usuario monitor = slot.getMonitor();
		monitor.getSlots().remove(slot);
		monitor = usuarioService.getUsuarioById(slotDetails.getMonitor().getId());
		slot.setMonitor(monitor);
		monitor.getSlots().add(slot);
		Sala sala = slot.getSala();
		sala.getSlots().remove(slot);
		sala = salaService.getSalaById(slotDetails.getSala().getId());
		slot.setSala(sala);
		sala.getSlots().add(slot);
		final Slot updatedSlot = slotRepository.save(slot);
		return updatedSlot;
	}

	// BORRAR UN ROL
	public Map<String, Boolean> deleteSlot(Long slotId) throws Exception {
		Slot slot = getSlotById(slotId);
		slotRepository.delete(slot);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
