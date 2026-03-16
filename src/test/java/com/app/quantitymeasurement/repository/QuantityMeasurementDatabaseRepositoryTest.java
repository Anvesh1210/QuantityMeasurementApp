package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.entity.QuantityModel;
import com.app.quantitymeasurement.units.IMeasurable;
import com.app.quantitymeasurement.units.LengthUnit;

public class QuantityMeasurementDatabaseRepositoryTest {
	private QuantityMeasurementDatabaseRepository repository;

	@BeforeEach
	void setup() {
		repository = QuantityMeasurementDatabaseRepository.getInstance();
		repository.deleteAll();
	}

	@Test
	void testDatabaseRepository_SaveEntity() {
		QuantityModel<IMeasurable> q1 = new QuantityModel<>(1, LengthUnit.FEET);
		QuantityModel<IMeasurable> q2 = new QuantityModel<>(12, LengthUnit.INCHES);
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "COMPARE", true);
		repository.save(entity);
		assertEquals(1, repository.getTotalCount());
	}

	@Test
	void testDatabaseRepository_DeleteAll() {
		repository.deleteAll();
		assertEquals(0, repository.getTotalCount());
	}

}