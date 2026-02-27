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


# UC11: Volume Measurement Support (Litre, Millilitre, Gallon)

## Description

UC11 introduces **Volume** as a new measurement category using the generic  
`Quantity<U extends IMeasurable>` architecture established in UC10.

Supported Units:
- LITRE (Base Unit)
- MILLILITRE
- GALLON

No modifications are made to:
- `Quantity<U>`
- `IMeasurable`
- `QuantityMeasurementApp`

Only a new `VolumeUnit` enum implementing `IMeasurable` is added.

---

## Features Covered

### Equality
- Same-unit equality (L↔L, mL↔mL, gal↔gal)
- Cross-unit equality (L↔mL, L↔gal, mL↔gal)
- Symmetric, reflexive, transitive properties
- Zero, negative, small, and large value handling
- Null comparison handling
- Cross-category comparison prevention (Volume vs Length/Weight)

---

### Conversion
- Conversion between all volume unit pairs
- Base unit normalization through litres
- Round-trip conversion validation
- Same-unit conversion handling
- Precision maintained within floating-point tolerance

---

### Addition
- Same-unit addition
- Cross-unit addition
- Explicit target unit specification
- Commutativity validation
- Zero and negative value handling
- Large and small magnitude value handling

---

### Enum Validation
- Conversion factor correctness
- convertToBaseUnit() validation
- convertFromBaseUnit() validation
- Enum immutability and thread safety

---

### Architectural Validation
- No changes required in generic `Quantity<U>`
- No duplication of logic
- Full backward compatibility with UC1–UC10
- Volume integrates seamlessly with existing generic system
- Architecture scales linearly for future categories

---

## Result

UC11 confirms that the generic design supports unlimited measurement categories.
Adding a new category requires only a new enum implementing `IMeasurable`.

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC11-VolumeMeasurement/src)
