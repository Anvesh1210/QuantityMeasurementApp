package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 1e-6;

	@Test
	public void testEquality_LitreToLitre_SameValue() {
		Quantity<VolumeUnit> l1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> l2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(l1, l2);
	}

	@Test
	public void testEquality_LitreToLitre_DifferentValue() {
		Quantity<VolumeUnit> l1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> l2 = new Quantity<>(2.0, VolumeUnit.LITRE);
		assertNotEquals(l1, l2);
	}

	@Test
	public void testEquality_LitreToMillilitre_EquivalentValue() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		assertEquals(litre, ml);
	}

	@Test
	public void testEquality_MillilitreToLitre_EquivalentValue() {
		Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(ml, litre);
	}

	@Test
	public void testEquality_LitreToGallon_EquivalentValue() {
		Quantity<VolumeUnit> litre = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
		assertEquals(litre, gallon);
	}

	@Test
	public void testEquality_GallonToLitre_EquivalentValue() {
		Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> litre = new Quantity<>(3.78541, VolumeUnit.LITRE);
		assertEquals(gallon, litre);
	}

	@Test
	public void testEquality_VolumeVsLength_Incompatible() {
		Quantity<VolumeUnit> vol = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<LengthUnit> len = new Quantity<>(1.0, LengthUnit.FEET);
		assertNotEquals(vol, len);
	}

	@Test
	public void testEquality_VolumeVsWeight_Incompatible() {
		Quantity<VolumeUnit> vol = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<WeightUnit> wgt = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertNotEquals(vol, wgt);
	}

	@Test
	public void testEquality_NullComparison() {
		Quantity<VolumeUnit> vol = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertNotEquals(vol, null);
	}

	@Test
	public void testEquality_SameReference() {
		Quantity<VolumeUnit> vol = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertEquals(vol, vol);
	}

	@Test
	public void testEquality_NullUnit() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Quantity<>(1.0, null);
		});
	}

	@Test
	public void testEquality_TransitiveProperty() {
		Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> c = new Quantity<>(1.0, VolumeUnit.LITRE);

		assertEquals(a, b);
		assertEquals(b, c);
		assertEquals(a, c);
	}

	@Test
	public void testEquality_ZeroValue() {
		Quantity<VolumeUnit> z1 = new Quantity<>(0.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> z2 = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
		assertEquals(z1, z2);
	}

	@Test
	public void testEquality_NegativeVolume() {
		Quantity<VolumeUnit> negL = new Quantity<>(-1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> negMl = new Quantity<>(-1000.0, VolumeUnit.MILLILITRE);
		assertEquals(negL, negMl);
	}

	@Test
	public void testEquality_LargeVolumeValue() {
		Quantity<VolumeUnit> largeMl = new Quantity<>(1_000_000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> largeL = new Quantity<>(1000.0, VolumeUnit.LITRE);
		assertEquals(largeMl, largeL);
	}

	@Test
	public void testEquality_SmallVolumeValue() {
		Quantity<VolumeUnit> smallL = new Quantity<>(0.001, VolumeUnit.LITRE);
		Quantity<VolumeUnit> smallMl = new Quantity<>(1.0, VolumeUnit.MILLILITRE);
		assertEquals(smallL, smallMl);
	}

	// CONVERSION TESTS

	@Test
	public void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = litre.convertTo(VolumeUnit.MILLILITRE);
		assertEquals(1000.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	@Test
	public void testConversion_MillilitreToLitre() {
		Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = ml.convertTo(VolumeUnit.LITRE);
		assertEquals(1.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void testConversion_GallonToLitre() {
		Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> result = gallon.convertTo(VolumeUnit.LITRE);
		assertEquals(3.78541, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_LitreToGallon() {
		Quantity<VolumeUnit> litre = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = litre.convertTo(VolumeUnit.GALLON);
		assertEquals(1.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_MillilitreToGallon() {
		Quantity<VolumeUnit> ml = new Quantity<>(3785.41, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = ml.convertTo(VolumeUnit.GALLON);
		assertEquals(1.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_SameUnit() {
		Quantity<VolumeUnit> litre = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = litre.convertTo(VolumeUnit.LITRE);
		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_ZeroValue() {
		Quantity<VolumeUnit> zero = new Quantity<>(0.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = zero.convertTo(VolumeUnit.MILLILITRE);
		assertEquals(0.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_NegativeValue() {
		Quantity<VolumeUnit> neg = new Quantity<>(-1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = neg.convertTo(VolumeUnit.MILLILITRE);
		assertEquals(-1000.0, result.getValue(), EPSILON);
	}

	@Test
	public void testConversion_RoundTrip() {
		Quantity<VolumeUnit> start = new Quantity<>(1.5, VolumeUnit.LITRE);
		Quantity<VolumeUnit> end = start.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
		assertEquals(start.getValue(), end.getValue(), EPSILON);
	}

	// --- ADDITION TESTS ---

	@Test
	public void testAddition_SameUnit_LitrePlusLitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(2.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(3.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void testAddition_SameUnit_MillilitrePlusMillilitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(1000.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	@Test
	public void testAddition_CrossUnit_LitrePlusMillilitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void testAddition_CrossUnit_MillilitrePlusLitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(2000.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	@Test
	public void testAddition_CrossUnit_GallonPlusLitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.GALLON);
		Quantity<VolumeUnit> q2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.GALLON, result.getUnit());
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Litre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.LITRE);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.LITRE, result.getUnit());
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Millilitre() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.MILLILITRE);
		assertEquals(2000.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Gallon() {
		Quantity<VolumeUnit> q1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.GALLON);
		assertEquals(2.0, result.getValue(), EPSILON);
		assertEquals(VolumeUnit.GALLON, result.getUnit());
	}

	@Test
	public void testAddition_Commutativity() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> res1 = q1.add(q2);
		Quantity<VolumeUnit> res2 = q2.add(q1);
		assertEquals(res1, res2);
	}

	@Test
	public void testAddition_WithZero() {
		Quantity<VolumeUnit> q1 = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(0.0, VolumeUnit.MILLILITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(5.0, result.getValue(), EPSILON);
	}

	@Test
	public void testAddition_NegativeValues() {
		Quantity<VolumeUnit> q1 = new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(-2000.0, VolumeUnit.MILLILITRE); // -2 Litres
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(3.0, result.getValue(), EPSILON);
	}

	@Test
	public void testAddition_LargeValues() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1e6, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1e6, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(2e6, result.getValue(), EPSILON);
	}

	@Test
	public void testAddition_SmallValues() {
		Quantity<VolumeUnit> q1 = new Quantity<>(0.001, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(0.002, VolumeUnit.LITRE);
		Quantity<VolumeUnit> result = q1.add(q2);
		assertEquals(0.003, result.getValue(), EPSILON);
	}

	// ENUM & HELPER TESTS

	@Test
	public void testVolumeUnitEnum_LitreConstant() {
		assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor(), EPSILON);
	}

	@Test
	public void testVolumeUnitEnum_MillilitreConstant() {
		assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor(), EPSILON);
	}

	@Test
	public void testVolumeUnitEnum_GallonConstant() {
		assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor(), EPSILON);
	}

	@Test
	public void testConvertToBaseUnit_LitreToLitre() {
		assertEquals(5.0, VolumeUnit.LITRE.convertToBaseUnit(5.0), EPSILON);
	}

	@Test
	public void testConvertToBaseUnit_MillilitreToLitre() {
		assertEquals(1.0, VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0), EPSILON);
	}

	@Test
	public void testConvertToBaseUnit_GallonToLitre() {
		assertEquals(3.78541, VolumeUnit.GALLON.convertToBaseUnit(1.0), EPSILON);
	}

	@Test
	public void testConvertFromBaseUnit_LitreToLitre() {
		assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2.0), EPSILON);
	}

	@Test
	public void testConvertFromBaseUnit_LitreToMillilitre() {
		assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0), EPSILON);
	}

	@Test
	public void testConvertFromBaseUnit_LitreToGallon() {
		assertEquals(1.0, VolumeUnit.GALLON.convertFromBaseUnit(3.78541), EPSILON);
	}

	// SYSTEM/COMPATIBILITY TESTS
	@Test
	public void testGenericQuantity_VolumeOperations_Consistency() {
		Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
		assertTrue(v instanceof Quantity);
	}

	@Test
	public void testScalability_VolumeIntegration() {
		assertNotNull(VolumeUnit.valueOf("LITRE"));
		assertNotNull(VolumeUnit.valueOf("MILLILITRE"));
	}

}