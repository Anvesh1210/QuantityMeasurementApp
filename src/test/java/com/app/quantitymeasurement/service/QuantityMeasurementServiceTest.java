package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceTest {

	private QuantityMeasurementServiceImpl service;
	private IQuantityMeasurementRepository repository;

	@BeforeEach
	void setup() {
		repository = mock(IQuantityMeasurementRepository.class);
		service = new QuantityMeasurementServiceImpl(repository);
	}

	@Test
	void testService_CompareEquality_SameUnit_Success() {
		QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(1, "FEET", "LENGTH");
		boolean result = service.compare(q1, q2);
		assertTrue(result);
	}

	@Test
	void testService_CompareEquality_DifferentUnit_Success() {
		QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTH");
		boolean result = service.compare(q1, q2);
		assertTrue(result);
	}

	@Test
	void testService_Convert_Success() {
		QuantityDTO meter = new QuantityDTO(1, "METERS", "LENGTH");
		QuantityDTO cm = new QuantityDTO(0, "CENTIMETERS", "LENGTH");
		QuantityDTO result = service.convert(meter, cm);
		assertEquals("CENTIMETERS", result.getUnit());
	}

	@Test
	void testService_Add_Success() {
		QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTH");
		QuantityDTO result = service.add(q1, q2);
		assertNotNull(result);
	}

	@Test
	void testService_Divide_ByZero_Error() {
		QuantityDTO q1 = new QuantityDTO(10, "KILOGRAM", "WEIGHT");
		QuantityDTO q2 = new QuantityDTO(0, "KILOGRAM", "WEIGHT");
		assertThrows(Exception.class, () -> service.division(q1, q2));
	}
}