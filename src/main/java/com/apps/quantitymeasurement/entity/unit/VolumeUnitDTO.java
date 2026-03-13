package com.apps.quantitymeasurement.entity.unit;

import com.apps.quantitymeasurement.entity.IMeasurableUnit;

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
