package com.app.quantitymeasurement.integrationTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementIntegrationTest {

	@Test
	void testIntegration_EndToEnd_LengthAddition() {
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(
				QuantityMeasurementDatabaseRepository.getInstance());
		QuantityMeasurementController controller = new QuantityMeasurementController(service);
		QuantityDTO q1 = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO q2 = new QuantityDTO(12, "INCHES", "LENGTH");
		QuantityDTO result = controller.performAddition(q1, q2);
		assertNotNull(result);
	}
}