package com.apps.quantitymeasurement.dto.unit;

import com.apps.quantitymeasurement.dto.IMeasurableUnit;

public enum WeightUnitDTO implements IMeasurableUnit {
	KILOGRAM, GRAM, POUND;

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}
}
