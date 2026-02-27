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


# UC12: Subtraction and Division Operations on Quantity Measurements

## Description

UC12 extends the generic `Quantity<U extends IMeasurable>` class by introducing two new arithmetic operations:

- **Subtraction** → Returns `Quantity<U>`
- **Division** → Returns dimensionless `double`

These operations follow the same design principles as equality, conversion, and addition from UC1–UC11:

- Cross-unit arithmetic within same category
- Optional explicit target unit (for subtraction)
- Base unit normalization
- Immutability preservation
- Cross-category prevention
- Comprehensive validation

No architectural restructuring is required.

---

## Features Covered

### Subtraction

- Same-unit subtraction
- Cross-unit subtraction (within same category)
- Explicit target unit specification
- Negative results handling
- Zero-result handling
- Non-commutative property validation
- Subtraction chaining support
- Null operand validation
- Null target unit validation
- Cross-category prevention
- Precision rounding to two decimal places
- Immutability preservation

---

### Division

- Same-unit division
- Cross-unit division (within same category)
- Returns dimensionless scalar (`double`)
- Ratio > 1, < 1, = 1 validation
- Non-commutative property validation
- Division by zero handling
- Large and small ratio handling
- Cross-category prevention
- Null operand validation
- Floating-point precision handling
- Immutability preservation

---

## Validation & Error Handling

- Null quantity checks
- Null target unit checks (subtraction)
- Finite number validation
- Cross-category operation prevention
- Division-by-zero exception handling
- Consistent validation patterns across operations

---

## Architectural Validation

- No changes required to `IMeasurable`
- No changes required to unit enums
- Works across all categories (Length, Weight, Volume)
- Fully backward compatible with UC1–UC11
- Maintains DRY principle
- Preserves Single Responsibility Principle
- Extensible for future arithmetic operations

---

## Mathematical Properties Verified

- Subtraction is non-commutative
- Division is non-commutative
- Division is non-associative
- A + B - B ≈ A (inverse relationship)
- Identity elements validated (subtract zero, divide by one)

---

## Result

UC12 demonstrates that the generic architecture supports full arithmetic capability without modification to the core design.

The system now supports:

- Equality
- Conversion
- Addition
- Subtraction
- Division

All while maintaining scalability, immutability, and type safety.

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC12-ArithmeticOperations/src)
