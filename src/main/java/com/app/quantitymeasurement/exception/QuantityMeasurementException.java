package com.app.quantitymeasurement.exception;

public class QuantityMeasurementException extends RuntimeException {

    private final String errorCode;

    public QuantityMeasurementException(String message) {
        super(message);
        this.errorCode = null;
    }

    public QuantityMeasurementException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }

    public QuantityMeasurementException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public QuantityMeasurementException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}