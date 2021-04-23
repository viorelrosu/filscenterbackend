package com.fc.controllers.rest;

import com.fc.domain.Provincia;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.ProvinciaService;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ProvinciaRESTController {

	@Autowired
	private ProvinciaService provinciaService;

	// LISTAR
	@GetMapping("/provincia")
	public List<Provincia> getAllProvincias() {
		return provinciaService.getProvincias();
	}

	// RECUPERAR POR ID
	@GetMapping("/provincia/{id}")
	public Provincia getProvinciaById(@PathVariable(value = "id") Long provinciaId) throws ResourceNotFoundException {
		return provinciaService.getProvinciaById(provinciaId);
	}

	// CREAR
	@PostMapping("/provincia")
	public Provincia saveProvincia(@Valid @RequestBody Provincia provincia) {
		return provinciaService.saveProvincia(provincia);
	}

	// ACTUALIZAR
	@PutMapping("/provincia/{id}")
	public Provincia updateProvincia(@PathVariable(value = "id") Long provinciaId,
			@Valid @RequestBody Provincia provinciaDetails) throws ResourceNotFoundException {
		return provinciaService.updateProvincia(provinciaId, provinciaDetails);
	}

	// BORRAR
	@DeleteMapping("/provincia/{id}")
	public Map<String, Boolean> deleteProvincia(@PathVariable(value = "id") Long provinciaId) throws Exception {
		return provinciaService.deleteProvincia(provinciaId);
	}

}
