package com.apps.quantitymeasurement;

import java.util.Scanner;

//main class
public class QuantityMeasurementApp {
	public static void main(String[] args) {
		//creating Scanner object
		Scanner kb = new Scanner(System.in);
		
		//taking two feet values
		System.out.println("Enter two feet values");
		Feet feet = new Feet(kb.nextDouble());
		Feet feet2 = new Feet(kb.nextDouble());
		
		//comparing both values
		System.out.println("Both Feet equal? "+feet.equals(feet2));
		
		//closing the Scanner
		kb.close();
	}
}
