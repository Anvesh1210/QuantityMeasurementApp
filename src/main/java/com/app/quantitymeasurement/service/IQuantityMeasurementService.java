package com.app.quantitymeasurement.service;

import java.util.List;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {

    // CORE OPERATIONS
    QuantityMeasurementDTO compare(QuantityDTO thisDTO, QuantityDTO thatDTO);

    QuantityMeasurementDTO convert(QuantityDTO thisDTO, QuantityDTO thatDTO);

    QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO);

    QuantityMeasurementDTO add(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetUnitDTO);

    QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO);

    QuantityMeasurementDTO subtract(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetUnitDTO);

    QuantityMeasurementDTO divide(QuantityDTO thisDTO, QuantityDTO thatDTO);

    // HISTORY APIs
    List<QuantityMeasurementDTO> getOperationHistory(String operation);

    List<QuantityMeasurementDTO> getMeasurementsByType(String type);

    long getOperationCount(String operation);

    List<QuantityMeasurementDTO> getErrorHistory();
}