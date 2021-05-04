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

import com.fc.domain.EjercicioSerie;
import com.fc.exceptions.ResourceNotFoundException;
import com.fc.services.EjercicioSerieService;

@RestController
@RequestMapping("/webservice")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class EjercicioSerieRESTController {

	@Autowired
	private EjercicioSerieService ejercicioSerieService;

	// LISTAR
	@GetMapping("/ejercicioSerie")
	public List<EjercicioSerie> getAllEjercicioSeries() {
		return ejercicioSerieService.getAllEjercicioSeries();
	}

	// RECUPERAR POR ID
	@GetMapping("/ejercicioSerie/{id}")
	public EjercicioSerie getEjercicioSerieById(@PathVariable(value = "id") Long ejercicioSerieId)
			throws ResourceNotFoundException {
		return ejercicioSerieService.getEjercicioSerieById(ejercicioSerieId);
	}

	// CREAR
	@PostMapping("/ejercicioSerie")
	public EjercicioSerie createEjercicioSerie(@Valid @RequestBody EjercicioSerie ejercicioSerie)
			throws ResourceNotFoundException {
		return ejercicioSerieService.createEjercicioSerie(ejercicioSerie);
	}

	// ACTUALIZAR
	@PutMapping("/ejercicioSerie/{id}")
	public EjercicioSerie updateEjercicioSerie(@PathVariable(value = "id") Long ejercicioSerieId,
			@Valid @RequestBody EjercicioSerie ejercicioSerieDetails) throws ResourceNotFoundException {
		return ejercicioSerieService.updateEjercicioSerie(ejercicioSerieId, ejercicioSerieDetails);
	}

	// BORRAR
	@DeleteMapping("/ejercicioSerie/{id}")
	public Map<String, Boolean> deleteEjercicioSerie(@PathVariable(value = "id") Long ejercicioSerieId)
			throws Exception {
		return ejercicioSerieService.deleteEjercicioSerie(ejercicioSerieId);
	}
	
	//RECUPERAR EJERCICIOS POR TABLA ID
    @GetMapping("/ejercicioSerieTabla/{id}")
    public List<EjercicioSerie> getEjerciciosSerieByTablaId(@PathVariable(value = "id") Long tablaEjercicioId)
            throws ResourceNotFoundException {
        return ejercicioSerieService.getEjerciciosSerieByTablaEjercicio(tablaEjercicioId);
    }

}
