package com.apps.quantitymeasurement;

public enum LengthUnit implements IMeasurable {
	FEET(1.0), // base unit
	INCHES(1.0 / 12.0), YARDS(3.0), CENTIMETERS(1.0 / 30.48);

	private final double conversionFactorToFeet;

	LengthUnit(double conversionFactorToFeet) {
		this.conversionFactorToFeet = conversionFactorToFeet;
	}

	// Converts value in this unit to base unit
	public double convertToBaseUnit(double value) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");
		return value * conversionFactorToFeet;
	}

	// Converts value from base unit to this unit
	public double convertFromBaseUnit(double baseValue) {
		if (!Double.isFinite(baseValue))
			throw new IllegalArgumentException("Value must be finite.");
		return baseValue / conversionFactorToFeet;
	}

	public double getConversionFactor() {
		return conversionFactorToFeet;
	}

	@Override
	public String getUnitName() {
		return name();
	}
}
