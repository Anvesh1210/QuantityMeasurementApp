package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

//Test class
public class QuantityMeasurementAppTest {
	
	//checking same value
	@Test
	public void testFeetEquality_SameValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);
		assertEquals(true, feet1.equals(feet2));
	}
	
	//checking different values
	@Test
	public void testFeetEquality_DifferentValue() {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(2.0);
		assertEquals(false, feet1.equals(feet2));
	}
	
	//check for a null value 
	@Test
	public void testFeetEquality_NullComparison() {
		Feet feet = new Feet(1.0);
		assertFalse(feet.equals(null));
	}
	
	//check for different classes
	@Test
	public void testFeetEquality_DifferentClass() {
		Feet feet1 = new Feet(1.0);
		String string = "1.0";
		assertEquals(false, feet1.equals(string));
	}
	
	//check for same Feet object
	@Test
	public void testFeetEquality_SameReference() {
		Feet feet = new Feet(1.0);
		assertEquals(true, feet.equals(feet));
	}
}
