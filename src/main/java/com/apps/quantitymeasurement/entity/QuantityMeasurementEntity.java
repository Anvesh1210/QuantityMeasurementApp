package com.apps.quantitymeasurement.entity;

import java.io.Serializable;

import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.model.QuantityModel;

public class QuantityMeasurementEntity implements Serializable {
	public static final long serialVersionUID = 1;
	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;
	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;

	public String operation;
	public double resultValue;
	public String resultUnit;
	public String resultMeasurementType;

	public String resultString;

	public boolean isError;

	public String errorMessage;

	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation) {
		this.thisValue = thisQuantity.getValue();
		this.thisUnit = thisQuantity.getUnit().getUnitName();
		this.thisMeasurementType = thisQuantity.getUnit().getMeasurementType();

		this.thatValue = thatQuantity.getValue();
		this.thatUnit = thatQuantity.getUnit().getUnitName();
		this.thatMeasurementType = thatQuantity.getUnit().getMeasurementType();

		this.operation = operation;
	}

	// Single Operand COmparison and COnversion
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation, String result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultString = result;
	}

	// double operand Arithmatic Operation
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation, QuantityModel<IMeasurable> result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType = result.getUnit().getMeasurementType();
	}

	// record error while performing operation
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation, String errorMessage, boolean isError) {
		this(thisQuantity, thatQuantity, operation);
		this.errorMessage = errorMessage;
		this.isError = isError;
	}

	// for comparison
	public QuantityMeasurementEntity(QuantityModel<IMeasurable> thisQuantity, QuantityModel<IMeasurable> thatQuantity,
			String operation, boolean result) {
		this(thisQuantity, thatQuantity, operation);
		this.resultString = String.valueOf(result);
	}

	@Override
	public String toString() {

		if (isError) {
			return "Operation: " + operation + ", Error: " + errorMessage;
		}

		if (resultString != null) {
			return "Operation: " + operation + ", Result: " + resultString;
		}

		return "Operation: " + operation + ", Result: " + resultValue + " " + resultUnit;
	}
}
