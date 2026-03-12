package com.apps.quantitymeasurement.dto.unit;

import com.apps.quantitymeasurement.dto.IMeasurableUnit;

public enum VolumeUnitDTO implements IMeasurableUnit {
	LITRE, MILLILITRE, GALLON;

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}
}
