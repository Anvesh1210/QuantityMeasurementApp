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

# UC6: Addition of Two Length Units (Same Category)

## Description

UC6 extends UC5 by introducing addition between two length measurements. The API allows adding two values (possibly with different units) and returns the result in the unit of the first operand.

All additions are performed by normalizing values to a common base unit before computing the final result.

---

## Preconditions

- The generic Quantity structure and LengthUnit enum exist.
- Conversion factors are defined relative to a consistent base unit.
- Two valid length measurements are provided.
- Both operands belong to the same measurement category (length).

---

## Main Flow

1. Client invokes the `add()` API with two length values.
2. Inputs are validated (non-null, finite numbers, valid units).
3. Both values are converted to the base unit.
4. Converted values are added.
5. The result is converted back to the unit of the first operand.
6. A new Quantity object representing the sum is returned.

---

## Postconditions

- A new Quantity object is returned with the computed sum.
- The original operands remain unchanged (immutability).
- Invalid inputs result in a documented exception.
- Addition is mathematically accurate within floating-point precision.
- Commutative behavior is preserved.

---

## Concepts Covered

- Arithmetic operations on value objects  
- Reuse of conversion logic from UC5  
- Base unit normalization for consistent calculations  
- Immutability and functional design  
- Method overloading for API flexibility  
- Input validation and exception handling  
- Floating-point precision handling  

---

## Testing Aspects Covered

- Same-unit addition  
- Cross-unit addition (feet–inches, yards–feet, cm–inch, etc.)  
- Commutativity verification  
- Identity element (addition with zero)  
- Negative value handling  
- Large and small magnitude value handling  
- Null operand validation  
- Precision tolerance and mathematical consistency  

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC6-UnitAddition/src)
