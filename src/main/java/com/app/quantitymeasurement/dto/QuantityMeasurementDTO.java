package com.app.quantitymeasurement.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuantityMeasurementDTO {

	// INPUT
	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;

	public Double thatValue;
	public String thatUnit;
	public String thatMeasurementType;

	// OPERATION
	public OperationType operation;

	// RESULT
	public String resultString;
	public Double resultValue;
	public String resultUnit;
	public String resultMeasurementType;

	// ERROR
	@JsonProperty("error")
	public boolean error;
	public String errorMessage;

	// ENTITY -> DTO
	public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
		if (entity == null) {
			return null;
		}

		QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
		dto.thisValue = entity.getThisValue();
		dto.thisUnit = entity.getThisUnit();
		dto.thisMeasurementType = entity.getThisMeasurementType();

		dto.thatValue = entity.getThatValue();
		dto.thatUnit = entity.getThatUnit();
		dto.thatMeasurementType = entity.getThatMeasurementType();

		dto.operation = parseOperation(entity.getOperation());
		dto.resultString = entity.getResultString();
		dto.resultValue = entity.getResultValue();
		dto.resultUnit = entity.getResultUnit();
		dto.resultMeasurementType = entity.getResultMeasurementType();

		dto.error = entity.isError();
		dto.errorMessage = entity.getErrorMessage();
		return dto;
	}

	// DTO -> ENTITY
	public QuantityMeasurementEntity toEntity() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setThisValue(this.thisValue);
		entity.setThisUnit(this.thisUnit);
		entity.setThisMeasurementType(this.thisMeasurementType);

		entity.setThatValue(this.thatValue);
		entity.setThatUnit(this.thatUnit);
		entity.setThatMeasurementType(this.thatMeasurementType);

		entity.setOperation(this.operation == null ? null : this.operation.name());
		entity.setResultString(this.resultString);
		entity.setResultValue(this.resultValue);
		entity.setResultUnit(this.resultUnit);
		entity.setResultMeasurementType(this.resultMeasurementType);
		entity.setError(this.error);
		entity.setErrorMessage(this.errorMessage);

		return entity;
	}

	public static List<QuantityMeasurementDTO> fromList(List<QuantityMeasurementEntity> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}
		return entities.stream().map(QuantityMeasurementDTO::from).collect(Collectors.toList());
	}

	public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
		if (dtos == null) {
			return Collections.emptyList();
		}
		return dtos.stream().map(QuantityMeasurementDTO::toEntity).collect(Collectors.toList());
	}

	private static OperationType parseOperation(String rawOperation) {
		if (rawOperation == null || rawOperation.isBlank()) {
			return null;
		}

		try {
			return OperationType.valueOf(rawOperation.trim().toUpperCase());
		} catch (IllegalArgumentException ignored) {
			return null;
		}
	}

	public double getThisValue() {
		return thisValue;
	}

	public void setThisValue(double thisValue) {
		this.thisValue = thisValue;
	}

	public String getThisUnit() {
		return thisUnit;
	}

	public void setThisUnit(String thisUnit) {
		this.thisUnit = thisUnit;
	}

	public String getThisMeasurementType() {
		return thisMeasurementType;
	}

	public void setThisMeasurementType(String thisMeasurementType) {
		this.thisMeasurementType = thisMeasurementType;
	}

	public Double getThatValue() {
		return thatValue;
	}

	public void setThatValue(Double thatValue) {
		this.thatValue = thatValue;
	}

	public String getThatUnit() {
		return thatUnit;
	}

	public void setThatUnit(String thatUnit) {
		this.thatUnit = thatUnit;
	}

	public String getThatMeasurementType() {
		return thatMeasurementType;
	}

	public void setThatMeasurementType(String thatMeasurementType) {
		this.thatMeasurementType = thatMeasurementType;
	}

	public OperationType getOperation() {
		return operation;
	}

	public void setOperation(OperationType operation) {
		this.operation = operation;
	}

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public Double getResultValue() {
		return resultValue;
	}

	public void setResultValue(Double resultValue) {
		this.resultValue = resultValue;
	}

	public String getResultUnit() {
		return resultUnit;
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit;
	}

	public String getResultMeasurementType() {
		return resultMeasurementType;
	}

	public void setResultMeasurementType(String resultMeasurementType) {
		this.resultMeasurementType = resultMeasurementType;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
