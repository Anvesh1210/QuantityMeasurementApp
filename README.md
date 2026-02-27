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

# UC9: Weight Measurement Equality, Conversion, and Addition

## Description

UC9 extends the application to support a new measurement category: **weight**.  
The system now handles kilograms, grams, and pounds with the same equality, conversion, and addition capabilities previously implemented for length.

Weight operates independently from length, ensuring strict category type safety.

---

## Supported Units

- KILOGRAM (Base Unit)  
- GRAM (1 kg = 1000 g)  
- POUND (1 lb ≈ 0.453592 kg)  

---

## Preconditions

- `WeightUnit` exists as a standalone enum with conversion responsibility.
- `QuantityWeight` mirrors the architectural pattern of `QuantityLength`.
- Conversion factors are defined relative to kilogram (base unit).
- Length functionality from UC1–UC8 remains unaffected.
- Weight and length categories are not comparable.

---

## Main Flow

### Equality
1. Two weight values with units are provided.
2. Values are validated.
3. Both values are converted to kilogram.
4. Converted values are compared using `equals()`.

### Conversion
1. A weight value and target unit are provided.
2. Value is normalized to kilogram.
3. Converted to target unit.
4. A new `QuantityWeight` object is returned.

### Addition
1. Two weight values are provided.
2. Both are normalized to kilogram.
3. Values are summed.
4. Result is returned in either:
   - First operand’s unit, or  
   - Explicitly specified target unit.

---

## Postconditions

- Equivalent values across units are considered equal.
- Conversions are mathematically accurate within floating-point tolerance.
- Addition returns new immutable objects.
- Length and weight remain separate, non-interoperable categories.
- Architectural scalability is validated for multiple measurement types.

---

## Concepts Covered

- Multiple measurement category support  
- Category-level type safety  
- Base unit normalization (kilogram)  
- Enum-based conversion responsibility  
- Immutability and value object semantics  
- Cross-unit arithmetic operations  
- Equality and hashCode contract compliance  
- Floating-point precision handling  
- Architectural scalability validation  

---

## Testing Aspects Covered

- Same-unit equality (kg–kg, g–g, lb–lb)  
- Cross-unit equality (kg–g, kg–lb, g–lb)  
- Weight vs. length incompatibility validation  
- Conversion accuracy between all weight units  
- Round-trip conversion precision  
- Same-unit and cross-unit addition  
- Explicit target unit addition  
- Commutativity of addition  
- Null and invalid unit validation  
- Zero, negative, small, and large value handling  
- Enum immutability and conversion correctness  
- Backward compatibility with UC1–UC8  

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC9-WeightMeasurement/src)
