package com.app.quantitymeasurement.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A quantity with a value and unit")
public class QuantityDTO {

    // INNER INTERFACE
    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    // ENUMS INSIDE DTO
    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS, METERS;

        public String getUnitName() {
            return this.name();
        }

        public String getMeasurementType() {
            return "LENGTH";
        }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;

        public String getUnitName() {
            return this.name();
        }

        public String getMeasurementType() {
            return "VOLUME";
        }
    }

    public enum WeightUnit implements IMeasurableUnit {
        MILLIGRAM, GRAM, KILOGRAM, POUND, TONNE;

        public String getUnitName() {
            return this.name();
        }

        public String getMeasurementType() {
            return "WEIGHT";
        }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;

        public String getUnitName() {
            return this.name();
        }

        public String getMeasurementType() {
            return "TEMPERATURE";
        }
    }

    // FIELDS

    @Schema(example = "1.0")
    private double value;

    @NotBlank(message = "Unit cannot be blank")
    @Schema(example = "FEET")
    private String unit;

    @NotBlank(message = "Measurement type cannot be blank")
    @Pattern(
            regexp = "(?i)LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit|LENGTH|VOLUME|WEIGHT|TEMPERATURE",
            message = "Invalid measurement type"
    )
    @Schema(example = "LENGTH")
    private String measurementType;

    // CONSTRUCTORS
    public QuantityDTO() {}

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public static String normalizeMeasurementType(String rawMeasurementType) {
        if (rawMeasurementType == null) {
            return null;
        }

        String normalized = rawMeasurementType.trim().toUpperCase();
        return switch (normalized) {
        case "LENGTH", "LENGTHUNIT" -> "LENGTH";
        case "VOLUME", "VOLUMEUNIT" -> "VOLUME";
        case "WEIGHT", "WEIGHTUNIT" -> "WEIGHT";
        case "TEMPERATURE", "TEMPERATUREUNIT" -> "TEMPERATURE";
        default -> null;
        };
    }

    // VALIDATION LOGIC

    @AssertTrue(message = "Value must be a finite number")
    public boolean isFiniteValue() {
        return Double.isFinite(value);
    }

    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnit() {
        String normalizedType = normalizeMeasurementType(measurementType);
        if (normalizedType == null || unit == null || unit.isBlank()) {
            return false;
        }

        String normalizedUnit = unit.trim().toUpperCase();

        try {
            switch (normalizedType) {

                case "LENGTH":
                    LengthUnit.valueOf(normalizedUnit);
                    break;

                case "VOLUME":
                    VolumeUnit.valueOf(normalizedUnit);
                    break;

                case "WEIGHT":
                    WeightUnit.valueOf(normalizedUnit);
                    break;

                case "TEMPERATURE":
                    TemperatureUnit.valueOf(normalizedUnit);
                    break;

                default:
                    return false;
            }

        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
