package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 1e-6;
	
	// INTERFACE TESTS 

	@Test
	public void testIMeasurable_LengthUnitImplementation() {
		IMeasurable unit = LengthUnit.FEET;
		assertEquals(1.0, unit.getConversionFactor(), EPSILON);
	}

	@Test
	public void testIMeasurable_WeightUnitImplementation() {
		IMeasurable unit = WeightUnit.KILOGRAM;
		assertEquals(1.0, unit.getConversionFactor(), EPSILON);
	}


	// LENGTH TESTS 

	@Test
	public void testGenericQuantity_Length_Equality() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
		assertTrue(q1.equals(q2));
	}

	@Test
	public void testGenericQuantity_Length_Conversion() {
		Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = q.convertTo(LengthUnit.INCHES);
		assertEquals(12.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	public void testGenericQuantity_Length_Addition() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> result = q1.add(q2, LengthUnit.FEET);
		assertEquals(2.0, result.getValue(), EPSILON);
	}

	//WEIGHT TESTS
	@Test
	public void testGenericQuantity_Weight_Equality() {
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
		assertTrue(w1.equals(w2));
	}

	@Test
	public void testGenericQuantity_Weight_Conversion() {
		Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> result = w.convertTo(WeightUnit.GRAM);
		assertEquals(1000.0, result.getValue(), EPSILON);
	}

	@Test
	public void testGenericQuantity_Weight_Addition() {
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> result = w1.add(w2, WeightUnit.KILOGRAM);
		assertEquals(2.0, result.getValue(), EPSILON);
	}

	//CROSS CATEGORY PROTECTION 
	@Test
	public void testCrossCategoryPrevention_LengthVsWeight() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertFalse(length.equals(weight));
	}

	//CONSTRUCTOR VALIDATION

	@Test
	public void testConstructor_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
	}

	@Test
	public void testConstructor_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}

	// ADDITION EDGE CASES 

	@Test
	public void testAddition_ImplicitTarget() {
		Quantity<LengthUnit> q1 = new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = q1.add(q2);
		assertEquals(24.0, result.getValue(), EPSILON);
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	public void testAddition_Commutativity() {
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
		assertTrue(w1.add(w2).equals(w2.add(w1)));
	}

	@Test
	public void testAddition_WithZero() {
		Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.INCHES);
		Quantity<LengthUnit> result = q1.add(q2);
		assertEquals(5.0, result.getValue(), EPSILON);
	}

	//HASHCODE CONTRACT 

	@Test
	public void testHashCode_Consistency() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
		assertEquals(q1.hashCode(), q2.hashCode());
	}

	// ROUND TRIP CONVERSION 

	@Test
	public void testRoundTripConversion() {
		Quantity<WeightUnit> original = new Quantity<>(2.5, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> converted = original.convertTo(WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);
		assertEquals(2.5, converted.getValue(), EPSILON);
	}

	// IMMUTABILITY

	@Test
	public void testImmutability() {
		Quantity<LengthUnit> original = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> converted = original.convertTo(LengthUnit.INCHES);
		assertNotEquals(original.getUnit(), converted.getUnit());
		assertEquals(1.0, original.getValue(), EPSILON);
	}
}