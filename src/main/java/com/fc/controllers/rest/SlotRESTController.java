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

import com.fc.domain.Slot;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.SlotService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SlotRESTController {

	@Autowired
	private SlotService slotService;

	// LISTAR
	@GetMapping("/slot")
	public List<Slot> getAllSlots() {
		return slotService.getSlots();
	}

	// RECUPERAR POR ID
	@GetMapping("/slot/{id}")
	public Slot getRolById(@PathVariable(value = "id") Long slotId) throws ResourceNotFoundException {
		return slotService.getSlotById(slotId);
	}

	// CREAR
	@PostMapping("/slot")
	public Slot createSlot(@Valid @RequestBody Slot slot) throws ResourceNotFoundException {
		return slotService.saveSlot(slot);
	}

	// ACTUALIZAR
	@PutMapping("/slot/{id}")
	public Slot updateRol(@PathVariable(value = "id") Long slotId, @Valid @RequestBody Slot slotDetails)
			throws ResourceNotFoundException {
		return slotService.updateSlot(slotId, slotDetails);
	}

	// BORRAR
	@DeleteMapping("/slot/{id}")
	public Map<String, Boolean> deleteRol(@PathVariable(value = "id") Long rolId) throws Exception {
		return slotService.deleteSlot(rolId);
	}

	// LISTAR SLOTS POR SALA
	@GetMapping("/slot/sala/{id}")
	public List<Slot> getSlotBySala(@PathVariable(value = "id") Long salaId) throws ResourceNotFoundException {
		return slotService.getSlotsBySala(salaId);
	}

	// LISTAR SLOTS POR SALA Y USUARIO
	@GetMapping("/slot/sala/{salaId}/usuario/{usuarioId}")
	public List<Slot> getSlotBySalaAndUsuario(@PathVariable(value = "salaId") Long salaId, @PathVariable(value = "usuarioId") Long usuarioId) throws ResourceNotFoundException {
		return slotService.getSlotsBySalaAndUsuario(salaId,usuarioId);
	}

	// LISTAR SLOTS POR SALA Y ACTIVIDAD
	@GetMapping("/slot/sala/{salaId}/actividad/{actividadId}")
	public List<Slot> getSlotBySalaAndActividad(@PathVariable(value = "salaId") Long salaId, @PathVariable(value = "actividadId") Long actividadId) throws ResourceNotFoundException {
		return slotService.getSlotsBySalaAndActividad(salaId,actividadId);
	}

}
