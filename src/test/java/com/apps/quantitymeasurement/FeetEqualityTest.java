package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class FeetEqualityTest {
	// checking same value
	@Test
	public void testFeetEquality_SameValue() {
		assertEquals(true, QuantityMeasurementApp.compareFeet(1.0, 1.0));
	}

	// checking different values
	@Test
	public void testFeetEquality_DifferentValue() {
		assertEquals(false, QuantityMeasurementApp.compareFeet(1.0, 2.0));
	}

	// check for a null value
	@Test
	public void testFeetEquality_NullComparison() {
		Feet feet = new Feet(1.0);
		assertFalse(feet.equals(null));
	}

	// check for different classes
	@Test
	public void testFeetEquality_DifferentClass() {
		Feet feet1 = new Feet(1.0);
		String string = "1.0";
		assertEquals(false, feet1.equals(string));
	}

	// check for same Feet object
	@Test
	public void testFeetEquality_SameReference() {
		Feet feet = new Feet(1.0);
		assertEquals(true, feet.equals(feet));
	}
}
