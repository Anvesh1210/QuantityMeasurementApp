package com.app.quantitymeasurement.model.unit;

import com.app.quantitymeasurement.model.IMeasurableUnit;

public enum TemperatureUnitDTO implements IMeasurableUnit {
	CELSIUS, FAHRENHEIT, KELVIN;

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}
}
