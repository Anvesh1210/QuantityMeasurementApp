package com.apps.quantitymeasurement;

//class representing a feet value
public class Feet {
	private final double value;

	// constructor to initialize feet
	public Feet(double value) {
		this.value = value;
	}

	// equals method to compare two feet objects
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this == null || obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Feet ft = (Feet) obj;
		return Double.compare(this.value, ft.value) == 0;
	}

	// hashCode method to generate hashcode
	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}
}
 