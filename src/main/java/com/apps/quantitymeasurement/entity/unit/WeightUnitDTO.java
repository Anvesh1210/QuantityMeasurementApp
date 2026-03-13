package com.apps.quantitymeasurement.entity.unit;

import com.apps.quantitymeasurement.entity.IMeasurableUnit;

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
