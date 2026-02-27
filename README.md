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

# UC4: Extended Unit Support

## Description

UC4 extends UC3 by adding Yards and Centimeters as additional length units. The generic Quantity design scales without modification to core logic, requiring only enum updates for new units and conversion factors.

All equality comparisons now work across feet, inches, yards, and centimeters.

---

## Preconditions

- The refactored generic Quantity structure from UC3 is in place.
- Two numerical values with supported unit types are provided.
- Conversion factors for yards (1 yard = 3 feet) and centimeters (1 cm = 0.393701 inches) are defined.

---

## Main Flow

1. User provides two values with unit types (feet, inches, yards, centimeters).
2. Inputs are validated for numeric correctness and supported units.
3. Values are converted to a common base unit.
4. Converted values are compared.
5. The equality result is returned.

---

## Postconditions

- Returns `true` for equivalent values across all supported units.
- Returns `false` for non-equivalent values.
- Maintains backward compatibility with UC1–UC3.
- No code duplication is introduced.

---

## Concepts Covered

- Scalability of generic design  
- Centralized conversion factor management  
- Enum extensibility  
- Cross-unit mathematical accuracy  
- DRY principle validation  
- Backward compatibility preservation  

---

## Testing Aspects Covered

- Same-unit equality (yards, centimeters)  
- Cross-unit conversions (yard–feet–inch, cm–inch, etc.)  
- Symmetric and transitive equality behavior  
- Multi-unit comparison scenarios  
- Unit validation  
- Null handling and equality contract compliance  
- Floating-point precision handling  

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC4-YardEquality/src)
