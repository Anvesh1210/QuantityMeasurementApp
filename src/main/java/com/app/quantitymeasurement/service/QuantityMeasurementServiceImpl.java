package com.app.quantitymeasurement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.quantitymeasurement.dto.OperationType;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	@Autowired
	private QuantityMeasurementRepository repository;

	// 🔥 BASIC CONVERSION LOGIC
	private double convertToBase(QuantityDTO dto) {
		switch (dto.getUnit()) {
		case "FEET":
			return dto.getValue() * 12;
		case "INCHES":
			return dto.getValue();
		case "YARDS":
			return dto.getValue() * 36;
		case "GALLON":
			return dto.getValue() * 3.785;
		case "LITRE":
			return dto.getValue();
		case "CELSIUS":
			return dto.getValue();
		case "FAHRENHEIT":
			return (dto.getValue() - 32) * 5 / 9;
		default:
			throw new RuntimeException("Invalid unit");
		}
	}

	// COMPARE
	@Override
	public QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2) {

		double v1 = convertToBase(q1);
		double v2 = convertToBase(q2);

		boolean result = Math.abs(v1 - v2) < 0.0001;

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.COMPARE);
		entity.setResultString(String.valueOf(result));
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	// CONVERT
	@Override
	public QuantityMeasurementDTO convert(QuantityDTO q1, QuantityDTO q2) {

		double base = convertToBase(q1);

		double result;
		switch (q2.getUnit()) {
		case "FAHRENHEIT":
			result = (base * 9 / 5) + 32;
			break;
		case "CELSIUS":
			result = base;
			break;
		default:
			result = base;
		}

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.CONVERT);
		entity.setResultValue(result);
		entity.setResultUnit(q2.getUnit());
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	// ADD
	@Override
	public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2) {

		double result;

		// Convert q2 into q1's unit
		if (q1.getUnit().equals("GALLON") && q2.getUnit().equals("LITRE")) {
			double q2InGallon = q2.getValue() / 3.785;
			result = q1.getValue() + q2InGallon;

		} else if (q1.getUnit().equals("LITRE") && q2.getUnit().equals("GALLON")) {
			double q2InLitre = q2.getValue() * 3.785;
			result = q1.getValue() + q2InLitre;

		} else {
			// fallback for same units or other types
			result = q1.getValue() + q2.getValue();
		}

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.ADD);
		entity.setResultValue(result);
		entity.setResultUnit(q1.getUnit());
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2, QuantityDTO target) {

		double result = convertToBase(q1) + convertToBase(q2);

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.ADD);
		entity.setResultValue(result);
		entity.setResultUnit(target.getUnit());
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	// SUBTRACT
	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2) {

		double result;

		// Convert q2 into q1 unit
		if (q1.getUnit().equals("FEET") && q2.getUnit().equals("INCHES")) {
			double q2InFeet = q2.getValue() / 12;
			result = q1.getValue() - q2InFeet;

		} else if (q1.getUnit().equals("INCHES") && q2.getUnit().equals("FEET")) {
			double q2InInches = q2.getValue() * 12;
			result = q1.getValue() - q2InInches;

		} else {
			result = q1.getValue() - q2.getValue();
		}

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.SUBTRACT);
		entity.setResultValue(result);
		entity.setResultUnit(q1.getUnit()); // IMPORTANT
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2, QuantityDTO target) {

		double result = convertToBase(q1) - convertToBase(q2);

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.SUBTRACT);
		entity.setResultValue(result);
		entity.setResultUnit(target.getUnit());
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	// DIVIDE
	@Override
	public QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2) {

		if (q2.getValue() == 0) {
			throw new RuntimeException("Divide by zero");
		}

		double result = convertToBase(q1) / convertToBase(q2);

		QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.DIVIDE);
		entity.setResultValue(result);
		entity.setError(false);

		repository.save(entity);
		return QuantityMeasurementDTO.from(entity);
	}

	// 🔥 COMMON ENTITY BUILDER
	private QuantityMeasurementEntity buildEntity(QuantityDTO q1, QuantityDTO q2, OperationType op) {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

		entity.setThisValue(q1.getValue());
		entity.setThisUnit(q1.getUnit());
		entity.setThisMeasurementType(q1.getMeasurementType());

		entity.setThatValue(q2.getValue());
		entity.setThatUnit(q2.getUnit());
		entity.setThatMeasurementType(q2.getMeasurementType());

		entity.setOperation(op.name());

		return entity;
	}

	// HISTORY

	@Override
	public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
		return QuantityMeasurementDTO.fromList(repository.findByOperation(operation));
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
		return QuantityMeasurementDTO.fromList(repository.findByThisMeasurementType(type));
	}

	@Override
	public long getOperationCount(String operation) {
		return repository.countByOperationAndIsErrorFalse(operation);
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return QuantityMeasurementDTO.fromList(repository.findByIsErrorTrue());
	}
}