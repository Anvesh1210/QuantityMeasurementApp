package com.app.quantitymeasurement.model.unit;

import com.app.quantitymeasurement.model.IMeasurableUnit;

public enum LengthUnitDTO implements IMeasurableUnit{
	FEET, INCHES, YARDS, CENTIMETERS;

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}
	
}
