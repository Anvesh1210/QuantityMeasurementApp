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

# UC8: Refactoring Unit Enum to Standalone with Conversion Responsibility

## Description

UC8 refactors the architecture by extracting `LengthUnit` into a standalone, top-level enum and assigning it full responsibility for unit conversion logic.

This removes circular dependency risks, improves cohesion, enforces the Single Responsibility Principle, and establishes a scalable design pattern for supporting multiple measurement categories (length, weight, volume, etc.).

All functionality from UC1–UC7 remains unchanged.

---

## Preconditions

- The refactored Quantity structure from UC1–UC7 exists.
- `LengthUnit` is implemented as a standalone enum.
- Conversion factors are defined within `LengthUnit`.
- All previous features (equality, conversion, addition) continue to work.

---

## Main Flow

1. `LengthUnit` is moved to a top-level enum.
2. Conversion logic is implemented inside the enum:
   - `convertToBaseUnit(value)`
   - `convertFromBaseUnit(baseValue)`
3. `QuantityLength` delegates conversion to `LengthUnit`.
4. Equality, conversion, and addition operations use delegated methods.
5. All existing test cases pass without modification.

---

## Postconditions

- `LengthUnit` manages all conversion responsibilities.
- `QuantityLength` focuses only on comparison and arithmetic logic.
- Circular dependencies are eliminated.
- Single Responsibility Principle is enforced.
- Backward compatibility with UC1–UC7 is preserved.
- The architecture supports future measurement categories.

---

## Concepts Covered

- Single Responsibility Principle (SRP)  
- Separation of Concerns  
- Delegation pattern  
- Dependency reduction and loose coupling  
- Enum encapsulating behavior and data  
- Architectural scalability  
- Refactoring best practices  
- Backward compatibility preservation  

---

## Testing Aspects Covered

- Standalone enum accessibility and correctness  
- Base-unit and from-base-unit conversions  
- Delegation of conversion logic from Quantity to Unit  
- Equality, conversion, and addition correctness after refactor  
- Backward compatibility with UC1–UC7 test suites  
- Round-trip conversion precision  
- Null and invalid value validation  
- Architectural readiness for multiple measurement categories  
- Enum immutability and thread-safety  

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC8-StandaloneUnit/src)
