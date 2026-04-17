package com.app.quantitymeasurement.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuantityMeasurementDTO {

	// INPUT
	private Double thisValue;
	private String thisUnit;
	private String thisMeasurementType;

	private Double thatValue;
	private String thatUnit;
	private String thatMeasurementType;

	// OPERATION
	private OperationType operation;

	// RESULT
	private String resultString;
	private Double resultValue;
	private String resultUnit;
	private String resultMeasurementType;

	// ERROR
	@JsonProperty("error")
	private boolean error;

	private String errorMessage;

	// ENTITY -> DTO
	public static QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
		if (entity == null) {
			return null;
		}

		return QuantityMeasurementDTO.builder().thisValue(entity.getThisValue()).thisUnit(entity.getThisUnit())
				.thisMeasurementType(entity.getThisMeasurementType()).thatValue(entity.getThatValue())
				.thatUnit(entity.getThatUnit()).thatMeasurementType(entity.getThatMeasurementType())
				.operation(parseOperation(entity.getOperation())).resultString(entity.getResultString())
				.resultValue(entity.getResultValue()).resultUnit(entity.getResultUnit())
				.resultMeasurementType(entity.getResultMeasurementType()).error(entity.isError())
				.errorMessage(entity.getErrorMessage()).build();
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
}