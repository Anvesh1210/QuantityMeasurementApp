package com.app.quantitymeasurement;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuantityMeasurementAppApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	// ------------------ HELPERS ------------------

	private String baseUrl() {
		return "http://localhost:" + port + "/api/v1/quantities";
	}

	private QuantityInputDTO input(double thisValue, String thisUnit, String thisType, double thatValue,
			String thatUnit, String thatType) {
		QuantityInputDTO dto = new QuantityInputDTO();

		dto.setThisQuantityDTO(new QuantityDTO(thisValue, thisUnit, thisType));
		dto.setThatQuantityDTO(new QuantityDTO(thatValue, thatUnit, thatType));

		return dto;
	}

	private QuantityInputDTO inputWithTarget(double thisValue, String thisUnit, String thisType, double thatValue,
			String thatUnit, String thatType, double targetValue, String targetUnit, String targetType) {
		QuantityInputDTO dto = input(thisValue, thisUnit, thisType, thatValue, thatUnit, thatType);
		dto.setTargetQuantityDTO(new QuantityDTO(targetValue, targetUnit, targetType));
		return dto;
	}

	private HttpEntity<QuantityInputDTO> jsonEntity(QuantityInputDTO body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(body, headers);
	}

	// ------------------ TESTS ------------------

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(restTemplate).isNotNull();
		assertThat(port).isGreaterThan(0);
	}

	// ---------- COMPARE ----------

	@Test
	@Order(2)
	void testCompare_FootEqualsInches() {
		QuantityInputDTO body = input(1, "FEET", "LengthUnit", 12, "INCHES", "LengthUnit");

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(baseUrl() + "/compare",
				jsonEntity(body), QuantityMeasurementDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResultString()).isEqualTo("true");
	}

	@Test
	@Order(3)
	void testCompare_FootNotEqualInch() {
		QuantityInputDTO body = input(1, "FEET", "LengthUnit", 1, "INCHES", "LengthUnit");

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(baseUrl() + "/compare",
				jsonEntity(body), QuantityMeasurementDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResultString()).isEqualTo("false");
	}

	// ---------- CONVERT ----------

	@Test
	@Order(4)
	void testConvert_CelsiusToFahrenheit() {
		QuantityInputDTO body = input(100, "CELSIUS", "TemperatureUnit", 0, "FAHRENHEIT", "TemperatureUnit");

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(baseUrl() + "/convert",
				jsonEntity(body), QuantityMeasurementDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResultValue()).isEqualTo(212.0);
	}

	// ---------- ADD ----------

	@Test
	@Order(5)
	void testAdd_GallonAndLitre() {
		QuantityInputDTO body = input(1, "GALLON", "VolumeUnit", 3.785, "LITRE", "VolumeUnit");

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(baseUrl() + "/add",
				jsonEntity(body), QuantityMeasurementDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResultValue()).isEqualTo(2.0);
	}

	// ---------- ADD WITH TARGET ----------

	@Test
	@Order(6)
	void testAddWithTarget() {
		QuantityInputDTO body = inputWithTarget(1, "FEET", "LengthUnit", 12, "INCHES", "LengthUnit", 0, "INCHES",
				"LengthUnit");

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate
				.postForEntity(baseUrl() + "/add-with-target-unit", jsonEntity(body), QuantityMeasurementDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResultValue()).isEqualTo(24.0);
	}

	// ---------- SUBTRACT ----------

	@Test
	@Order(7)
	void testSubtract() {
		QuantityInputDTO body = input(2, "FEET", "LengthUnit", 12, "INCHES", "LengthUnit");

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(baseUrl() + "/subtract",
				jsonEntity(body), QuantityMeasurementDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResultValue()).isEqualTo(1.0);
	}

	// ---------- DIVIDE ----------

	@Test
	@Order(8)
	void testDivide() {
		QuantityInputDTO body = input(1, "YARDS", "LengthUnit", 1, "FEET", "LengthUnit");

		ResponseEntity<QuantityMeasurementDTO> response = restTemplate.postForEntity(baseUrl() + "/divide",
				jsonEntity(body), QuantityMeasurementDTO.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getResultValue()).isEqualTo(3.0);
	}

	// ---------- HISTORY ----------

	@Test
	@Order(9)
	void testHistory() {
		ResponseEntity<List> response = restTemplate.getForEntity(baseUrl() + "/history/operation/COMPARE", List.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotEmpty();
	}

	// ---------- ERROR ----------

	@Test
	@Order(10)
	void testDivideByZero_Error() {
		QuantityInputDTO body = input(1, "YARDS", "LengthUnit", 0, "FEET", "LengthUnit");

		ResponseEntity<String> response = restTemplate.postForEntity(baseUrl() + "/divide", jsonEntity(body),
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(response.getBody()).contains("Divide by zero");
	}
}