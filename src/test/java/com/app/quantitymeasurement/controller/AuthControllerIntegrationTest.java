package com.app.quantitymeasurement.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.app.quantitymeasurement.model.AppUserEntity;
import com.app.quantitymeasurement.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AppUserRepository appUserRepository;

	@Test
	void registerNewUserAndLoginSuccessfully() {
		String name = "Test User";
		String email = randomEmail("user");
		String password = "Password@123";
		String mobileNumber = randomMobileNumber();
		HttpEntity<Map<String, String>> registerPayload = registerJsonEntity(name, email, password, mobileNumber);
		HttpEntity<Map<String, String>> loginPayload = loginJsonEntity(email, password);

		ResponseEntity<Map> registerResponse = restTemplate.postForEntity(authUrl("/register"), registerPayload, Map.class);

		assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(registerResponse.getBody()).isNotNull();
		assertThat(registerResponse.getBody().get("email")).isEqualTo(email);
		assertThat(registerResponse.getBody().get("accessToken")).isNotNull();
		assertThat(registerResponse.getBody().get("tokenType")).isEqualTo("Bearer");

		AppUserEntity storedUser = appUserRepository.findByEmail(email).orElse(null);
		assertThat(storedUser).isNotNull();
		assertThat(storedUser.getName()).isEqualTo(name);
		assertThat(storedUser.getMobileNumber()).isEqualTo(mobileNumber);
		assertThat(storedUser.getRole()).isEqualTo("ROLE_USER");

		ResponseEntity<Map> loginResponse = restTemplate.postForEntity(authUrl("/login"), loginPayload, Map.class);

		assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(loginResponse.getBody()).isNotNull();
		assertThat(loginResponse.getBody().get("email")).isEqualTo(email);
		assertThat(loginResponse.getBody().get("accessToken")).isNotNull();
	}

	@Test
	void registerDuplicateUserReturnsConflict() {
		String name = "Duplicate User";
		String email = randomEmail("dup");
		String password = "Password@123";
		String mobileNumber = randomMobileNumber();
		HttpEntity<Map<String, String>> payload = registerJsonEntity(name, email, password, mobileNumber);

		ResponseEntity<Map> firstResponse = restTemplate.postForEntity(authUrl("/register"), payload, Map.class);
		ResponseEntity<String> secondResponse = restTemplate.postForEntity(authUrl("/register"), payload, String.class);

		assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(secondResponse.getBody()).contains("Email already exists");
	}

	@Test
	void registerDuplicateMobileReturnsConflict() {
		String name1 = "First User";
		String name2 = "Second User";
		String email1 = randomEmail("mob1");
		String email2 = randomEmail("mob2");
		String password = "Password@123";
		String mobileNumber = randomMobileNumber();

		HttpEntity<Map<String, String>> firstPayload = registerJsonEntity(name1, email1, password, mobileNumber);
		HttpEntity<Map<String, String>> secondPayload = registerJsonEntity(name2, email2, password, mobileNumber);

		ResponseEntity<Map> firstResponse = restTemplate.postForEntity(authUrl("/register"), firstPayload, Map.class);
		ResponseEntity<String> secondResponse = restTemplate.postForEntity(authUrl("/register"), secondPayload,
				String.class);

		assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(secondResponse.getBody()).contains("Mobile number already exists");
	}

	private HttpEntity<Map<String, String>> registerJsonEntity(String name, String email, String password,
			String mobileNumber) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(
				Map.of("name", name, "email", email, "password", password, "mobileNumber", mobileNumber), headers);
	}

	private HttpEntity<Map<String, String>> loginJsonEntity(String email, String password) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(Map.of("email", email, "password", password), headers);
	}

	private String authUrl(String path) {
		return "http://localhost:" + port + "/api/v1/auth" + path;
	}

	private String randomMobileNumber() {
		long tenDigitNumber = ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);
		return "+1" + tenDigitNumber;
	}

	private String randomEmail(String prefix) {
		String token = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
		return prefix + "_" + token + "@example.com";
	}
}
