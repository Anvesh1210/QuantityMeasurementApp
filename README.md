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
# UC3: Generic Quantity Class (DRY Principle)

## Description

UC3 refactors the separate Feet and Inches implementations into a single generic Quantity structure. This removes code duplication and applies the DRY principle while preserving all functionality from UC1 and UC2.

The system supports both same-unit and cross-unit equality by converting values to a common base unit before comparison.

---

## Preconditions

- The application is instantiated.
- Two numerical values with their respective unit types are provided.
- Conversion factors for supported units are defined.

---

## Main Flow

1. User provides two values with unit types.
2. Inputs are validated for numeric correctness and supported units.
3. Values are converted to a common base unit.
4. Converted values are compared.
5. The equality result is returned.

---

## Postconditions

- Returns `true` for equivalent values (including cross-unit comparisons).
- Returns `false` for different values.
- Eliminates duplication from UC1 and UC2.
- Maintains backward compatibility.

---

## Concepts Covered

- DRY Principle  
- Enum usage for unit representation  
- Cross-unit conversion logic  
- Value-based equality  
- Encapsulation and abstraction  
- Scalability for adding new units  

---

## Testing Aspects Covered

- Same-unit equality  
- Cross-unit equality (e.g., 1 foot = 12 inches)  
- Different value comparison  
- Unit validation and type safety  
- Null handling  
- Equality contract compliance  
- Backward compatibility with previous use cases  

---

## Code Reference

[UC3 Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC3-GenericLength/src)
