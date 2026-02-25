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

	// ARITHMATIC OPERATIONS
	
	//SUBTRACTION
	public Quantity<U> subtract(Quantity<U> other) {
		validateOperand(other);
		return subtract(other, this.unit);
	}

	//SUBTRACTION BY TARGETED UNIT
	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateOperand(other);

		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double baseThis = unit.convertToBaseUnit(this.value);
		double baseOther = other.unit.convertToBaseUnit(other.value);

		double baseResult = baseThis - baseOther;

		double converted = targetUnit.convertFromBaseUnit(baseResult);

		return new Quantity<>(roundToTwoDecimal(converted), targetUnit);
	}

	//DIVIDE
	public double divide(Quantity<U> other) {
		validateOperand(other);

		double baseThis = unit.convertToBaseUnit(this.value);
		double baseOther = other.unit.convertToBaseUnit(other.value);

		if (Math.abs(baseOther) < EPSILON) {
			throw new ArithmeticException("Cannot divide by zero quantity");
		}

		return baseThis / baseOther;
	}

	//METHOD TO VALIDATE OPERAND
	private void validateOperand(Quantity<U> other) {
		if (other == null) {
			throw new IllegalArgumentException("Quantity cannot be null");
		}

		if (this.unit.getClass() != other.unit.getClass()) {
			throw new IllegalArgumentException("Cross-category arithmetic not allowed");
		}

		if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
			throw new IllegalArgumentException("Values must be finite numbers");
		}
	}

	//ROUND TO DECIMAL
	private double roundToTwoDecimal(double value) {
		return Math.round(value * 100.0) / 100.0;
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
