package com.fc.controllers;

import com.fc.domain.Provincia;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.ProvinciaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ProvinciaController {

	@Autowired
	private ProvinciaRepository provinciaRepository;

	// LISTAR
	@GetMapping("/provincia")
	public List<Provincia> getAllProvincias() {
		System.out.println(provinciaRepository.findAll());
		return provinciaRepository.findAll();
	}

	// RECUPERAR POR ID
	@GetMapping("/provincia/{id}")
	public ResponseEntity<Provincia> getProvinciaById(@PathVariable(value = "id") Long provinciaId)
			throws ResourceNotFoundException {
		Provincia provincia = provinciaRepository.findById(provinciaId)
				.orElseThrow(() -> new ResourceNotFoundException("Provincia not found on :: " + provinciaId));
		return ResponseEntity.ok().body(provincia);
	}

	// CREAR
	@PostMapping("/provincia")
	public Provincia createProvincia(@Valid @RequestBody Provincia provincia) {
		return provinciaRepository.save(provincia);
	}

	// ACTUALIZAR
	@PutMapping("/provincia/{id}")
	  public ResponseEntity<Provincia> updateProvincia(
	      @PathVariable(value = "id") Long provinciaId, @Valid @RequestBody Provincia provinciaDetails)
	      throws ResourceNotFoundException {

	    Provincia provincia =
	        provinciaRepository
	            .findById(provinciaId)
	            .orElseThrow(() -> new ResourceNotFoundException("Provincia not found on :: " + provinciaId));

	    provincia.setNombre(provinciaDetails.getNombre());
	    final Provincia updatedProvincia = provinciaRepository.save(provincia);
	    return ResponseEntity.ok(updatedProvincia);
	  }
	
	// BORRAR
	@DeleteMapping("/provincia/{id}")
	  public Map<String, Boolean> deleteProvincia(@PathVariable(value = "id") Long provinciaId) throws Exception {
	    Provincia provincia =
	        provinciaRepository
	            .findById(provinciaId)
	            .orElseThrow(() -> new ResourceNotFoundException("Provincia not found on :: " + provinciaId));

	    provinciaRepository.delete(provincia);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("deleted", Boolean.TRUE);
	    return response;
	  }
}
