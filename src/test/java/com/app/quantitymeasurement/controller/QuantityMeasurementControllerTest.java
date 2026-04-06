package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.OperationType;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.security.JwtService;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "USER")
public class QuantityMeasurementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IQuantityMeasurementService service;

	@MockBean
	private JwtService jwtService;

	@Autowired
	private ObjectMapper objectMapper;

	private QuantityInputDTO quantity1;
	private QuantityMeasurementDTO result;

	@BeforeEach
	public void setUp() {
		quantity1 = new QuantityInputDTO();
		quantity1.setThisQuantityDTO(new QuantityDTO(1.0, "FEET", "LENGTH"));
		quantity1.setThatQuantityDTO(new QuantityDTO(12.0, "INCHES", "LENGTH"));
		result = new QuantityMeasurementDTO();
		result.setThisValue(1.0);
		result.setThisUnit("FEET");
		result.setThisMeasurementType("LENGTH");
		result.setThatValue(12.0);
		result.setThatUnit("INCHES");
		result.setThatMeasurementType("LENGTH");
	}

	// COMPARE

	@Test
	public void testCompareQuantities_Success() throws Exception {
		QuantityMeasurementDTO result = new QuantityMeasurementDTO();
		result.setOperation(OperationType.COMPARE);
		result.setResultString("true");
		result.setError(false);
		Mockito.when(service.compare(quantity1.getThisQuantityDTO(), quantity1.getThatQuantityDTO()))
				.thenReturn(result);

		mockMvc.perform(post("/api/v1/quantities/compare").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(quantity1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.resultString").value("true"));
	}

	// ADD

	@Test
	public void testAddQuantities_Success() throws Exception {
		result.setOperation(OperationType.ADD);
		result.setResultValue(2.0);
		result.setResultUnit("FEET");
		result.setResultMeasurementType("LENGTH");
		result.setError(false);
		Mockito.when(service.add(quantity1.getThisQuantityDTO(), quantity1.getThatQuantityDTO())).thenReturn(result);
		mockMvc.perform(post("/api/v1/quantities/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(quantity1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.resultValue").value(2.0));
	}

	// SUBTRACT
	@Test
	public void testSubtractQuantities_Success() throws Exception {
		result.setOperation(OperationType.SUBTRACT);
		result.setResultValue(0.0);
		result.setResultUnit("FEET");
		result.setResultMeasurementType("LENGTH");
		result.setError(false);
		Mockito.when(service.subtract(quantity1.getThisQuantityDTO(), quantity1.getThatQuantityDTO()))
				.thenReturn(result);
		mockMvc.perform(post("/api/v1/quantities/subtract").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(quantity1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.resultValue").value(0.0));
	}

	// DIVISION
	@Test
	public void testDivision_Success() throws Exception {
		result.setOperation(OperationType.DIVIDE);
		result.setResultValue(1.0);
		result.setResultUnit("FEET");
		result.setResultMeasurementType("LENGTH");
		result.setError(false);
		Mockito.when(service.divide(quantity1.getThisQuantityDTO(), quantity1.getThatQuantityDTO())).thenReturn(result);
		mockMvc.perform(post("/api/v1/quantities/divide").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(quantity1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.resultValue").value(1.0));
	}

	// HISTORY
	@Test
	public void testGetOperationHistory_Success() throws Exception {
		Mockito.when(service.getOperationHistory("COMPARE")).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/api/v1/quantities/history/operation/COMPARE")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	// COUNT
	@Test
	public void testGetOperationCount_Success() throws Exception {
		Mockito.when(service.getOperationCount("COMPARE")).thenReturn(0L);
		mockMvc.perform(get("/api/v1/quantities/count/COMPARE")).andExpect(status().isOk())
				.andExpect(content().string("0"));
	}
}
