package com.app.quantitymeasurement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long>{
	
	// Find all measurements by operation
    List<QuantityMeasurementEntity> findByOperation(String operation);

    // Find all measurements by measurement type
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);

    // Find measurements created after a specific date
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);

    // Custom JPQL query for successful operations
    @Query("SELECT e FROM QuantityMeasurementEntity e WHERE e.operation = :operation AND e.isError = false")
    List<QuantityMeasurementEntity> findSuccessfulOperations(String operation);

    // Count successful operations
    long countByOperationAndIsErrorFalse(String operation);

    // Find measurements with errors
    List<QuantityMeasurementEntity> findByIsErrorTrue();

}
