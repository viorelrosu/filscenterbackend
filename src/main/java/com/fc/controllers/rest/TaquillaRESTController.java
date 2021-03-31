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

import com.fc.domain.Taquilla;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.TaquillaRepository;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class TaquillaRESTController {

	@Autowired
	private TaquillaRepository taquillaRepository;

	// LISTAR
	@GetMapping("/taquilla")
	public List<Taquilla> getAllTaquillas() {
		return taquillaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/taquilla/{id}")
	public ResponseEntity<Taquilla> getTaquillaById(@PathVariable(value = "id") Long taquillaId)
			throws ResourceNotFoundException {
		Taquilla taquilla = encontrarTaquillaPorId(taquillaId);
		return ResponseEntity.ok().body(taquilla);
	}

	// CREAR
	@PostMapping("/taquilla")
	public Taquilla createTaquilla(@Valid @RequestBody Taquilla taquilla) {
		return taquillaRepository.save(taquilla);
	}

	// ACTUALIZAR
	@PutMapping("/taquilla/{id}")
	public ResponseEntity<Taquilla> updateTaquilla(@PathVariable(value = "id") Long taquillaId,
			@Valid @RequestBody Taquilla taquillaDetails) throws ResourceNotFoundException {
		Taquilla taquilla = encontrarTaquillaPorId(taquillaId);
		taquilla.setNumero(taquillaDetails.getNumero());
		final Taquilla updatedTaquilla = taquillaRepository.save(taquilla);
		return ResponseEntity.ok(updatedTaquilla);
	}

	// BORRAR
	@DeleteMapping("/taquilla/{id}")
	public Map<String, Boolean> deleteTaquilla(@PathVariable(value = "id") Long taquillaId)
			throws Exception {
		Taquilla taquilla = encontrarTaquillaPorId(taquillaId);
		taquillaRepository.delete(taquilla);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	// METODOS

	public Taquilla encontrarTaquillaPorId(Long taquillaId) throws ResourceNotFoundException {
		Taquilla taquilla = taquillaRepository.findById(taquillaId).orElseThrow(
				() -> new ResourceNotFoundException("Taquilla not found on :: " + taquillaId));
		return taquilla;
	}

}
