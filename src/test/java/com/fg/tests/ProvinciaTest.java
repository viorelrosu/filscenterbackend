package com.fg.tests;

import org.junit.jupiter.api.Test;
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
public class ProvinciaTest {

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
	public void testGetAllProvincias() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/provincias", HttpMethod.GET, entity,
				String.class);

		Assert.assertNotNull(response.getBody());
	}
	
	// RECUPERAR POR ID
	@Test
	public void testGetProvinciaById() {
	Provincia provincia = restTemplate.getForObject(getRootUrl() + "/provincias/1", Provincia.class);
	System.out.println(provincia.getNombre());
	Assert.assertNotNull(provincia);
	}

	// CREAR
	@Test
	public void testCreateProvincia() {
	Provincia provincia = new Provincia();
	provincia.setNombre("Valencia");


	ResponseEntity<Provincia> postResponse = restTemplate.postForEntity(getRootUrl() + "/provincias", provincia, Provincia.class);
	Assert.assertNotNull(postResponse);
	Assert.assertNotNull(postResponse.getBody());
	}
	
	// ACTUALIZAR
	@Test
	public void testUpdateProvincia() {
	int id = 1;
	Provincia provincia = restTemplate.getForObject(getRootUrl() + "/provincias/" + id, Provincia.class);
	provincia.setNombre("Barcelona");


	restTemplate.put(getRootUrl() + "/provincias/" + id, provincia);

	Provincia updatedProvincia = restTemplate.getForObject(getRootUrl() + "/provincias/" + id, Provincia.class);
	Assert.assertNotNull(updatedProvincia);
	}

	// BORRAR
	@Test
	public void testDeleteProvincia() {
	int id = 2;
	Provincia provincia = restTemplate.getForObject(getRootUrl() + "/provincias/" + id, Provincia.class);
	Assert.assertNotNull(provincia);

	restTemplate.delete(getRootUrl() + "/provincias/" + id);

	try {
	provincia = restTemplate.getForObject(getRootUrl() + "/provincias/" + id, Provincia.class);
	} catch (final HttpClientErrorException e) {
	Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	}

}
