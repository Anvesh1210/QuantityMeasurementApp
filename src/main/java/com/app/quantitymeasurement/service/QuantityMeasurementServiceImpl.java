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

        double finalResult = roundToFourDecimals(result);

        QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.CONVERT);
        entity.setResultValue(finalResult);
        entity.setResultString(String.valueOf(finalResult)); // ✅ FIX
        entity.setResultUnit(normalizeUnit(q2.getUnit()));
        entity.setResultMeasurementType(normalizeMeasurementTypeOrThrow(q2.getMeasurementType()));
        entity.setError(false);

        repository.save(entity);
        return QuantityMeasurementDTO.from(entity);
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2) {
        validateCompatible(q1, q2);
        // Default target to q1 if not provided
        return add(q1, q2, q1);
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2, QuantityDTO target) {
        return performArithmetic(q1, q2, target, OperationType.ADD);
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        validateCompatible(q1, q2);
        // Default target to q1 if not provided
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
        double finalResult = roundToFourDecimals(result);

        QuantityMeasurementEntity entity = buildEntity(q1, q2, OperationType.DIVIDE);
        entity.setResultValue(finalResult);
        entity.setResultString(String.valueOf(finalResult)); // ✅ FIX
        entity.setResultUnit("RATIO");
        entity.setResultMeasurementType("SCALAR");
        entity.setError(false);

        repository.save(entity);
        return QuantityMeasurementDTO.from(entity);
    }

    private QuantityMeasurementDTO performArithmetic(QuantityDTO q1, QuantityDTO q2, QuantityDTO target,
            OperationType operation) {

        // If target is null, default to q1
        if (target == null) {
            target = q1;
        }

        validateCompatible(q1, q2);
        validateCompatible(q1, target);

        IMeasurable sourceUnit = resolveUnit(q1);
        sourceUnit.validateOperationSupport(operation.name());

        double baseResult = switch (operation) {
            case ADD -> convertToBase(q1) + convertToBase(q2);
            case SUBTRACT -> convertToBase(q1) - convertToBase(q2);
            default -> throw new IllegalArgumentException("Unsupported operation");
        };

        IMeasurable targetUnit = resolveUnit(target);
        double result = targetUnit.convertFromBaseUnit(baseResult);

        double finalResult = roundToFourDecimals(result);

        QuantityMeasurementEntity entity = buildEntity(q1, q2, operation);
        entity.setResultValue(finalResult);
        entity.setResultString(String.valueOf(finalResult));
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
        String type = normalizeMeasurementTypeOrThrow(dto.getMeasurementType());
        String unit = normalizeUnit(dto.getUnit());

        return switch (type) {
        case "LENGTH" -> LengthUnit.valueOf(unit);
        case "VOLUME" -> VolumeUnit.valueOf(unit);
        case "WEIGHT" -> WeightUnit.valueOf(unit);
        case "TEMPERATURE" -> TemperatureUnit.valueOf(unit);
        default -> throw new IllegalArgumentException("Unsupported type");
        };
    }

    private void validateCompatible(QuantityDTO q1, QuantityDTO q2) {
        if (!normalizeMeasurementTypeOrThrow(q1.getMeasurementType())
                .equals(normalizeMeasurementTypeOrThrow(q2.getMeasurementType()))) {
            throw new IllegalArgumentException("Cross-category operation not allowed");
        }
    }

    private String normalizeMeasurementTypeOrThrow(String type) {
        return type == null ? null : type.trim().toUpperCase();
    }

    private String normalizeUnit(String unit) {
        return unit.trim().toUpperCase();
    }

    private double roundToFourDecimals(double value) {
        return Math.round(value * 10000.0) / 10000.0;
    }

    private QuantityMeasurementEntity buildEntity(QuantityDTO q1, QuantityDTO q2, OperationType op) {
        QuantityMeasurementEntity e = new QuantityMeasurementEntity();

        e.setThisValue(q1.getValue());
        e.setThisUnit(normalizeUnit(q1.getUnit()));
        e.setThisMeasurementType(normalizeMeasurementTypeOrThrow(q1.getMeasurementType()));

        e.setThatValue(q2.getValue());
        e.setThatUnit(normalizeUnit(q2.getUnit()));
        e.setThatMeasurementType(normalizeMeasurementTypeOrThrow(q2.getMeasurementType()));

        e.setOperation(op.name());
        return e;
    }

    @Override
    public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
        return QuantityMeasurementDTO.fromList(repository.findByOperation(operation.toUpperCase()));
    }

    @Override
    public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
        return QuantityMeasurementDTO.fromList(repository.findByThisMeasurementType(type.toUpperCase()));
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation.toUpperCase());
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromList(repository.findByIsErrorTrue());
    }
}