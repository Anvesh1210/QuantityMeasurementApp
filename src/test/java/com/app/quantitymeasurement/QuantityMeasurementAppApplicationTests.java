package com.app.quantitymeasurement;

import com.app.quantitymeasurement.dto.QuantityInputDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuantityMeasurementAppApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private static String bearerToken;

	private String baseUrl() {
		return "http://localhost:" + port + "/api/v1/quantities";
	}

	@BeforeAll
	static void init() {
		bearerToken = null;
	}

	@BeforeEach
	void setupUserAndLogin() {
		if (bearerToken != null) return;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// REGISTER (ignore failure if user already exists)
		try {
			restTemplate.postForEntity(
					"http://localhost:" + port + "/api/v1/auth/register",
					new HttpEntity<>(Map.of(
							"name", "Test User",
							"email", "appuser@example.com",
							"password", "password123",
							"mobileNumber", "+911234567890"
					), headers),
					Map.class
			);
		} catch (Exception ignored) {}

		// LOGIN
		ResponseEntity<Map> loginResponse = restTemplate.postForEntity(
				"http://localhost:" + port + "/api/v1/auth/login",
				new HttpEntity<>(Map.of(
						"email", "appuser@example.com",
						"password", "password123"
				), headers),
				Map.class
		);

		assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(loginResponse.getBody()).isNotNull();
		assertThat(loginResponse.getBody().get("accessToken")).isNotNull();

		bearerToken = "Bearer " + loginResponse.getBody().get("accessToken");

		System.out.println("TOKEN = " + bearerToken);
	}

	private HttpEntity<QuantityInputDTO> jsonEntity(QuantityInputDTO body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		assertThat(bearerToken).isNotNull();

		headers.setBearerAuth(bearerToken.replace("Bearer ", ""));
		return new HttpEntity<>(body, headers);
	}
}