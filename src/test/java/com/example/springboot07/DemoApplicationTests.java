package com.example.springboot07;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
		TestRestTemplate restTemplate = this.testRestTemplate.withBasicAuth("rwibawa", "Ch@ng3M3!");

		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = restTemplate.getForEntity(
			"http://localhost:" + this.port + "/greeting", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		TestRestTemplate restTemplate = this.testRestTemplate.withBasicAuth("actuator", "management");

		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = restTemplate.getForEntity(
			"http://localhost:" + this.mgt + "/configprops", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
