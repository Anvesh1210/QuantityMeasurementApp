package com.app.quantitymeasurement.units;

public enum WeightUnit implements IMeasurable {
	KILOGRAM(1.0), // Base unit
	GRAM(0.001), MILLIGRAM(0.000001), POUND(0.453592), TONNE(1000.0);

	private final double conversionFactor;

	WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	// Convert value in this unit to kilograms (base unit)
	@Override
	public double convertToBaseUnit(double value) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite.");
		}
		return value * conversionFactor;
	}

	// Convert value from kilograms (base unit) to this unit
	@Override
	public double convertFromBaseUnit(double baseValue) {
		if (!Double.isFinite(baseValue)) {
			throw new IllegalArgumentException("Value must be finite.");
		}
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
