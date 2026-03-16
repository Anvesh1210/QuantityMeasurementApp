package com.app.quantitymeasurement.units;

import com.app.quantitymeasurement.core.SupportsArithmetic;

public interface IMeasurable {
	double getConversionFactor();

	// Convert value in this unit to base unit
	double convertToBaseUnit(double value);

	// Convert value from base unit to this unit
	double convertFromBaseUnit(double baseValue);

	String getUnitName();

	// By default, all units support arithmetic
	SupportsArithmetic supportsArithmetic = () -> true;

	default boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	// Default: allow arithmetic
	default void validateOperationSupport(String operation) {
		// Do nothing (all units except temperature allow arithmetic)
	}
	
	//method to get the measurementType for this Unit
	public String getMeasurementType();
	
	//return the IMeasurable unit Instance for the given unit
	public IMeasurable getUnitInstance(String unitName);
}
