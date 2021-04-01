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
import com.fc.domain.Usuario;
import com.fc.domain.Actividad;
import com.fc.domain.Sala;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SlotRepository;
import com.fc.repositories.UsuarioRepository;
import com.fc.repositories.ActividadRepository;
import com.fc.repositories.SalaRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SlotRESTController {

	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private ActividadRepository actividadRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private SalaRepository salaRepository;

	// LISTAR
	@GetMapping("/slot")
	public List<Slot> getAllSlotes() {
		return slotRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/slot/{id}")
	public ResponseEntity<Slot> getSlotById(@PathVariable(value = "id") Long slotId)
			throws ResourceNotFoundException {
		Slot slot = encontrarSlotPorId(slotId);
		return ResponseEntity.ok().body(slot);
	}

	// CREAR
	@PostMapping("/slot")
	public Slot createSlot(@Valid @RequestBody Slot slot) throws ResourceNotFoundException {
		Actividad actividad = encontrarActividadPorId(slot.getActividad().getId());
		slot.setActividad(actividad);
		actividad.getSlots().add(slot);
		Usuario monitor = encontrarUsuarioPorId(slot.getMonitor().getId());
		slot.setMonitor(monitor);
		monitor.getSlots().add(slot);
		Sala sala = encontrarSalaPorId(slot.getSala().getId());
		slot.setSala(sala);
		sala.getSlots().add(slot);
		return slotRepository.save(slot);
	}

	// ACTUALIZAR
	@PutMapping("/slot/{id}")
	public ResponseEntity<Slot> updateSlot(@PathVariable(value = "id") Long slotId,
			@Valid @RequestBody Slot slotDetails) throws ResourceNotFoundException {

		Slot slot = encontrarSlotPorId(slotId);
		slot.setAforoActual(slotDetails.getAforoActual());
		slot.setDiaSemana(slotDetails.getDiaSemana());
		slot.setHoraInicio(slotDetails.getHoraInicio());
		Actividad actividad = slot.getActividad();
		actividad.getSlots().remove(slot);
		actividad = encontrarActividadPorId(slotDetails.getActividad().getId());
		slot.setActividad(actividad);
		actividad.getSlots().add(slot);
		Usuario monitor = slot.getMonitor();
		monitor.getSlots().remove(slot);
		monitor = encontrarUsuarioPorId(slotDetails.getMonitor().getId());
		slot.setMonitor(monitor);
		monitor.getSlots().add(slot);
		Sala sala = slot.getSala();
		sala.getSlots().remove(slot);
		sala = encontrarSalaPorId(slotDetails.getSala().getId());
		slot.setSala(sala);
		sala.getSlots().add(slot);
		final Slot updatedSlot = slotRepository.save(slot);
		return ResponseEntity.ok(updatedSlot);
	}

	// BORRAR
	@DeleteMapping("/slot/{id}")
	public Map<String, Boolean> deleteSlot(@PathVariable(value = "id") Long slotId) throws Exception {
		Slot slot = encontrarSlotPorId(slotId);
		slotRepository.delete(slot);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Slot encontrarSlotPorId(Long slotId) throws ResourceNotFoundException {
		Slot slot = slotRepository.findById(slotId)
				.orElseThrow(() -> new ResourceNotFoundException("Slot not found on :: " + slotId));
		return slot;
	}

	public Actividad encontrarActividadPorId(Long actividadId) throws ResourceNotFoundException {
		Actividad actividad = actividadRepository.findById(actividadId)
				.orElseThrow(() -> new ResourceNotFoundException("Actividad not found on :: " + actividadId));
		return actividad;
	}
	
	public Usuario encontrarUsuarioPorId(Long usuarioId) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + usuarioId));
		return usuario;
	}
	
	public Sala encontrarSalaPorId(Long salaId) throws ResourceNotFoundException {
		Sala sala = salaRepository.findById(salaId)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario not found on :: " + salaId));
		return sala;
	}

}
