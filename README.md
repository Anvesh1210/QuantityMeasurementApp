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

# UC1: Feet Measurement Equality

## Description

UC1 verifies equality between two numerical values measured in feet. It ensures accurate value-based comparison with proper handling of floating-point precision, null safety, and type safety.

---

## Preconditions

- The application is initialized.
- Two numerical values in feet are provided for comparison.

---

## Main Flow

1. User inputs two numerical values in feet.
2. The system validates that inputs are numeric.
3. The two values are compared for equality.
4. The result (`true` or `false`) is returned.

---

## Postconditions

- Returns `true` if both values are equal.
- Returns `false` if values differ.
- Ensures safe handling of null and invalid comparisons.

---

## Concepts Covered

- Object equality implementation  
- Floating-point comparison using `Double.compare()`  
- Null handling to prevent runtime exceptions  
- Type safety during object comparison  
- Encapsulation and immutability principles  

---

## Testing Aspects Covered

The following areas were validated through unit testing:

- Same value comparison  
- Different value comparison  
- Null comparison handling  
- Type safety validation  
- Reflexive property of equality  
- Consistency of equality results  
- Edge cases involving floating-point precision  

---

## Summary

UC1 establishes the foundation for reliable measurement comparison by ensuring accurate equality checks, safe handling of edge cases, and compliance with Java’s equality contract.

## Code Reference

- Source Code: [View UC1 Implementation](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC1-FeetEquality/src)
