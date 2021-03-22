package com.fg.tests;

import org.junit.jupiter.api.Test;
import com.fc.domain.Localidad;
import com.fc.domain.Provincia;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.fc.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalidadTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	// LISTAR
	@Test
	public void testGetAllLocalidades() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/localidades", HttpMethod.GET, entity,
				String.class);

		Assert.assertNotNull(response.getBody());
	}

	// RECUPERAR POR ID
	@Test
	public void testGetLocalidadById() {
		Localidad localidad = restTemplate.getForObject(getRootUrl() + "/localidades/1", Localidad.class);
		System.out.println(localidad.getNombre());
		Assert.assertNotNull(localidad);
	}

	// CREAR
	@Test
	public void testCreateLocalidad() {
		Localidad localidad = new Localidad();
		localidad.setNombre("Valencia");
		localidad.setProvincia(new Provincia("Malaga"));

		ResponseEntity<Localidad> postResponse = restTemplate.postForEntity(getRootUrl() + "/localidades", localidad,
				Localidad.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	// ACTUALIZAR
	@Test
	public void testUpdateLocalidad() {
		int id = 1;
		Localidad localidad = restTemplate.getForObject(getRootUrl() + "/localidades/" + id, Localidad.class);
		localidad.setNombre("Barcelona");
		localidad.setProvincia(new Provincia("Lugo"));

		restTemplate.put(getRootUrl() + "/localidades/" + id, localidad);

		Localidad updatedProvincia = restTemplate.getForObject(getRootUrl() + "/localidades/" + id, Localidad.class);
		Assert.assertNotNull(updatedProvincia);
	}

	// BORRAR
	@Test
	public void testDeleteLocalidad() {
		int id = 2;
		Localidad localidad = restTemplate.getForObject(getRootUrl() + "/localidades/" + id, Localidad.class);
		Assert.assertNotNull(localidad);

		restTemplate.delete(getRootUrl() + "/localidades/" + id);

		try {
			localidad = restTemplate.getForObject(getRootUrl() + "/localidades/" + id, Localidad.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}