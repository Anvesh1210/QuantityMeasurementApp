package com.apps.quantitymeasurement;

//class representing a feet value
public class Inches {
	private final double value;

	//constructor to initialize feet
	public Inches(double value) {
		this.value = value;
	}

	//equals method to compare two feet objects
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || getClass() != obj.getClass())
			return false;

		Inches other = (Inches) obj;
		return Double.compare(this.value, other.value) == 0;
	}

	// hashCode method to generate hashcode
	@Override
	public int hashCode() {
		return Double.hashCode(value);
	}
}
