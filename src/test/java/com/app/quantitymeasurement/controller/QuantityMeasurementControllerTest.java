package com.app.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementControllerTest {

	private QuantityMeasurementController controller;
	private IQuantityMeasurementService service;

	@BeforeEach
	void setUp() {
		service = mock(IQuantityMeasurementService.class);
		controller = new QuantityMeasurementController(service);
	}

	@Test
	void testController_DemonstrateEquality_Success() {
		QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTH");
		when(service.compare(q1, q2)).thenReturn(true);
		boolean result = controller.performComparison(q1, q2);
		assertTrue(result);
		verify(service).compare(q1, q2);
	}

	@Test
	void testController_DemonstrateConversion_Success() {
		QuantityDTO meter = new QuantityDTO(1, "METERS", "LENGTH");
		QuantityDTO cm = new QuantityDTO(0, "CENTIMETERS", "LENGTH");
		QuantityDTO expected = new QuantityDTO(100, "CENTIMETERS", "LENGTH");
		when(service.convert(meter, cm)).thenReturn(expected);
		QuantityDTO result = controller.performConversion(meter, cm);
		assertEquals(100, result.getValue());
	}

	@Test
	void testController_DemonstrateAddition_Success() {
		QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTH");
		QuantityDTO expected = new QuantityDTO(2, "FEET", "LENGTH");
		when(service.add(q1, q2)).thenReturn(expected);
		QuantityDTO result = controller.performAddition(q1, q2);
		assertEquals(2, result.getValue());
	}

	@Test
	void testController_DemonstrateDivision_Success() {
		QuantityDTO q1 = new QuantityDTO(10, "KILOGRAM", "WEIGHT");
		QuantityDTO q2 = new QuantityDTO(5, "KILOGRAM", "WEIGHT");
		QuantityDTO expected = new QuantityDTO(2, "KILOGRAM", "WEIGHT");
		when(service.division(q1, q2)).thenReturn(expected);
		QuantityDTO result = controller.performDivision(q1, q2);
		assertEquals(2, result.getValue());
	}

	@Test
	void testController_NullService_Prevention() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityMeasurementController(null));
	}
}
