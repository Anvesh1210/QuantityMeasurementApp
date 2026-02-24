package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 1e-6;

	// ---------------- Weight Equality Tests ----------------

	@Test
	public void testWeightEquality_KilogramToKilogram_SameValue() {
		assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Weight(1.0, WeightUnit.KILOGRAM)));
	}

	@Test
	public void testWeightEquality_KilogramToGram_EquivalentValue() {
		assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Weight(1000.0, WeightUnit.GRAM)));
	}

	@Test
	public void testWeightEquality_KilogramToPound_EquivalentValue() {
		assertTrue(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Weight(2.20462, WeightUnit.POUND)));
	}

	@Test
	public void testWeightEquality_ZeroValue() {
		assertTrue(new Weight(0.0, WeightUnit.KILOGRAM).equals(new Weight(0.0, WeightUnit.GRAM)));
	}

	@Test
	public void testWeightEquality_NegativeValue() {
		assertTrue(new Weight(-1.0, WeightUnit.KILOGRAM).equals(new Weight(-1000.0, WeightUnit.GRAM)));
	}

	@Test
	public void testWeightEquality_SameReference() {
		Weight w = new Weight(5.0, WeightUnit.KILOGRAM);
		assertTrue(w.equals(w));
	}

	@Test
	public void testWeightEquality_NullComparison() {
		assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(null));
	}

	@Test
	public void testWeightVsLength_Incompatible() {
		assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(new Length(1.0, LengthUnit.FEET)));
	}

	// ---------------- Weight Conversion Tests ----------------

	@Test
	public void testWeightConversion_KgToGram() {
		Weight result = new Weight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue(), EPSILON);
	}

	@Test
	public void testWeightConversion_KgToPound() {
		Weight result = new Weight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND);

		assertEquals(2.20462, result.getValue(), 1e-4);
	}

	@Test
	public void testWeightConversion_PoundToKg() {
		Weight result = new Weight(2.20462, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM);

		assertEquals(1.0, result.getValue(), 1e-4);
	}

	@Test
	public void testWeightConversion_RoundTrip() {
		Weight original = new Weight(2.5, WeightUnit.KILOGRAM);

		Weight converted = original.convertTo(WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);

		assertEquals(2.5, converted.getValue(), EPSILON);
	}

	@Test
	public void testWeightConversion_InvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Weight(1.0, null));
	}

	// ---------------- Weight Addition Tests ----------------

	@Test
	public void testWeightAddition_SameUnit() {
		Weight result = new Weight(2.0, WeightUnit.KILOGRAM).add(new Weight(3.0, WeightUnit.KILOGRAM));

		assertEquals(5.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	public void testWeightAddition_CrossUnit_KgPlusGram() {
		Weight result = new Weight(1.0, WeightUnit.KILOGRAM).add(new Weight(1000.0, WeightUnit.GRAM));

		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	public void testWeightAddition_CrossUnit_PoundPlusKg() {
		Weight result = new Weight(2.20462, WeightUnit.POUND).add(new Weight(1.0, WeightUnit.KILOGRAM));

		assertEquals(4.40924, result.getValue(), 1e-4);
		assertEquals(WeightUnit.POUND, result.getUnit());
	}

	@Test
	public void testWeightAddition_ExplicitTargetUnit() {
		Weight result = Weight.add(new Weight(1.0, WeightUnit.KILOGRAM), new Weight(1000.0, WeightUnit.GRAM),
				WeightUnit.GRAM);

		assertEquals(2000.0, result.getValue(), EPSILON);
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	@Test
	public void testWeightAddition_Commutativity() {
		Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
		Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

		assertTrue(w1.add(w2).equals(w2.add(w1)));
	}

	@Test
	public void testWeightAddition_WithZero() {
		Weight result = new Weight(5.0, WeightUnit.KILOGRAM).add(new Weight(0.0, WeightUnit.GRAM));

		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	public void testWeightAddition_NegativeValue() {
		Weight result = new Weight(5.0, WeightUnit.KILOGRAM).add(new Weight(-2000.0, WeightUnit.GRAM));
		assertEquals(3.0, result.getValue(), EPSILON);
	}

	@Test
	public void testWeightAddition_LargeValues() {
		Weight result = new Weight(1e6, WeightUnit.KILOGRAM).add(new Weight(1e6, WeightUnit.KILOGRAM));
		assertEquals(2e6, result.getValue(), EPSILON);
	}
}