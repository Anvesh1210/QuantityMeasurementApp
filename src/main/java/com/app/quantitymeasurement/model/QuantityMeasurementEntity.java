package com.app.quantitymeasurement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.app.quantitymeasurement.units.IMeasurable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quantity_measurement_entity", indexes = { @Index(name = "idx_operation", columnList = "operation"),
		@Index(name = "idx_measurement_type", columnList = "this_measurement_type"),
		@Index(name = "idx_created_at", columnList = "created_at") })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// PRIMARY KEY
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// THIS QUANTITY
	@Column(name = "this_value", nullable = false)
	private double thisValue;

	@Column(name = "this_unit", nullable = false)
	private String thisUnit;

	@Column(name = "this_measurement_type", nullable = false)
	private String thisMeasurementType;

	// THAT QUANTITY
	@Column(name = "that_value")
	private Double thatValue;

	@Column(name = "that_unit")
	private String thatUnit;

	@Column(name = "that_measurement_type")
	private String thatMeasurementType;

	// OPERATION
	@Column(name = "operation", nullable = false)
	private String operation;

	// RESULT
	@Column(name = "result_value")
	private Double resultValue;

	@Column(name = "result_unit")
	private String resultUnit;

	@Column(name = "result_measurement_type")
	private String resultMeasurementType;

	@Column(name = "result_string")
	private String resultString;

	// ERROR
	@Column(name = "is_error")
	private boolean isError = false;

	@Column(name = "error_message")
	private String errorMessage;

	// TIMESTAMPS
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	// LIFECYCLE METHODS
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

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
