package com.fc.controllers;

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

import com.fc.domain.Slot;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.SlotRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class SlotRESTController {

	@Autowired
	private SlotRepository slotRepository;

	// LISTAR
	@GetMapping("/slot")
	public List<Slot> getAllSlots() {
		System.out.println(slotRepository.findAll());
		return slotRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/slot/{id}")
	public ResponseEntity<Slot> getSlotById(@PathVariable(value = "id") Long slotId)
			throws ResourceNotFoundException {
		Slot slot = slotRepository.findById(slotId)
				.orElseThrow(() -> new ResourceNotFoundException("Slot not found on :: " + slotId));
		return ResponseEntity.ok().body(slot);
	}

	// BORRAR
	@DeleteMapping("/slot/{id}")
	public Map<String, Boolean> deleteSlot(@PathVariable(value = "id") Long slotId) throws Exception {
		Slot slot = slotRepository.findById(slotId)
				.orElseThrow(() -> new ResourceNotFoundException("Slot not found on :: " + slotId));

		slotRepository.delete(slot);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
