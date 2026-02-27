# QuantityMeasurementApp

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

# UC10: Generic Quantity Class with Unit Interface for Multi-Category Support

## Description

UC10 refactors the system into a single generic `Quantity<U extends IMeasurable>` class that supports all measurement categories through a common `IMeasurable` interface.

This eliminates duplication between category-specific classes (e.g., length and weight), restores the DRY and Single Responsibility principles, and establishes a scalable architecture for adding new measurement types without modifying core logic.

All functionality from UC1–UC9 is preserved.

---

## Preconditions

- All UC1–UC9 functionality is operational.
- `IMeasurable` interface defines the unit conversion contract.
- `LengthUnit` and `WeightUnit` implement `IMeasurable`.
- Generic `Quantity<U>` replaces category-specific Quantity classes.
- Type safety is enforced using bounded generics.

---

## Main Flow

1. Define `IMeasurable` interface with conversion methods.
2. Refactor unit enums to implement the interface.
3. Implement generic `Quantity<U>`:
   - Holds `value` and `unit`
   - Validates constructor inputs
   - Implements `equals()`, `convertTo()`, and `add()` methods
   - Delegates conversion to unit implementations
4. Simplify `QuantityMeasurementApp` using generic demonstration methods.
5. Ensure all previous test cases pass without modification.

---

## Postconditions

- A single reusable `Quantity<U>` class supports all categories.
- Unit enums encapsulate conversion behavior.
- Category-specific duplication is eliminated.
- Backward compatibility with UC1–UC9 is maintained.
- New measurement categories require only a new enum implementing `IMeasurable`.
- Code growth becomes linear instead of exponential.

---

## Concepts Covered

- Generic programming with bounded type parameters  
- Interface-based design and polymorphism  
- DRY and Single Responsibility principles  
- Open-Closed Principle  
- Cross-category type safety (compile-time and runtime)  
- Delegation and composition over inheritance  
- Enum behavior encapsulation  
- Architectural scalability and maintainability  
- Backward compatibility preservation  

---

## Testing Aspects Covered

- `IMeasurable` interface implementation validation  
- Generic `Quantity<U>` equality, conversion, and addition  
- Cross-category comparison prevention  
- Backward compatibility with UC1–UC9 test suites  
- Type safety enforcement through generics  
- Wildcard usage (`Quantity<?>`) flexibility  
- Round-trip conversion precision  
- HashCode and equals contract preservation  
- Immutability validation  
- Scalability with new unit enums (e.g., VolumeUnit)  
- DRY principle validation (single source of truth)  
- Runtime safety despite type erasure  
- Simplified demonstration method verification  
- Performance equivalence with pre-generic implementation  

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC10-GenericCategoryUnit/src)
