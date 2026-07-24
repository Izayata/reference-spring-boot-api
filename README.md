# ImagineBar

A Spring Boot REST API backend for a restaurant & bar management system — layered architecture, DDD-style value objects, custom validation, session-based security with Redis rate-limiting, and 658 tests.

## Tech Stack

| Layer | Technology |
|---|---|
| Language / Runtime | Java 17 |
| Framework | Spring Boot 3.4.5, Spring MVC |
| Persistence | Spring Data JPA + Hibernate, PostgreSQL |
| Security | Spring Security (session-based, BCrypt) |
| Caching / Rate-limiting | Redis (Lettuce) |
| Validation | Jakarta Bean Validation + Hibernate Validator, Google libphonenumber |
| Testing | JUnit 5, Mockito, Spring `@WebMvcTest` / `@DataJpaTest` |
| Build | Gradle 8.5 |

## Highlights

**Architecture**
- Strict layering: `Controller → Service → Repository → Entity`, with dedicated converters between JPA entities and API DTOs.
- Domain fields modeled as individual `@Embeddable` value objects (`ZipCode`, `FoodName`, `Price`, etc.) enforcing invariants at the type level.
- Event-driven side effects (email dispatch on registration, order confirmation, password change) via Spring's `@TransactionalEventListener`.

**Security**
- Session-based auth with BCrypt password hashing and CSRF protection.
- Redis-backed atomic rate limiting on login attempts, password-reset requests, and account-enumeration endpoints.
- Role-based authorization (`ROLE_USER` / `ROLE_ADMIN`) and hardened HTTP security headers.

**Validation**
- Custom JSR-380 constraint annotations and validators for cross-field and domain-specific rules (phone number normalization, password-confirmation matching, forbidden-value checks).

**Testing**
- 658 tests across every layer: entity/DTO validation, repository (`@DataJpaTest`), service (Mockito), and controller (`@WebMvcTest`).

## API Reference

A ready-to-import Postman collection covering the full API surface (auth, orders, food/allergen/ingredient catalog, registration, password reset) is included at [`postman/collection/ImagineBar.postman_collection.json`](postman/collection/ImagineBar.postman_collection.json).
