package com.apps.quantitymeasurement.units;

import com.apps.quantitymeasurement.core.IMeasurable;

public enum VolumeUnit implements IMeasurable {
	LITRE(1.0) // base unit
	, MILLILITRE(0.001), GALLON(3.78541);

	private final double conversionFactor;

	VolumeUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	// Converts value in this unit to base unit
	public double convertToBaseUnit(double value) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");
		return value * conversionFactor;
	}

	// Converts value from base unit to this unit
	public double convertFromBaseUnit(double baseValue) {
		if (!Double.isFinite(baseValue))
			throw new IllegalArgumentException("Value must be finite.");
		return baseValue / conversionFactor;
	}

	@Override
	public String getUnitName() {
		return name();
	}

	@Override
	public String getMeasurementType() {
		return "VOLUME";
	}

	@Override
	public IMeasurable getUnitInstance(String unitName) {
		return VolumeUnit.valueOf(unitName);
	}
}
