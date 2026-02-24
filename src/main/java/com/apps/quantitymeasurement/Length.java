package com.apps.quantitymeasurement;

import java.util.Objects;

//class representing Length
public class Length {

	// Length value and length unit
	private final double value;
	private final LengthUnit unit;

	private static final double EPSILON = 1e-6;

	// Constructor
	public Length(double value, LengthUnit unit) {
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite.");
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null.");
		this.value = value;
		this.unit = unit;
	}

	// Convert this length to base unit (feet)
	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	// CONVERSION

	public Length convertTo(LengthUnit targetUnit) {

		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null.");

		double baseValue = this.toBaseUnit();
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

		return new Length(convertedValue, targetUnit);
	}

	public static double convert(double value, LengthUnit source, LengthUnit target) {

		if (source == null || target == null)
			throw new IllegalArgumentException("Units cannot be null.");

		double base = source.convertToBaseUnit(value);
		return target.convertFromBaseUnit(base);
	}

	// UC6 ADDITION OF TWO LENGTHS
	public Length add(Length other) {

		if (other == null)
			throw new IllegalArgumentException("Other length cannot be null.");

		// Add in base unit
		double sumBase = this.toBaseUnit() + other.toBaseUnit();
		// Convert back to this unit
		double resultValue = this.unit.convertFromBaseUnit(sumBase);
		return new Length(resultValue, this.unit);
	}

	public static Length add(Length l1, Length l2) {
		if (l1 == null || l2 == null)
			throw new IllegalArgumentException("Operands cannot be null.");

		return l1.add(l2);
	}

	// UC7 ADDITION WITH EXPLICIT TARGET

	public static Length add(Length l1, Length l2, LengthUnit targetUnit) {
		if (l1 == null || l2 == null)
			throw new IllegalArgumentException("Operands cannot be null.");
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null.");
		double sumBase = l1.toBaseUnit() + l2.toBaseUnit();
		double resultValue = targetUnit.convertFromBaseUnit(sumBase);
		return new Length(resultValue, targetUnit);
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

	// hashCode override
	@Override
	public int hashCode() {
		return Objects.hash(Math.round(toBaseUnit() / EPSILON));
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