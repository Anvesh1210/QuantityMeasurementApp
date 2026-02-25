package com.apps.quantitymeasurement;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {
	private final double value;
	private final U unit;

	private static final double EPSILON = 1e-5;

	// Constructor
	public Quantity(double value, U unit) {

		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null.");

		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");

		this.value = value;
		this.unit = unit;
	}

	// Convert to base unit
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

		Quantity<?> other = (Quantity<?>) obj;

		// Prevent cross-category comparison
		if (this.unit.getClass() != other.unit.getClass())
			return false;

		double thisBase = this.toBaseUnit();
		double otherBase = other.unit.convertToBaseUnit(other.value);

		return Math.abs(thisBase - otherBase) < EPSILON;
	}

	@Override
	public int hashCode() {
		return Objects.hash(toBaseUnit());
	}

	// CONVERSION

	public Quantity<U> convertTo(U targetUnit) {

		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null.");

		double baseValue = this.toBaseUnit();
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new Quantity<>(convertedValue, targetUnit);
	}

	// ADDITION

	// Implicit target (first operand unit)
	public Quantity<U> add(Quantity<U> other) {

		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null.");

		double sumBase = this.toBaseUnit() + other.toBaseUnit();
		double resultValue = unit.convertFromBaseUnit(sumBase);

		return new Quantity<>(resultValue, unit);
	}

	// Explicit target unit
	public Quantity<U> add(Quantity<U> other, U targetUnit) {

		if (other == null)
			throw new IllegalArgumentException("Other quantity cannot be null.");

		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null.");

		double sumBase = this.toBaseUnit() + other.toBaseUnit();
		double resultValue = targetUnit.convertFromBaseUnit(sumBase);

		return new Quantity<>(resultValue, targetUnit);
	}

	// GETTERS

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	}
}
