# Quantity Measurement App

## App Overview

The Quantity Measurement App is designed to compare two quantities (such as length, weight, etc.) and determine their equality or relational comparison.

As the application evolves, it progressively introduces:

- Unit-to-unit conversion  
- Cross-unit comparison  
- Quantity arithmetic operations  
- Support for multiple measurement types  

The application is developed incrementally, starting with simple use cases and gradually adding complexity. Each use case defines a clear and limited scope to ensure the system remains maintainable, modular, and focused.

---

## Development Principle

While building this application:

- Strictly follow the scope defined in each use case.
- Avoid adding functionality beyond the given requirements.
- Start simple and progressively increase complexity.
- Focus on clean design and incremental enhancement.

In enterprise projects, complete domain knowledge may not always be available initially. Therefore, it is important to implement only what is required at each stage and evolve the system step by step.

---

# UC5: Unit-to-Unit Conversion (Same Measurement Type)

## Description

UC5 extends UC4 by introducing explicit unit-to-unit conversion within the same measurement type (length). In addition to equality checks, the API now provides a `convert` operation that transforms a value from a source unit to a target unit using centralized conversion factors.

---

## Preconditions

- The generic Quantity structure and LengthUnit enum (FEET, INCHES, YARDS, CENTIMETERS) exist.
- Each unit defines a conversion factor relative to a common base unit.
- Input includes a numeric value, a valid source unit, and a valid target unit.

---

## Main Flow

1. Client invokes the `convert(value, sourceUnit, targetUnit)` API.
2. Input value is validated (finite number).
3. Source and target units are validated (non-null and supported).
4. Value is normalized to the base unit.
5. The normalized value is converted to the target unit.
6. The converted numeric result is returned.

---

## Postconditions

- Returns the numeric value expressed in the target unit.
- Invalid inputs (null unit, NaN, infinite values) result in a documented exception.
- Mathematical equivalence is preserved within floating-point precision limits.

---

## Concepts Covered

- Enum-based conversion factor management  
- Immutability and value object semantics  
- Base unit normalization for consistent conversion  
- Method overloading and method overriding (`equals()`, `toString()`)  
- Input validation and exception handling  
- Precision handling using floating-point tolerance  
- Clean API design for usability and extensibility  

---

## Testing Aspects Covered

- Basic adjacent unit conversion (feet–inches, yards–feet)  
- Cross-unit conversion (yards–inches, centimeters–feet, etc.)  
- Bidirectional and round-trip conversion accuracy  
- Same-unit conversion behavior  
- Zero, negative, large, and small value handling  
- Floating-point precision tolerance  
- Invalid unit and invalid value validation  
- Mathematical consistency of conversion formula  
- Enum integration and centralized factor usage  

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC5-UnitConversion/src)
