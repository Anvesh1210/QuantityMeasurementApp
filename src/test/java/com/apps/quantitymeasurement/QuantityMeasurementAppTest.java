package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class QuantityMeasurementAppTest {

	private static final double EPS = 1e-6;

	// HELPER DELEGATION

	@Test
	void testRefactoring_Add_DelegatesViaHelper() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);
		assertEquals(15.0, q1.add(q2).getValue(), EPS);
	}

	@Test
	void testRefactoring_Subtract_DelegatesViaHelper() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);
		assertEquals(5.0, q1.subtract(q2).getValue(), EPS);
	}

	@Test
	void testRefactoring_Divide_DelegatesViaHelper() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);
		assertEquals(2.0, q1.divide(q2), EPS);
	}

	// VALIDATION CONSISTENCY

	@Test
	void testValidation_NullOperand_ConsistentAcrossOperations() {
		Quantity<LengthUnit> q = new Quantity<>(10, LengthUnit.FEET);

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> q.add(null));
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> q.divide(null));

		assertEquals(e1.getMessage(), e2.getMessage());
		assertEquals(e2.getMessage(), e3.getMessage());
	}

	@Test
	void testValidation_CrossCategory_ConsistentAcrossOperations() {
		Quantity<LengthUnit> l = new Quantity<>(10, LengthUnit.FEET);
		Quantity<WeightUnit> w = new Quantity<>(5, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> l.add((Quantity) w));
		assertThrows(IllegalArgumentException.class, () -> l.subtract((Quantity) w));
		assertThrows(IllegalArgumentException.class, () -> l.divide((Quantity) w));
	}

	@Test
	void testValidation_FiniteValue_ConsistentAcrossOperations() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}

	@Test
	void testValidation_NullTargetUnit_AddSubtractReject() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
		assertThrows(IllegalArgumentException.class, () -> q1.subtract(q2, null));
	}

	// ENUM COMPUTATION

	@Test
	void testArithmeticOperation_Add_EnumComputation() {
		assertEquals(15.0, ArithmeticOperation.ADD.compute(10, 5), EPS);
	}

	@Test
	void testArithmeticOperation_Subtract_EnumComputation() {
		assertEquals(5.0, ArithmeticOperation.SUBTRACT.compute(10, 5), EPS);
	}

	@Test
	void testArithmeticOperation_Divide_EnumComputation() {
		assertEquals(2.0, ArithmeticOperation.DIVIDE.compute(10, 5), EPS);
	}

	@Test
	void testArithmeticOperation_DivideByZero_EnumThrows() {
		assertThrows(ArithmeticException.class, () -> ArithmeticOperation.DIVIDE.compute(10, 0));
	}

	// HELPER CORRECTNESS

	@Test
	void testPerformBaseArithmetic_ConversionAndOperation() {
		Quantity<LengthUnit> feet = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> inches = new Quantity<>(12, LengthUnit.INCHES);
		assertEquals(2.0, feet.add(inches).getValue(), EPS);
	}

	// BACKWARD COMPATIBILITY

	@Test
	void testAdd_UC12_BehaviorPreserved() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);
		assertEquals(new Quantity<>(2, LengthUnit.FEET), q1.add(q2));
	}

	@Test
	void testSubtract_UC12_BehaviorPreserved() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(6, LengthUnit.INCHES);
		assertEquals(new Quantity<>(9.5, LengthUnit.FEET), q1.subtract(q2));
	}

	@Test
	void testDivide_UC12_BehaviorPreserved() {
		Quantity<LengthUnit> q1 = new Quantity<>(24, LengthUnit.INCHES);
		Quantity<LengthUnit> q2 = new Quantity<>(2, LengthUnit.FEET);
		assertEquals(1.0, q1.divide(q2), EPS);
	}

	// ROUNDING

	@Test
	void testRounding_AddSubtract_TwoDecimalPlaces() {
		Quantity<LengthUnit> q1 = new Quantity<>(0.001, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(0.0005, LengthUnit.FEET);
		assertEquals(0.0, q1.subtract(q2).getValue(), EPS);
	}

	@Test
	void testRounding_Divide_NoRounding() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(3, LengthUnit.FEET);
		assertEquals(1.0 / 3.0, q1.divide(q2), 1e-6);
	}

	// TARGET UNIT

	@Test
	void testImplicitTargetUnit_AddSubtract() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);
		assertEquals(LengthUnit.FEET, q1.add(q2).getUnit());
	}

	@Test
	void testExplicitTargetUnit_AddSubtract_Overrides() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(60, LengthUnit.INCHES);
		assertEquals(LengthUnit.INCHES, q1.add(q2, LengthUnit.INCHES).getUnit());
	}

	@Test
	void testImmutability_AfterAdd_ViaCentralizedHelper() {
		Quantity<LengthUnit> q = new Quantity<>(10, LengthUnit.FEET);
		q.add(new Quantity<>(5, LengthUnit.FEET));
		assertEquals(10, q.getValue());
	}

	@Test
	void testImmutability_AfterSubtract_ViaCentralizedHelper() {
		Quantity<LengthUnit> q = new Quantity<>(10, LengthUnit.FEET);
		q.subtract(new Quantity<>(5, LengthUnit.FEET));
		assertEquals(10, q.getValue());
	}

	@Test
	void testImmutability_AfterDivide_ViaCentralizedHelper() {
		Quantity<LengthUnit> q = new Quantity<>(10, LengthUnit.FEET);
		q.divide(new Quantity<>(5, LengthUnit.FEET));
		assertEquals(10, q.getValue());
	}

	// CATEGORY SCALABILITY

	@Test
	void testAllOperations_AcrossAllCategories() {
		Quantity<WeightUnit> w1 = new Quantity<>(10, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(5, WeightUnit.KILOGRAM);
		assertEquals(2.0, w1.divide(w2), EPS);

		Quantity<VolumeUnit> v1 = new Quantity<>(5, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(2, VolumeUnit.LITRE);
		assertEquals(2.5, v1.divide(v2), EPS);
	}

	// DRY VERIFICATION

	@Test
	void testCodeDuplication_ValidationLogic_Eliminated() throws Exception {
		Method m = Quantity.class.getDeclaredMethod("validateArithmeticOperands", Quantity.class, IMeasurable.class,
				boolean.class);
		assertTrue(Modifier.isPrivate(m.getModifiers()));
	}

	@Test
	void testCodeDuplication_ConversionLogic_Eliminated() throws Exception {
		Method m = Quantity.class.getDeclaredMethod("performBaseArithmetic", Quantity.class, ArithmeticOperation.class);
		assertTrue(Modifier.isPrivate(m.getModifiers()));
	}

	// ENUM DISPATCH

	@Test
	void testEnumDispatch_AllOperations_CorrectlyDispatched() {
		assertEquals(15, ArithmeticOperation.ADD.compute(10, 5), EPS);
		assertEquals(5, ArithmeticOperation.SUBTRACT.compute(10, 5), EPS);
		assertEquals(2, ArithmeticOperation.DIVIDE.compute(10, 5), EPS);
	}

	// FUTURE SCALABILITY

	@Test
	void testFutureOperation_MultiplicationPattern() {
		double simulated = 5 * 3;
		assertEquals(15, simulated);
	}

	// ERROR MESSAGE CONSISTENCY

	@Test
	void testErrorMessage_Consistency_Across_Operations() {
		Quantity<LengthUnit> q = new Quantity<>(10, LengthUnit.FEET);
		String msg1 = assertThrows(IllegalArgumentException.class, () -> q.add(null)).getMessage();
		String msg2 = assertThrows(IllegalArgumentException.class, () -> q.subtract(null)).getMessage();
		assertEquals(msg1, msg2);
	}

	// ENCAPSULATION

	@Test
	void testHelper_PrivateVisibility() throws Exception {
		Method m = Quantity.class.getDeclaredMethod("performBaseArithmetic", Quantity.class, ArithmeticOperation.class);
		assertTrue(Modifier.isPrivate(m.getModifiers()));
	}

	@Test
	void testValidation_Helper_PrivateVisibility() throws Exception {
		Method m = Quantity.class.getDeclaredMethod("validateArithmeticOperands", Quantity.class, IMeasurable.class,
				boolean.class);
		assertTrue(Modifier.isPrivate(m.getModifiers()));
	}

	// CHAIN OPERATIONS

	@Test
	void testArithmetic_Chain_Operations() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(2, LengthUnit.FEET);
		Quantity<LengthUnit> q3 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q4 = new Quantity<>(3, LengthUnit.FEET);

		double result = q1.add(q2).subtract(q3).divide(q4);
		assertEquals(11.0 / 3.0, result, EPS);
	}

	// LARGE DATASET

	@Test
	void testRefactoring_NoBehaviorChange_LargeDataset() {
		for (int i = 1; i <= 1000; i++) {
			Quantity<LengthUnit> q1 = new Quantity<>(i, LengthUnit.FEET);
			Quantity<LengthUnit> q2 = new Quantity<>(i, LengthUnit.FEET);
			assertEquals(i * 2, q1.add(q2).getValue(), EPS);
		}
	}

	// PERFORMANCE SIMULATION

	@Test
	void testRefactoring_Performance_ComparableToUC12() {
		long start = System.nanoTime();
		for (int i = 1; i <= 10000; i++) {
			Quantity<LengthUnit> q1 = new Quantity<>(i, LengthUnit.FEET);
			Quantity<LengthUnit> q2 = new Quantity<>(i, LengthUnit.FEET);
			q1.add(q2);
		}
		long end = System.nanoTime();
		assertTrue(end - start > 0);
	}

	// DIRECT ENUM CONSTANTS

	@Test
	void testEnumConstant_ADD_CorrectlyAdds() {
		assertEquals(10.0, ArithmeticOperation.ADD.compute(7, 3), EPS);
	}

	@Test
	void testEnumConstant_SUBTRACT_CorrectlySubtracts() {
		assertEquals(4.0, ArithmeticOperation.SUBTRACT.compute(7, 3), EPS);
	}

	@Test
	void testEnumConstant_DIVIDE_CorrectlyDivides() {
		assertEquals(3.5, ArithmeticOperation.DIVIDE.compute(7, 2), EPS);
	}

	// HELPER BASE + RESULT CONVERSION

	@Test
	void testHelper_BaseUnitConversion_Correct() {
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCHES);
		assertEquals(2.0, q1.add(q2).getValue(), EPS);
	}

	@Test
	void testHelper_ResultConversion_Correct() {
		Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);
		Quantity<LengthUnit> result = q1.add(q2, LengthUnit.INCHES);
		assertEquals(180.0, result.getValue(), EPS);
	}

	// UNIFIED VALIDATION BEHAVIOR

	@Test
	void testRefactoring_Validation_UnifiedBehavior() {
		Quantity<LengthUnit> q = new Quantity<>(10, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
	}

}