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

	// ---------------- WEIGHT METHODS ----------------

	public static boolean demonstrateWeightEquality(Weight w1, Weight w2) {
		return w1.equals(w2);
	}

	public static Weight demonstrateWeightConversion(Weight weight, WeightUnit targetUnit) {
		return weight.convertTo(targetUnit);
	}

	public static Weight demonstrateWeightAddition(Weight w1, Weight w2) {
		return w1.add(w2);
	}

	public static Weight demonstrateWeightAddition(Weight w1, Weight w2, WeightUnit targetUnit) {
		return Weight.add(w1, w2, targetUnit);
	}

	public static void main(String[] args) {

		// defining two length
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);

		printResult("FEET", Length.add(l1, l2, LengthUnit.FEET));
		printResult("INCHES", Length.add(l1, l2, LengthUnit.INCHES));
		printResult("YARDS", Length.add(l1, l2, LengthUnit.YARDS));
		printResult("CENTIMETERS", Length.add(l1, l2, LengthUnit.CENTIMETERS));

		// ---------------- WEIGHT DEMO ----------------

		System.out.println("\nWeight Equality:");

		Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
		Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

		System.out.println("1 KG equals 1000 G ? " + demonstrateWeightEquality(w1, w2));

		System.out.println("\nWeight Conversion:");
		Weight pounds = new Weight(2.0, WeightUnit.POUND);
		System.out.println("2 LB in KG = " + demonstrateWeightConversion(pounds, WeightUnit.KILOGRAM));

		System.out.println("\nWeight Addition (Implicit Target)");
		System.out.println("1 KG + 1000 G = " + demonstrateWeightAddition(w1, w2));

		System.out.println("\nWeight Addition (Explicit Target - POUND)");
		System.out.println("1 KG + 1000 G in LB = " + demonstrateWeightAddition(w1, w2, WeightUnit.POUND));
	}

	// method to print result
	private static void printResult(String label, Length result) {
		System.out.println(label + " = " + result);
	}

}
