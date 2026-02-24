package com.apps.quantitymeasurement;

import java.util.Objects;

//class defining weight
public class Weight {

	private final double value;
	private final WeightUnit unit;

	private static final double EPSILON = 1e-5;

	// Constructor
	public Weight(double value, WeightUnit unit) {

		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null.");

		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");

		this.value = value;
		this.unit = unit;
	}

	// Convert this weight to base unit (kilogram)
	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	// EQUALITY

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Weight other = (Weight) obj;

		double thisBase = unit.convertToBaseUnit(value);
		double otherBase = other.unit.convertToBaseUnit(other.value);

		return Math.abs(thisBase - otherBase) < EPSILON;
	}

	@Override
	public int hashCode() {
		return Objects.hash(toBaseUnit());
	}

	// CONVERSION

	public Weight convertTo(WeightUnit targetUnit) {

		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null.");

		double baseValue = this.toBaseUnit();
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new Weight(convertedValue, targetUnit);
	}

	// ADDITION (Implicit Target)

	public Weight add(Weight other) {

		if (other == null)
			throw new IllegalArgumentException("Other weight cannot be null.");

		double sumBase = this.toBaseUnit() + other.toBaseUnit();
		double resultValue = unit.convertFromBaseUnit(sumBase);

		return new Weight(resultValue, unit);
	}

	// ADDITION (Explicit Target)

	public static Weight add(Weight w1, Weight w2, WeightUnit targetUnit) {

		if (w1 == null || w2 == null)
			throw new IllegalArgumentException("Operands cannot be null.");

		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null.");

		double sumBase = w1.toBaseUnit() + w2.toBaseUnit();
		double resultValue = targetUnit.convertFromBaseUnit(sumBase);

		return new Weight(resultValue, targetUnit);
	}

	// GETTERS

	public double getValue() {
		return value;
	}

	public WeightUnit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}
}
