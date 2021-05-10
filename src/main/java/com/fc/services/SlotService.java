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
		List<Slot> slots = (List<Slot>) slot.getSala().getSlots();
		slots.remove(slot);
		slot.getSala().setSlots(slots);
		slot.setSala(null);
		slots = (List<Slot>) slot.getActividad().getSlots();
		slots.remove(slot);
		slot.getActividad().setSlots(slots);
		slot.setActividad(null);
		slots = (List<Slot>) slot.getMonitor().getSlots();
		slots.remove(slot);
		slot.getMonitor().setSlots(slots);
		slot.setMonitor(null);
		slotRepository.delete(slot);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	//DEVUELVE UNA LISTA DE SLOTS POR SALA
	public List<Slot> getSlotsBySala(Long salaId) throws ResourceNotFoundException {
		return (List<Slot>) salaService.getSalaById(salaId).getSlots();
	}

	//DEVUELVE UNA LISTA DE SLOTS POR SALA Y USUARIO
	public List<Slot> getSlotsBySalaAndUsuario(Long salaId, Long usuarioId) {
		return slotRepository.findBySalaIdAndMonitorId(salaId, usuarioId);
	}

	//DEVUELVE UNA LISTA DE SLOTS POR SALA Y ACTIVIDAD
	public List<Slot> getSlotsBySalaAndActividad(Long salaId, Long actividadId) {
		return slotRepository.findBySalaIdAndActividadId(salaId, actividadId);
	}

}
