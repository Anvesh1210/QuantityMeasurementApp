package com.app.quantitymeasurement.model.unit;

import com.app.quantitymeasurement.model.IMeasurableUnit;

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
