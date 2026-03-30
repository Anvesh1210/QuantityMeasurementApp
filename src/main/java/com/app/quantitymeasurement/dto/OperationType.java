package com.app.quantitymeasurement.dto;

public enum OperationType {

    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    COMPARE,
    CONVERT;

    //for display (nice for UI / logs)
    public String getDisplayName() {
        return this.name().toLowerCase();
    }
}