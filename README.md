# ImagineBar

[![CI](https://github.com/Izayata/reference-spring-boot-api/actions/workflows/ci.yml/badge.svg)](https://github.com/Izayata/reference-spring-boot-api/actions/workflows/ci.yml)

**Related repository:** [reference-react-typescript](https://github.com/Izayata/reference-react-typescript) — the React/TypeScript frontend for this API.

A Spring Boot REST API backend for a restaurant & bar management system — layered architecture, DDD-style value objects, custom validation, session-based security with Redis rate-limiting, and 674 tests.

## Tech Stack

| Layer | Technology |
|---|---|
| Language / Runtime | Java 17 (via Gradle toolchain) |
| Framework | Spring Boot 3.4.5, Spring MVC |
| Persistence | Spring Data JPA + Hibernate, PostgreSQL |
| Security | Spring Security (session-based, BCrypt) |
| Caching / Rate-limiting | Redis (Lettuce) |
| Validation | Jakarta Bean Validation + Hibernate Validator, Google libphonenumber |
| Testing | JUnit 5, Mockito, Spring `@WebMvcTest` / `@DataJpaTest` |
| Build | Gradle 9.0.0 |

## Getting started

**Prerequisites**
- JDK 17 — resolved automatically by the Gradle toolchain from a locally installed JDK (it does not auto-download one, so make sure one is installed).
- Docker and Docker Compose — for running the full stack (Postgres, Redis, and the app together).

**Environment setup**

Copy `.env.example` to `.env` and fill in real values. Required: `MAIL_USERNAME`, `EMAIL_PASSWORD`, `DB_USERNAME`, `DB_PASSWORD`, `APP_FRONTEND_URL`, `PASSWORD_RESET_TOKEN_EXPIRY_MINUTES`, `PASSWORD_RESET_MAX_REQUESTS_PER_HOUR`, `PASSWORD_RESET_CLEANUP_RATE_MS`, `REDIS_HOST`. Optional, only needed for `bootRun` against Docker-hosted Postgres/Redis: `SPRING_PROFILES_ACTIVE`, `SPRING_DATASOURCE_URL`.

**Run the full stack (recommended)**

```
docker-compose up --build
```

Starts Postgres, Redis, and the app together under the `dev` profile, seeding sample data.

**Run the app directly**

```
./gradlew bootRun
```

**Run the tests**

```
./gradlew test
```

## CI/CD

[`.github/workflows/ci.yml`](.github/workflows/ci.yml) runs on every push and pull request targeting `main`: it checks out the repo, sets up Temurin JDK 17, configures Gradle, then runs `./gradlew build`, which compiles the project and runs the full test suite against the self-contained `test` profile (H2 in-memory database + mocked Redis — no service containers required).

## Documentation

- [`CLAUDE.md`](CLAUDE.md) — project overview, commands, workflow, and architecture notes.
- [`docs/DESIGN.md`](docs/DESIGN.md) — detailed design description with diagrams.
- [`docs/API_ENDPOINTS.md`](docs/API_ENDPOINTS.md) — per-endpoint request/response reference.
- [`docs/BACKEND_ZIP_CITY_LOOKUP.md`](docs/BACKEND_ZIP_CITY_LOOKUP.md) — zip-code → city lookup design.
- [`docs/erd.html`](docs/erd.html) — domain-model entity-relationship diagram.
- [Postman collection](postman/collection/ImagineBar.postman_collection.json) — ready-to-import API collection.

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
- 674 tests across every layer: entity/DTO validation, repository (`@DataJpaTest`), service (Mockito), and controller (`@WebMvcTest`).

## API Reference

A ready-to-import Postman collection covering the full API surface (auth, orders, food/allergen/ingredient catalog, registration, password reset) is included at [`postman/collection/ImagineBar.postman_collection.json`](postman/collection/ImagineBar.postman_collection.json).
