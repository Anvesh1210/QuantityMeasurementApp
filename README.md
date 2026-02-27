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
# UC2: Feet and Inches Measurement Equality

## Description

UC2 extends UC1 to support equality checks for inches in addition to feet. Both units are handled separately, and comparisons are performed only within the same unit type.

---

## Preconditions

- The application is instantiated.
- Two numerical values in feet are provided.
- Two numerical values in inches are provided.

---

## Main Flow

1. Separate methods are invoked for feet and inches equality checks.
2. Inputs are validated to ensure they are numeric.
3. Values are compared within the same unit type.
4. The result (`true` or `false`) is returned.

---

## Postconditions

- Returns `true` if values in the same unit are equal.
- Returns `false` if values differ.
- Ensures null and type-safe comparisons.
- Supports both feet-to-feet and inch-to-inch comparisons.

---

## Concepts Covered

- Value-based equality across multiple unit types  
- Floating-point comparison using `Double.compare()`  
- Null safety and type checking  
- Encapsulation and immutability  

---

## Testing Aspects Covered

- Same value comparison (feet and inches)  
- Different value comparison  
- Null handling  
- Type safety validation  
- Reflexive and consistent equality behavior  

---

## Design Limitation

Using separate implementations for feet and inches introduces code duplication. Since both share similar logic, this approach violates the DRY principle and increases maintenance effort. A unified quantity abstraction would improve scalability and maintainability.

---

## Code Reference

- Code: [View UC2 Implementation](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC2-InchEquality/src)
