package com.apps.quantitymeasurement.dto.unit;

import com.apps.quantitymeasurement.dto.IMeasurableUnit;

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
