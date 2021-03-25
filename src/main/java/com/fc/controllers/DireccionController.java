package com.fc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fc.domain.Direccion;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.repositories.DireccionRepository;

@RestController
@RequestMapping("/webservice")
public class DireccionController {
    @Autowired
    private DireccionRepository direccionRepository;

 // LISTAR
 	@GetMapping("/direccion")
 	public List<Direccion> getAllDirecciones() {
 		return direccionRepository.findAll();
 	}

 	// RECUPERAR POR ID
 	@GetMapping("/direccion/{id}")
 	public ResponseEntity<Direccion> getDireccionById(@PathVariable(value = "id") Long direccionId)
 			throws ResourceNotFoundException {
 		Direccion direccion = direccionRepository.findById(direccionId)
 				.orElseThrow(() -> new ResourceNotFoundException("Direccion not found on :: " + direccionId));
 		return ResponseEntity.ok().body(direccion);
 	}

 	// CREAR
 	@PostMapping("/direccion")
 	public Direccion createDireccion(@Valid @RequestBody Direccion direccion) {
 		return direccionRepository.save(direccion);
 	}

 	// ACTUALIZAR
 	@PutMapping("/direccion/{id}")
 	public ResponseEntity<Direccion> updateDireccion(@PathVariable(value = "id") Long direccionId,
 			@Valid @RequestBody Direccion direccionDetails) throws ResourceNotFoundException {

 		Direccion direccion = direccionRepository.findById(direccionId)
 				.orElseThrow(() -> new ResourceNotFoundException("Direccion not found on :: " + direccionId));

 		direccion.setCalle(direccionDetails.getCalle());
 		direccion.setNumero(direccionDetails.getNumero());
 		direccion.setBloque(direccionDetails.getBloque());
 		direccion.setEscalera(direccionDetails.getEscalera());
 		direccion.setPiso(direccionDetails.getPiso());
 		direccion.setPuerta(direccionDetails.getPuerta());
 		direccion.setCodigoPostal(direccionDetails.getCodigoPostal());
 		direccion.setLocalidad(direccionDetails.getLocalidad());
 		final Direccion updatedDireccion = direccionRepository.save(direccion);
  	    return ResponseEntity.ok(updatedDireccion);
  	  }
  	
  	// BORRAR
  	@DeleteMapping("/direccion/{id}")
  	  public Map<String, Boolean> deleteDireccion(@PathVariable(value = "id") Long direccionId) throws Exception {
  	    Direccion direccion =
  	        direccionRepository
  	            .findById(direccionId)
  	            .orElseThrow(() -> new ResourceNotFoundException("Direccion not found on :: " + direccionId));

  	    direccionRepository.delete(direccion);
  	    Map<String, Boolean> response = new HashMap<>();
  	    response.put("deleted", Boolean.TRUE);
  	    return response;
  	  }
}
