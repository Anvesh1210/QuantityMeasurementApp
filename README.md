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


# UC13: Centralized Arithmetic Logic to Enforce DRY

## Description

UC13 refactors the internal implementation of arithmetic operations (`add`, `subtract`, `divide`) to eliminate duplication and enforce the **DRY (Don't Repeat Yourself)** principle.

All validation, base-unit conversion, and arithmetic execution logic are centralized into private helper methods. Public APIs remain unchanged, and all UC12 behavior is fully preserved.

This refactoring improves maintainability, readability, and scalability without altering functionality.

---

## Problem in UC12

UC12 implemented arithmetic operations independently, leading to:

- Repeated null checks
- Repeated cross-category validation
- Repeated finiteness validation
- Repeated base-unit conversion logic
- Repeated target-unit handling
- Higher maintenance risk
- Increased bug surface area
- Harder scalability for future operations

---

## Refactoring Strategy

### 1. Centralized Validation Helper

A private method validates:

- Null operands
- Null target units (where required)
- Cross-category mismatches
- Non-finite values
- Division-by-zero scenarios

Validation logic now exists in **one place only**.

---

### 2. Centralized Arithmetic Helper

A private method performs:

- Base-unit conversion for both operands
- Delegated arithmetic execution
- Base-unit result return

All arithmetic operations delegate to this helper.

---

### 3. ArithmeticOperation Enum

An internal enum defines:

- ADD
- SUBTRACT
- DIVIDE

Each constant encapsulates its own computation logic.

Two supported approaches:
- Abstract method implementation
- Lambda-based `DoubleBinaryOperator`

This removes the need for `if-else` or `switch` statements.

---

## Public API (Unchanged)

- `add(other)`
- `add(other, targetUnit)`
- `subtract(other)`
- `subtract(other, targetUnit)`
- `divide(other)`

All methods delegate internally but behave exactly as in UC12.

---

## What Is Covered

### DRY Enforcement

- Validation logic centralized
- Conversion logic centralized
- Arithmetic logic centralized
- No duplicated code across operations

---

### Behavioral Preservation

- All UC12 test cases pass without modification
- Subtraction remains non-commutative
- Division remains non-commutative
- Division-by-zero handling preserved
- Rounding behavior unchanged
- Immutability preserved

---

### Validation Consistency

- Same exception types across operations
- Same error messages across operations
- Same cross-category checks
- Same finiteness checks
- Unified error-handling strategy

---

### Architectural Improvements

- Reduced method size
- Improved readability
- Clear separation of responsibilities
- Single source of truth for arithmetic logic
- Easier onboarding for new developers
- Cleaner extension path for future operations

---

### Scalability

Future operations such as:

- MULTIPLY
- MODULO
- POWER

can be added by:

- Adding new enum constant
- Reusing centralized helper
- No duplication required

---

## Result

UC13 strengthens the architecture by:

- Eliminating duplication
- Improving maintainability
- Reducing bug risk
- Preserving full backward compatibility
- Enforcing SOLID and DRY principles

The system now supports scalable arithmetic operations with a centralized and extensible internal design.

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC13-CentralizedArithmeticLogic/src)
