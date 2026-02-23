package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {

	private final double value;
	private final LengthUnit unit;

	private static final double EPSILON = 1e-6;

	// Constructor
	public Length(double value, LengthUnit unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");

		this.value = value;
		this.unit = unit;
	}

	// Convert to base unit (inches)
	private double convertToBaseUnit() {
		return this.value * this.unit.getConversionFactor();
	}

	// Compare method
	public boolean compare(Length other) {
		if (other == null)
			return false;

		return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
	}

	public Length add(Length other) {

		if (other == null)
			throw new IllegalArgumentException("Other length cannot be null.");

		// Convert both to base unit (inches)
		double thisBase = this.value * this.unit.getConversionFactor();
		double otherBase = other.value * other.unit.getConversionFactor();

		// Add in base unit
		double sumBase = thisBase + otherBase;

		// Convert back to this unit
		double resultValue = sumBase / this.unit.getConversionFactor();

		return new Length(resultValue, this.unit);
	}

	public static Length add(Length l1, Length l2) {
		if (l1 == null || l2 == null)
			throw new IllegalArgumentException("Operands cannot be null.");

		return l1.add(l2);
	}

	// equals() override
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		Length other = (Length) obj;

		double thisBase = this.value * this.unit.getConversionFactor();
		double otherBase = other.value * other.unit.getConversionFactor();

		return Math.abs(thisBase - otherBase) < EPSILON;
	}

	public Length convertTo(LengthUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null.");
		double convertedValue = convert(this.value, this.unit, targetUnit);
		return new Length(convertedValue, targetUnit);
	}

	public static double convert(double value, LengthUnit source, LengthUnit target) {

		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");

		if (source == null || target == null)
			throw new IllegalArgumentException("Units cannot be null.");
		double valueInBase = value * source.getConversionFactor();
		return valueInBase / target.getConversionFactor();
	}

	// hashCode override
	@Override
	public int hashCode() {
		return Objects.hash(convertToBaseUnit());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}
}