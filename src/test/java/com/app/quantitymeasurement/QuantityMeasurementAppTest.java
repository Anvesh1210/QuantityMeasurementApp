package com.app.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {

	@Test
	void testPackageStructure_AllLayersPresent() {
		assertNotNull(com.app.quantitymeasurement.controller.QuantityMeasurementController.class);
		assertNotNull(com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl.class);
		assertNotNull(com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository.class);
	}

	@Test
	void testApplication_MainRunsSuccessfully() {
		assertDoesNotThrow(() -> QuantityMeasurementApp.main(new String[] {}));
	}

}