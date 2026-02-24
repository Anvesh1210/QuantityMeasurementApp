package com.apps.quantitymeasurement;

//main class
public class QuantityMeasurementApp {

	// method to Demonstrate Length Equality
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	// Method to Demonstrate Length conversion
	public static double demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {
		return Length.convert(value, fromUnit, toUnit);
	}

	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}

	public static void main(String[] args) {

		//defining two length
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);

		printResult("FEET", Length.add(l1, l2, LengthUnit.FEET));
		printResult("INCHES", Length.add(l1, l2, LengthUnit.INCHES));
		printResult("YARDS", Length.add(l1, l2, LengthUnit.YARDS));
		printResult("CENTIMETERS", Length.add(l1, l2, LengthUnit.CENTIMETERS));
	}

	//method to print result
	private static void printResult(String label, Length result) {
		System.out.println(label + " = " + result);
	}
}
