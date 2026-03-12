package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;

public interface IQuantityMeasurementService {
	// Comparison - Compare two quantities
	public boolean compare(QuantityDTO thiQuantityDTO, QuantityDTO thatQuantityDTO);

	// Conversion - Convert quantities from one unit to another
	public QuantityDTO convert(QuantityDTO thiQuantityDTO, QuantityDTO thatQuantityDTO);

	// Addition - Add two quantities
	public QuantityDTO add(QuantityDTO thiQuantityDTO, QuantityDTO thatQuantityDTO);

	public QuantityDTO add(QuantityDTO thiQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	// Subtraction - Subtract one quantity from another
	public QuantityDTO subtarct(QuantityDTO thiQuantityDTO, QuantityDTO thatQuantityDTO);

	public QuantityDTO subtarct(QuantityDTO thiQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);

	// Division - Divide quantities
	public QuantityDTO division(QuantityDTO thiQuantityDTO, QuantityDTO thatQuantityDTO);
}
