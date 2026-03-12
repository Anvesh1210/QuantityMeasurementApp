package com.apps.quantitymeasurement.units;

import com.apps.quantitymeasurement.core.IMeasurable;

public enum WeightUnit implements IMeasurable {
	KILOGRAM(1.0), // Base unit
	GRAM(0.001), POUND(0.453592);

	private final double conversionFactor;

	WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	public double getConversionFactor() {
		return conversionFactor;
	}

	// Convert value in this unit → kilograms (base unit)
	public double convertToBaseUnit(double value) {
		return value * conversionFactor;
	}

	// Convert value from kilograms → this unit
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactor;
	}

	@Override
	public String getUnitName() {
		return name();
	}
	
	@Override
	public String getMeasurementType() {
	    return "WEIGHT";
	}

	@Override
	public IMeasurable getUnitInstance(String unitName) {
	    return WeightUnit.valueOf(unitName);
	}
}
