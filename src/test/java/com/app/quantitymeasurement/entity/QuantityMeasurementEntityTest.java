package com.app.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.units.IMeasurable;
import com.app.quantitymeasurement.units.LengthUnit;

public class QuantityMeasurementEntityTest {

	@Test
	void testQuantityEntity_SingleOperandConstruction() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12, LengthUnit.INCHES);
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "COMPARE", true);
		assertEquals("COMPARE", entity.operation);
	}

	@Test
	void testQuantityEntity_ErrorConstruction() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "ADD", "error", true);
		assertTrue(entity.isError);
	}

	@Test
	void testQuantityEntity_ToString_Error() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "ADD", "error", true);
		assertTrue(entity.toString().contains("Error"));
	}

}