package com.apps.quantitymeasurement;

//main class
public class QuantityMeasurementApp {
	public static boolean compareFeet(double value1, double value2) {
		Feet f1 = new Feet(value1);
		Feet f2 = new Feet(value2);
		return f1.equals(f2);
	}

	public static boolean compareInches(double value1, double value2) {
		Inches i1 = new Inches(value1);
		Inches i2 = new Inches(value2);
		return i1.equals(i2);
	}

	public static void main(String[] args) {
		
		System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Equal (" + compareInches(1.0, 1.0) + ")");

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Equal (" + compareFeet(1.0, 1.0) + ")");
	}
}
