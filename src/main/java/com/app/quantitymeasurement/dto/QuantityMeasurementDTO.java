package com.app.quantitymeasurement.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
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

	// ENTITY → DTO
	public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {

		if (entity == null)
			return null;

		QuantityMeasurementDTO dto = new QuantityMeasurementDTO();

		dto.thisValue = entity.getThisValue();
		dto.thisUnit = entity.getThisUnit();
		dto.thisMeasurementType = entity.getThisMeasurementType();

		dto.thatValue = entity.getThatValue();
		dto.thatUnit = entity.getThatUnit();
		dto.thatMeasurementType = entity.getThatMeasurementType();

		// 🔥 STRING → ENUM
		dto.operation = OperationType.valueOf(entity.getOperation());

		dto.resultString = entity.getResultString();
		dto.resultValue = entity.getResultValue();
		dto.resultUnit = entity.getResultUnit();
		dto.resultMeasurementType = entity.getResultMeasurementType();

		dto.error = entity.isError();
		dto.errorMessage = entity.getErrorMessage();

		return dto;
	}

	// DTO → ENTITY
	public QuantityMeasurementEntity toEntity() {

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setThisValue(this.thisValue);
		entity.setThisUnit(this.thisUnit);
		entity.setThisMeasurementType(this.thisMeasurementType);

		entity.setThatValue(this.thatValue);
		entity.setThatUnit(this.thatUnit);
		entity.setThatMeasurementType(this.thatMeasurementType);

		// 🔥 ENUM → STRING
		entity.setOperation(this.operation.name());

		entity.setResultString(this.resultString);
		entity.setResultValue(this.resultValue);
		entity.setResultUnit(this.resultUnit);
		entity.setResultMeasurementType(this.resultMeasurementType);

		entity.setError(this.error);
		entity.setErrorMessage(this.errorMessage);

		return entity;
	}

	// LIST: ENTITY → DTO
	public static List<QuantityMeasurementDTO> fromList(List<QuantityMeasurementEntity> entities) {

		return entities.stream().map(QuantityMeasurementDTO::from).collect(Collectors.toList());
	}

	// LIST: DTO → ENTITY
	public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {

		return dtos.stream().map(QuantityMeasurementDTO::toEntity).collect(Collectors.toList());
	}
}