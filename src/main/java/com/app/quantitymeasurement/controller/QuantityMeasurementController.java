package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

	private IQuantityMeasurementService quantityMeasurementService;

	// Constructor using Dependency Injection
	public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {

		if (quantityMeasurementService == null) {
			throw new IllegalArgumentException("Service cannot be null");
		}

		this.quantityMeasurementService = quantityMeasurementService;
	}

	// Compare two quantities

	public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
	}

	// Convert quantity

	public QuantityDTO performConversion(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.convert(thisQuantityDTO, thatQuantityDTO);
	}

	// Addition without target unit
	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO);
	}

	// Addition with target unit
	public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
	}

	// Subtraction without target unit

	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.subtarct(thisQuantityDTO, thatQuantityDTO);
	}
	// Subtraction with target unit

	public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		return quantityMeasurementService.subtarct(thisQuantityDTO, thatQuantityDTO, targetUnitDTO);
	}

	// Division
	public QuantityDTO performDivision(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return quantityMeasurementService.division(thisQuantityDTO, thatQuantityDTO);
	}
}