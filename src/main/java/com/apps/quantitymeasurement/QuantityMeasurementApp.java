package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

	public static void main(String[] args) {

		// Create Repository
		IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();

		// Create Service
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);

		// Create Controller
		QuantityMeasurementController controller = new QuantityMeasurementController(service);

		// Comparison
		QuantityDTO feet = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO inches = new QuantityDTO(12, "INCHES", "LENGTH");
		boolean comparison = controller.performComparison(feet, inches);
		System.out.println("1 FOOT == 12 INCH : " + comparison);

		// Conversion
		QuantityDTO meter = new QuantityDTO(1, "METERS", "LENGTH");
		QuantityDTO centimeter = new QuantityDTO(0, "CENTIMETERS", "LENGTH");
		QuantityDTO converted = controller.performConversion(meter, centimeter);
		System.out.println("1 METER = " + converted.getValue() + " " + converted.getUnit());

		// Addition
		QuantityDTO foot = new QuantityDTO(1, "FEET", "LENGTH");
		QuantityDTO inch = new QuantityDTO(12, "INCHES", "LENGTH");
		QuantityDTO addition = controller.performAddition(foot, inch);
		System.out.println("Addition Result : " + addition.getValue() + " " + addition.getUnit());

		// Division
		QuantityDTO weight1 = new QuantityDTO(10, "KILOGRAM", "WEIGHT");
		QuantityDTO weight2 = new QuantityDTO(5, "KILOGRAM", "WEIGHT");
		QuantityDTO division = controller.performDivision(weight1, weight2);
		System.out.println("Division Result : " + division.getValue());

		// Temperature comparison
		QuantityDTO celsius = new QuantityDTO(0, "CELSIUS", "TEMPERATURE");
		QuantityDTO fahrenheit = new QuantityDTO(32, "FAHRENHEIT", "TEMPERATURE");
		boolean tempComparison = controller.performComparison(celsius, fahrenheit);
		System.out.println("0C == 32F : " + tempComparison);
	}
}