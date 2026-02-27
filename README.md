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

# UC7: Addition with Target Unit Specification

## Description

UC7 extends UC6 by allowing the caller to explicitly specify the unit of the addition result. Instead of defaulting to the unit of the first operand, the result can be expressed in any supported length unit.

This provides flexibility when the result must be represented in a specific unit, regardless of the operand units.

---

## Preconditions

- The generic Quantity structure and LengthUnit enum (FEET, INCHES, YARDS, CENTIMETERS) exist.
- Conversion factors are defined relative to a consistent base unit.
- Two valid length measurements are provided.
- A valid target unit is explicitly specified.
- All units belong to the same measurement category (length).

---

## Main Flow

1. Client invokes `add(length1, length2, targetUnit)`.
2. Inputs are validated (non-null operands, valid units, finite values).
3. Both operands are converted to the base unit.
4. Converted values are added.
5. The sum is converted to the explicitly specified target unit.
6. A new Quantity object is returned.

---

## Postconditions

- A new Quantity object is returned in the specified target unit.
- Original operands remain unchanged (immutability).
- Result unit always matches the explicitly provided target unit.
- Invalid inputs result in documented exceptions.
- Addition remains mathematically accurate and commutative.

---

## Concepts Covered

- Method overloading for flexible API design  
- Explicit parameter-driven result control  
- Reuse of base unit normalization logic  
- Immutability and pure function behavior  
- DRY principle compliance through shared internal logic  
- Cross-unit arithmetic independence  
- Precision handling across different unit scales  
- Validation of category and unit compatibility  

---

## Testing Aspects Covered

- Explicit target unit equal to first operand  
- Explicit target unit equal to second operand  
- Explicit target unit different from both operands  
- Commutativity with explicit target unit  
- Zero and negative value handling  
- Large-to-small and small-to-large scale conversions  
- Null and invalid target unit validation  
- Precision tolerance across unit combinations  
- Mathematical consistency across all supported units  

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC7-TargetUnitAddition/src)
