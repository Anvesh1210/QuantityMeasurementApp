package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class InchesEqualityTest {

    // checking same value
    @Test
    public void testInchesEquality_SameValue() {
        assertEquals(true, QuantityMeasurementApp.compareInches(1.0, 1.0));
    }

    // checking different values
    @Test
    public void testInchesEquality_DifferentValue() {
        assertEquals(false, QuantityMeasurementApp.compareInches(1.0, 2.0));
    }

    // check for a null value
    @Test
    public void testInchesEquality_NullComparison() {
        Inches inches = new Inches(1.0);
        assertFalse(inches.equals(null));
    }

    // check for different classes
    @Test
    public void testInchesEquality_DifferentClass() {
        Inches inches = new Inches(1.0);
        String string = "1.0";
        assertEquals(false, inches.equals(string));
    }

    // check for same Inches object
    @Test
    public void testInchesEquality_SameReference() {
        Inches inches = new Inches(1.0);
        assertEquals(true, inches.equals(inches));
    }
}