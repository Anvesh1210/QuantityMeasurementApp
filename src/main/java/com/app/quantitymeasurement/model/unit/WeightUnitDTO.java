package com.app.quantitymeasurement.model.unit;

import com.app.quantitymeasurement.model.IMeasurableUnit;

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
