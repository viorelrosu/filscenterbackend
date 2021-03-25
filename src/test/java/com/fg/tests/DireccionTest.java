package com.fg.tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.fc.Application;
import com.fc.domain.Direccion;
import com.fc.domain.Localidad;
import com.fc.domain.Provincia;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DireccionTest {

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
	public void testGetAllDirecciones() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/direccion", HttpMethod.GET, entity,
				String.class);

		Assert.assertNotNull(response.getBody());
	}

	// RECUPERAR POR ID
	@Test
	public void testGetDireccionById() {
		Direccion direccion = restTemplate.getForObject(getRootUrl() + "/direccion/1", Direccion.class);
		System.out.println(direccion);
		Assert.assertNotNull(direccion);
	}

	// CREAR
	@Test
	public void testCreateDireccion() {
		Direccion direccion = new Direccion();
		direccion.setCalle("Pintor Romero");
		direccion.setNumero(2);
 		direccion.setBloque("A");
 		direccion.setEscalera(3);
 		direccion.setPiso(2);
 		direccion.setPuerta("C");
 		direccion.setCodigoPostal(28856);
 		direccion.setLocalidad(new Localidad("Murcia",new Provincia("Orense")));

		ResponseEntity<Direccion> postResponse = restTemplate.postForEntity(getRootUrl() + "/direccion", direccion,
				Direccion.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	// ACTUALIZAR
	@Test
	public void testUpdateDireccion() {
		int id = 1;
		Direccion direccion = restTemplate.getForObject(getRootUrl() + "/direccion/" + id, Direccion.class);
		direccion.setCalle("Pintora Romera");
		direccion.setNumero(3);
 		direccion.setBloque("B");
 		direccion.setEscalera(7);
 		direccion.setPiso(1);
 		direccion.setPuerta("A");
 		direccion.setCodigoPostal(28756);
 		direccion.setLocalidad(new Localidad("Velilla",new Provincia("Manchester")));

		restTemplate.put(getRootUrl() + "/direccion/" + id, direccion);

		Direccion updatedProvincia = restTemplate.getForObject(getRootUrl() + "/direccion/" + id, Direccion.class);
		Assert.assertNotNull(updatedProvincia);
	}

	// BORRAR
	@Test
	public void testDeleteDireccion() {
		int id = 2;
		Direccion direccion = restTemplate.getForObject(getRootUrl() + "/direccion/" + id, Direccion.class);
		Assert.assertNotNull(direccion);

		restTemplate.delete(getRootUrl() + "/direccion/" + id);

		try {
			direccion = restTemplate.getForObject(getRootUrl() + "/direccion/" + id, Direccion.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
