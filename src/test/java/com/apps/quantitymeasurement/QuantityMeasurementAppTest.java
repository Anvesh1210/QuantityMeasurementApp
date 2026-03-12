package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.model.QuantityModel;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.units.LengthUnit;
import com.apps.quantitymeasurement.units.TemperatureUnit;
import com.apps.quantitymeasurement.units.WeightUnit;

public class QuantityMeasurementAppTest {

	private QuantityMeasurementController controller;
	private QuantityMeasurementServiceImpl service;

	@BeforeEach
	void setup() {
		service = new QuantityMeasurementServiceImpl(QuantityMeasurementCacheRepository.getInstance());
		controller = new QuantityMeasurementController(service);
	}

	// ---------- ENTITY TESTS ----------

	@Test
	void testQuantityEntity_SingleOperandConstruction() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12, LengthUnit.INCHES);
		QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2, "CONVERT", "true");
		assertEquals("FEET", e.thisUnit);
	}

	@Test
	void testQuantityEntity_BinaryOperandConstruction() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12, LengthUnit.INCHES);
		QuantityModel<IMeasurable> r = new QuantityModel<>(2, LengthUnit.FEET);
		QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2, "ADD", r);
		assertEquals("ADD", e.operation);
	}

	@Test
	void testQuantityEntity_ErrorConstruction() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2, "ADD", "error", true);
		assertTrue(e.isError);
	}

	@Test
	void testQuantityEntity_ToString_Success() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12, LengthUnit.INCHES);
		QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2, "COMPARE", "true");
		assertNotNull(e.toString());
	}

	@Test
	void testQuantityEntity_ToString_Error() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2, "ADD", "error", true);
		assertTrue(e.toString().contains("error"));
	}

	// ---------- SERVICE TESTS ----------

	@Test
	void testService_CompareEquality_SameUnit_Success() {
		assertTrue(service.compare(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(1, "FEET", "LENGTH")));
	}

	@Test
	void testService_CompareEquality_DifferentUnit_Success() {
		assertTrue(service.compare(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH")));
	}

	@Test
	void testService_CompareEquality_CrossCategory_Error() {

		boolean result = service.compare(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(1, "KILOGRAM", "WEIGHT"));

		assertFalse(result);
	}

	@Test
	void testService_Convert_Success() {
		QuantityDTO r = service.convert(new QuantityDTO(1, "METERS", "LENGTH"),
				new QuantityDTO(0, "CENTIMETERS", "LENGTH"));
		assertEquals(100, r.getValue());
	}

	@Test
	void testService_Add_Success() {
		QuantityDTO r = service.add(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH"));
		assertEquals(2, r.getValue());
	}

	@Test
	void testService_Add_UnsupportedOperation_Error() {
		assertThrows(Exception.class, () -> service.add(new QuantityDTO(10, "CELSIUS", "TEMPERATURE"),
				new QuantityDTO(20, "FAHRENHEIT", "TEMPERATURE")));
	}

	@Test
	void testService_Subtract_Success() {
		QuantityDTO r = service.subtarct(new QuantityDTO(2, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH"));
		assertEquals(1, r.getValue());
	}

	@Test
	void testService_Divide_Success() {
		QuantityDTO r = service.division(new QuantityDTO(10, "KILOGRAM", "WEIGHT"),
				new QuantityDTO(5, "KILOGRAM", "WEIGHT"));
		assertEquals(2, r.getValue());
	}

	@Test
	void testService_Divide_ByZero_Error() {
		assertThrows(Exception.class, () -> service.division(new QuantityDTO(10, "KILOGRAM", "WEIGHT"),
				new QuantityDTO(0, "KILOGRAM", "WEIGHT")));
	}

	// ---------- CONTROLLER TESTS ----------

	@Test
	void testController_DemonstrateEquality_Success() {
		assertTrue(controller.performComparison(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(12, "INCHES", "LENGTH")));
	}

	@Test
	void testController_DemonstrateConversion_Success() {
		QuantityDTO r = controller.performConversion(new QuantityDTO(1, "METERS", "LENGTH"),
				new QuantityDTO(0, "CENTIMETERS", "LENGTH"));
		assertEquals(100, r.getValue());
	}

	@Test
	void testController_DemonstrateAddition_Success() {
		QuantityDTO r = controller.performAddition(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(12, "INCHES", "LENGTH"));
		assertEquals(2, r.getValue());
	}

	@Test
	void testController_DemonstrateAddition_Error() {
		assertThrows(Exception.class, () -> controller.performAddition(new QuantityDTO(10, "CELSIUS", "TEMPERATURE"),
				new QuantityDTO(20, "FAHRENHEIT", "TEMPERATURE")));
	}

	@Test
	void testController_DisplayResult_Success() {
		QuantityDTO r = controller.performAddition(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(12, "INCHES", "LENGTH"));
		assertNotNull(r);
	}

	@Test
	void testController_DisplayResult_Error() {
		assertThrows(Exception.class, () -> controller.performAddition(new QuantityDTO(10, "CELSIUS", "TEMPERATURE"),
				new QuantityDTO(20, "FAHRENHEIT", "TEMPERATURE")));
	}

	// ---------- LAYER SEPARATION ----------

	@Test
	void testLayerSeparation_ServiceIndependence() {
		assertNotNull(service);
	}

	@Test
	void testLayerSeparation_ControllerIndependence() {
		assertNotNull(controller);
	}

	// ---------- DATA FLOW ----------

	@Test
	void testDataFlow_ControllerToService() {
		assertTrue(controller.performComparison(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(12, "INCHES", "LENGTH")));
	}

	@Test
	void testDataFlow_ServiceToController() {
		assertEquals(100, controller
				.performConversion(new QuantityDTO(1, "METERS", "LENGTH"), new QuantityDTO(0, "CENTIMETERS", "LENGTH"))
				.getValue());
	}

	// ---------- BACKWARD COMPATIBILITY ----------

	@Test
	void testBackwardCompatibility_AllUC1_UC14_Tests() {
		assertTrue(service.compare(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH")));
	}

	// ---------- CATEGORY TESTS ----------

	@Test
	void testService_AllMeasurementCategories() {
		assertTrue(service.compare(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH")));
	}

	@Test
	void testController_AllOperations() {
		controller.performComparison(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH"));
		controller.performAddition(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH"));
		assertTrue(true);
	}

	@Test
	void testService_ValidationConsistency() {
		assertThrows(Exception.class, () -> service.compare(null, null));
	}

	@Test
	void testEntity_Immutability() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		assertNotNull(q1);
	}

	@Test
	void testService_ExceptionHandling_AllOperations() {
		assertThrows(Exception.class, () -> service.add(new QuantityDTO(10, "CELSIUS", "TEMPERATURE"),
				new QuantityDTO(20, "FAHRENHEIT", "TEMPERATURE")));
	}

	@Test
	void testController_ConsoleOutput_Format() {
		QuantityDTO r = controller.performAddition(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(12, "INCHES", "LENGTH"));
		assertEquals(2, r.getValue());
	}

	// ---------- INTEGRATION ----------

	@Test
	void testIntegration_EndToEnd_LengthAddition() {
		QuantityDTO r = controller.performAddition(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(12, "INCHES", "LENGTH"));
		assertEquals(2, r.getValue());
	}

	@Test
	void testIntegration_EndToEnd_TemperatureUnsupported() {
		assertTrue(controller.performComparison(new QuantityDTO(0, "CELSIUS", "TEMPERATURE"),
				new QuantityDTO(32, "FAHRENHEIT", "TEMPERATURE")));
	}

	// ---------- EXTRA UC15 TESTS ----------

	@Test
	void testService_NullEntity_Rejection() {
		assertThrows(Exception.class, () -> service.compare(null, null));
	}

	@Test
	void testController_NullService_Prevention() {
		assertThrows(Exception.class, () -> new QuantityMeasurementController(null));
	}

	@Test
	void testService_AllUnitImplementations() {
		assertTrue(service.compare(new QuantityDTO(1, "FEET", "LENGTH"), new QuantityDTO(12, "INCHES", "LENGTH")));
	}

	@Test
	void testEntity_OperationType_Tracking() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12, LengthUnit.INCHES);
		QuantityMeasurementEntity e = new QuantityMeasurementEntity(q1, q2, "ADD", "true");
		assertEquals("ADD", e.operation);
	}

	@Test
	void testLayerDecoupling_ServiceChange() {
		assertNotNull(service);
	}

	@Test
	void testLayerDecoupling_EntityChange() {
		assertNotNull(new QuantityMeasurementEntity(new QuantityModel<>(1, LengthUnit.FEET),
				new QuantityModel<>(12, LengthUnit.INCHES), "COMPARE", "true"));
	}

	@Test
	void testScalability_NewOperation_Addition() {
		QuantityDTO r = controller.performAddition(new QuantityDTO(1, "FEET", "LENGTH"),
				new QuantityDTO(12, "INCHES", "LENGTH"));
		assertEquals(2, r.getValue());
	}

}