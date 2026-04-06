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

## Security (JWT + Google OAuth2)

The app now protects all `/api/v1/quantities/**` endpoints with Spring Security.
Access requires an authenticated token with role `USER` or `ADMIN`.

### JWT Login

- Registration endpoint: `POST /api/v1/auth/register`
- Registration request:
  ```json
  {
    "name": "New User",
    "email": "newuser@example.com",
    "password": "Password@123",
    "mobileNumber": "+15555550111"
  }
  ```
- Registration response returns `accessToken` (JWT) with HTTP `201 Created`.

- Endpoint: `POST /api/v1/auth/login`
- Request:
  ```json
  {
    "email": "appuser@example.com",
    "password": "password123"
  }
  ```
- Response returns `accessToken` (JWT). Use it as:
  `Authorization: Bearer <accessToken>`

Default development credentials are configured in `application.properties`:

- `app.auth.name=App User`
- `app.auth.email=appuser@example.com`
- `app.auth.password=password123`
- `app.auth.mobile-number=+15555550123`

### Google OAuth2 Login

Set Google client credentials (recommended via environment variables):

- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`

Then configure these properties (currently commented in `application.properties`):

- `spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}`
- `spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}`
- `spring.security.oauth2.client.registration.google.scope=openid,profile,email`

Start login from:

- `/oauth2/authorization/google`

On successful login, the app redirects to:

- `app.oauth2.authorized-redirect-uri`

with a JWT token in the query parameter `token`.

---


# UC14: Temperature Measurement with Selective Arithmetic Support

## Description

UC14 introduces **Temperature** as a new measurement category while addressing a fundamental design limitation: not all measurement types support the same arithmetic operations.

Unlike length, weight, and volume, temperature:

- Supports **equality comparison**
- Supports **unit conversion**
- Does **not** support addition, subtraction, or division in a meaningful way for absolute temperatures

To accommodate this, the `IMeasurable` interface is refactored to allow **optional arithmetic support** using default methods and capability validation.

This ensures the system remains scalable, type-safe, and SOLID-compliant.

---

## Key Architectural Changes

### 1. IMeasurable Refactoring

- Introduced default methods for operation validation
- Added capability-based design for arithmetic support
- Default behavior allows arithmetic (backward compatible)
- Categories like Temperature override to restrict unsupported operations

This enforces **Interface Segregation Principle (ISP)** without breaking existing units.

---

### 2. TemperatureUnit Enum

Supports:

- CELSIUS
- FAHRENHEIT
- KELVIN

Implements:

- Accurate non-linear conversion formulas
- Base-unit normalization
- Operation restriction through overridden validation methods

Arithmetic operations throw `UnsupportedOperationException` with clear messages.

---

### 3. Quantity Class Enhancement

- Validates operation support before executing arithmetic
- Maintains immutability
- Maintains cross-category type safety
- Preserves all UC1–UC13 behavior

No structural redesign required.

---

## What Is Covered

### Temperature Equality

- Same-unit equality
- Cross-unit equality (C ↔ F ↔ K)
- Symmetric property
- Reflexive property
- Transitive property
- Epsilon-based floating-point tolerance

---

### Temperature Conversion

- Celsius ↔ Fahrenheit
- Celsius ↔ Kelvin
- Fahrenheit ↔ Kelvin
- Round-trip conversion validation
- Absolute zero handling
- Edge case: -40°C = -40°F
- Large and small value handling
- Same-unit conversion

---

### Unsupported Operations

- Addition throws `UnsupportedOperationException`
- Subtraction throws `UnsupportedOperationException`
- Division throws `UnsupportedOperationException`
- Clear, category-specific error messages
- Capability validation executed before arithmetic

---

### Cross-Category Type Safety

- Temperature cannot be compared with:
  - Length
  - Weight
  - Volume
- Compile-time generic enforcement
- Runtime category validation via `unit.getClass()`

---

### Interface Evolution Validation

- Default methods preserve backward compatibility
- Length, Weight, Volume require no changes
- Existing UC1–UC13 tests pass without modification
- Non-breaking interface evolution

---

## Design Principles Enforced

- Interface Segregation Principle (ISP)
- Open/Closed Principle (OCP)
- DRY principle maintained
- Capability-based design
- Polymorphic validation
- Clear exception semantics
- Backward-compatible refactoring

---

## Architectural Result

The system now supports:

- Linear measurement categories (Length, Weight, Volume) with full arithmetic
- Non-linear measurement category (Temperature) with selective support
- Optional operation validation at runtime
- Scalable structure for future constrained categories

UC14 proves the generic architecture can accommodate categories with fundamentally different operational constraints without structural redesign.

---

## Code Reference

[View Source Code](https://github.com/Anvesh1210/QuantityMeasurementApp/tree/feature/UC14-TemperatureMeasurement/src)
