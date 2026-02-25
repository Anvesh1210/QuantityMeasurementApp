package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 1e-6;

	// SUBTRACTION TESTS

	@Test
	void testSubtraction_SameUnit_FeetMinusFeet() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

		assertEquals(new Quantity<>(5.0, LengthUnit.FEET), q1.subtract(q2));
	}

	@Test
	void testSubtraction_SameUnit_LitreMinusLitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(10.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(3.0, VolumeUnit.LITRE);

		assertEquals(new Quantity<>(7.0, VolumeUnit.LITRE), q1.subtract(q2));
	}

	@Test
	void testSubtraction_CrossUnit_FeetMinusInches() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);

		assertEquals(new Quantity<>(9.5, LengthUnit.FEET), q1.subtract(q2));
	}

	@Test
	void testSubtraction_CrossUnit_InchesMinusFeet() {
		Quantity<LengthUnit> q1 = new Quantity<>(120.0, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

		assertEquals(new Quantity<>(60.0, LengthUnit.INCHES), q1.subtract(q2));
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Inches() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);

		assertEquals(new Quantity<>(114.0, LengthUnit.INCHES), q1.subtract(q2, LengthUnit.INCHES));
	}

	@Test
	void testSubtraction_ResultingInNegative() {
		Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(10.0, LengthUnit.FEET);

		assertEquals(new Quantity<>(-5.0, LengthUnit.FEET), q1.subtract(q2));
	}

	@Test
	void testSubtraction_ResultingInZero() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(120.0, LengthUnit.INCHES);

		assertEquals(new Quantity<>(0.0, LengthUnit.FEET), q1.subtract(q2));
	}

	@Test
	void testSubtraction_WithZeroOperand() {
		Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.INCHES);

		assertEquals(new Quantity<>(5.0, LengthUnit.FEET), q1.subtract(zero));
	}

	@Test
	void testSubtraction_NonCommutative() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);

		assertNotEquals(a.subtract(b), b.subtract(a));
	}

	@Test
	void testSubtraction_NullOperand() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
	}

	@Test
	void testSubtraction_CrossCategory() {
		Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> length.subtract((Quantity) weight));
	}

	@Test
	void testSubtraction_ChainedOperations() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

		Quantity<LengthUnit> result = q.subtract(new Quantity<>(2.0, LengthUnit.FEET))
				.subtract(new Quantity<>(1.0, LengthUnit.FEET));

		assertEquals(new Quantity<>(7.0, LengthUnit.FEET), result);
	}

	@Test
	void testSubtraction_Immutability() {
		Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);

		original.subtract(other);

		assertEquals(new Quantity<>(10.0, LengthUnit.FEET), original);
	}

	// DIVISION TESTS

	@Test
	void testDivision_SameUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

		assertEquals(5.0, q1.divide(q2), EPSILON);
	}

	@Test
	void testDivision_CrossUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

		assertEquals(1.0, q1.divide(q2), EPSILON);
	}

	@Test
	void testDivision_RatioGreaterThanOne() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

		assertEquals(5.0, q1.divide(q2), EPSILON);
	}

	@Test
	void testDivision_RatioLessThanOne() {
		Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(10.0, LengthUnit.FEET);
		assertEquals(0.5, q1.divide(q2), EPSILON);
	}

	@Test
	void testDivision_RatioEqualToOne() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(10.0, LengthUnit.FEET);
		assertEquals(1.0, q1.divide(q2), EPSILON);
	}

	@Test
	void testDivision_NonCommutative() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
		assertNotEquals(a.divide(b), b.divide(a));
	}

	@Test
	void testDivision_ByZero() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.FEET);
		assertThrows(ArithmeticException.class, () -> q1.divide(zero));
	}

	@Test
	void testDivision_LargeRatio() {
		Quantity<WeightUnit> q1 = new Quantity<>(1e6, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> q2 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertEquals(1e6, q1.divide(q2), EPSILON);
	}

	@Test
	void testDivision_SmallRatio() {
		Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> q2 = new Quantity<>(1e6, WeightUnit.KILOGRAM);
		assertEquals(1e-6, q1.divide(q2), EPSILON);
	}

	@Test
	void testDivision_NullOperand() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.divide(null));
	}

	@Test
	void testDivision_CrossCategory() {
		Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);
		assertThrows(IllegalArgumentException.class, () -> length.divide((Quantity) weight));
	}

	@Test
	void testDivision_Immutability() {
		Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);
		original.divide(other);
		assertEquals(new Quantity<>(10.0, LengthUnit.FEET), original);
	}

}