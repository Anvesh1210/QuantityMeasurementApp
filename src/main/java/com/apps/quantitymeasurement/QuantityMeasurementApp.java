package com.apps.quantitymeasurement;

//main class
public class QuantityMeasurementApp {
	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	public static void main(String[] args) {

		Length length1 = new Length(1.0, LengthUnit.FEET);
		Length length2 = new Length(12.0, LengthUnit.INCHES);

		System.out.println("Input: " + length1 + " and " + length2);
		System.out.println("Equal (" + demonstrateLengthEquality(length1, length2) + ")");

		Length length3 = new Length(1.0, LengthUnit.INCHES);
		Length length4 = new Length(1.0, LengthUnit.INCHES);

		System.out.println("Input: " + length3 + " and " + length4);
		System.out.println("Equal (" + demonstrateLengthEquality(length3, length4) + ")");
		
		Length length5 = new Length(100.0, LengthUnit.CENTIMETERS);
		Length length6 = new Length(39.3701, LengthUnit.INCHES);

		System.out.println("Input: " + length5 + " and " + length6);
		System.out.println("Equal (" + demonstrateLengthEquality(length5, length6) + ")");
		
	}
}
