package com.app.quantitymeasurement.entity.unit;

import com.app.quantitymeasurement.entity.IMeasurableUnit;

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
