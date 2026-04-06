package com.app.quantitymeasurement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.dto.OperationType;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.units.IMeasurable;
import com.app.quantitymeasurement.units.LengthUnit;
import com.app.quantitymeasurement.units.TemperatureUnit;
import com.app.quantitymeasurement.units.VolumeUnit;
import com.app.quantitymeasurement.units.WeightUnit;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private static final double EPSILON = 1e-9;

	private final QuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2) {
		validateCompatible(q1, q2);

		double v1 = convertToBase(q1);
		double v2 = convertToBase(q2);
		boolean result = Math.abs(v1 - v2) < 0.0001;

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.COMPARE);
		entity.setResultString(String.valueOf(result));
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO q1, QuantityDTO q2) {
		validateCompatible(q1, q2);

		double baseValue = convertToBase(q1);
		IMeasurable targetUnit = resolveUnit(q2);
		double result = targetUnit.convertFromBaseUnit(baseValue);

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.CONVERT);
		entity.setResultValue(roundToFourDecimals(result));
		entity.setResultUnit(normalizeUnit(q2.getUnit()));
		entity.setResultMeasurementType(normalizeMeasurementTypeOrThrow(q2.getMeasurementType()));
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2) {
		validateCompatible(q1, q2);
		return add(q1, q2, q1);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2, QuantityDTO target) {
		return performArithmetic(q1, q2, target, OperationType.ADD);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2) {
		validateCompatible(q1, q2);
		return subtract(q1, q2, q1);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2, QuantityDTO target) {
		return performArithmetic(q1, q2, target, OperationType.SUBTRACT);
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2) {
		validateCompatible(q1, q2);

		IMeasurable unit = resolveUnit(q1);
		unit.validateOperationSupport(OperationType.DIVIDE.name());

		double denominator = convertToBase(q2);
		if (Math.abs(denominator) < EPSILON) {
			throw new ArithmeticException("Divide by zero");
		}

		double result = convertToBase(q1) / denominator;

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.DIVIDE);
		entity.setResultValue(roundToFourDecimals(result));
		entity.setResultUnit("RATIO");
		entity.setResultMeasurementType("SCALAR");
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	private QuantityMeasurementDTO performArithmetic(QuantityDTO q1, QuantityDTO q2, QuantityDTO target,
			OperationType operation) {
		if (target == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		validateCompatible(q1, q2);
		validateCompatible(q1, target);

		IMeasurable sourceUnit = resolveUnit(q1);
		sourceUnit.validateOperationSupport(operation.name());

		double baseResult = switch (operation) {
		case ADD -> convertToBase(q1) + convertToBase(q2);
		case SUBTRACT -> convertToBase(q1) - convertToBase(q2);
		default -> throw new IllegalArgumentException("Unsupported arithmetic operation: " + operation);
		};

		IMeasurable targetUnit = resolveUnit(target);
		double result = targetUnit.convertFromBaseUnit(baseResult);

		QuantityMeasurementEntity entity = buildEntity(q1, q2, operation);
		entity.setResultValue(roundToFourDecimals(result));
		entity.setResultUnit(normalizeUnit(target.getUnit()));
		entity.setResultMeasurementType(normalizeMeasurementTypeOrThrow(target.getMeasurementType()));
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	private double convertToBase(QuantityDTO dto) {
		IMeasurable unit = resolveUnit(dto);
		return unit.convertToBaseUnit(dto.getValue());
	}

	private IMeasurable resolveUnit(QuantityDTO dto) {
		String measurementType = normalizeMeasurementTypeOrThrow(dto.getMeasurementType());
		String unitName = normalizeUnit(dto.getUnit());

		try {
			return switch (measurementType) {
			case "LENGTH" -> LengthUnit.valueOf(unitName);
			case "VOLUME" -> VolumeUnit.valueOf(unitName);
			case "WEIGHT" -> WeightUnit.valueOf(unitName);
			case "TEMPERATURE" -> TemperatureUnit.valueOf(unitName);
			default -> throw new IllegalArgumentException("Unsupported measurement type: " + measurementType);
			};
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException(
					"Unit '" + unitName + "' is not valid for measurement type '" + measurementType + "'");
		}
	}

	private void validateCompatible(QuantityDTO left, QuantityDTO right) {
		if (left == null || right == null) {
			throw new IllegalArgumentException("Quantity cannot be null");
		}

		String leftType = normalizeMeasurementTypeOrThrow(left.getMeasurementType());
		String rightType = normalizeMeasurementTypeOrThrow(right.getMeasurementType());

		if (!leftType.equals(rightType)) {
			throw new IllegalArgumentException("Cross-category operation is not allowed");
		}
	}

	private String normalizeMeasurementTypeOrThrow(String measurementType) {
		String normalized = QuantityDTO.normalizeMeasurementType(measurementType);
		if (normalized == null) {
			throw new IllegalArgumentException("Invalid measurement type: " + measurementType);
		}
		return normalized;
	}

	private String normalizeUnit(String unit) {
		if (unit == null || unit.isBlank()) {
			throw new IllegalArgumentException("Unit cannot be blank");
		}
		return unit.trim().toUpperCase();
	}

	private double roundToFourDecimals(double value) {
		return Math.round(value * 10000.0) / 10000.0;
	}

	private QuantityMeasurementEntity buildEntity(QuantityDTO q1, QuantityDTO q2, OperationType operation) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setThisValue(q1.getValue());
		entity.setThisUnit(normalizeUnit(q1.getUnit()));
		entity.setThisMeasurementType(normalizeMeasurementTypeOrThrow(q1.getMeasurementType()));

		entity.setThatValue(q2.getValue());
		entity.setThatUnit(normalizeUnit(q2.getUnit()));
		entity.setThatMeasurementType(normalizeMeasurementTypeOrThrow(q2.getMeasurementType()));

		entity.setOperation(operation.name());
		return entity;
	}

	@Override
	public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
		return QuantityMeasurementDTO.fromList(repository.findByOperation(normalizeOperation(operation)));
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
		return QuantityMeasurementDTO.fromList(repository.findByThisMeasurementType(normalizeMeasurementTypeOrThrow(type)));
	}

	@Override
	public long getOperationCount(String operation) {
		return repository.countByOperationAndIsErrorFalse(normalizeOperation(operation));
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return QuantityMeasurementDTO.fromList(repository.findByIsErrorTrue());
	}

	private String normalizeOperation(String operation) {
		if (operation == null || operation.isBlank()) {
			throw new IllegalArgumentException("Operation cannot be blank");
		}

		try {
			return OperationType.valueOf(operation.trim().toUpperCase()).name();
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Unsupported operation: " + operation);
		}
	}
}
