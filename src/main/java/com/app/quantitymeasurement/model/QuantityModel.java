package com.app.quantitymeasurement.model;

import com.app.quantitymeasurement.units.IMeasurable;

public class QuantityModel<U extends IMeasurable> {
	public double value;
	public U unit;

	public QuantityModel(double value, U unit) {
		this.value = value;
		this.unit = unit;
	}
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public U getUnit() {
		return unit;
	}

	public void setUnit(U unit) {
		this.unit = unit;
	}

}
