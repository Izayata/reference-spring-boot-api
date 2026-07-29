# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

"ImagineBar" — a Spring Boot 3.4.5 / Java 17 / Gradle REST API for a restaurant/bar food-ordering
system: menu (foods, allergens, ingredients), customers, orders, authentication/registration, and
password reset. Root package: `bar.imagine.demo`.

## Commands

- Build: `./gradlew build`
- Run all tests: `./gradlew test`
- Run a single test class: `./gradlew test --tests "bar.imagine.demo.service.FoodServiceTest"`
- Run a single test method: `./gradlew test --tests "bar.imagine.demo.service.FoodServiceTest.methodName"`
- Run locally: `./gradlew bootRun`
  - The custom `bootRun` block in `build.gradle` auto-loads a `.env` file from the project root if
    present — copy `.env.example` to `.env` and fill in real values first.
- Full stack via Docker: `docker-compose up --build` — starts postgres + redis + app, with the app
  running under `SPRING_PROFILES_ACTIVE=dev` (seeds `data.sql`).
- Manual API testing: Postman collection at `postman/collection/ImagineBar.postman_collection.json`;
  for exact request/response JSON shapes per endpoint, see `docs/API_ENDPOINTS.md`.
- CI: `.github/workflows/ci.yml` runs `./gradlew build` on every push/PR to `main`. The `test`
  profile is fully self-contained (H2 + mocked Redis), so no service containers are needed.

Required env vars (see `.env.example`): mail credentials (`MAIL_USERNAME`, `EMAIL_PASSWORD` — a
Gmail app password), `DB_USERNAME`/`DB_PASSWORD`, `APP_FRONTEND_URL` (drives CORS and password-reset
links), password-reset timing (`PASSWORD_RESET_TOKEN_EXPIRY_MINUTES`,
`PASSWORD_RESET_MAX_REQUESTS_PER_HOUR`, `PASSWORD_RESET_CLEANUP_RATE_MS`), and `REDIS_HOST`.

## Workflow

Every task must follow this sequence:

1. Check out `main` if not already on it.
2. Pull the latest changes from remote `main`.
3. Create a new branch off `main`, named to reflect the task (e.g. `feature/implementing-mfa`,
   `fix/order-total-rounding`).
4. Complete the task. Discuss any non-obvious or uneasy decisions with the user, and explain the
   reasoning behind the chosen solution.
5. Commit the task-related changes.
6. Push the commit(s).
7. Open a PR with a proper title and description.

## Profiles

- **default** (`application.yml`): Postgres, `ddl-auto: validate`, no seeding — production-shaped,
  requires all env vars to be set.
- **dev** (`application-dev.yml`): `ddl-auto: create-drop`, seeds `src/main/resources/data.sql`,
  verbose Spring logging. Used by `docker-compose.yml`.
- **test** (`application-test.yml`): H2 in-memory database, used by the test suite. Redis is never
  actually contacted in tests — see below.

## Architecture

For a full design description with diagrams (domain model ERD, sequence diagrams for auth/order/
email flows), see `docs/DESIGN.md`.

**Layering**: Controller → Service → Repository, with `converter/` classes translating between JPA
entities (`data/`) and API DTOs (`dto/`). Controllers and services never return entities directly.

**Value-object modeling convention**: most entity fields are individually wrapped `@Embeddable`
value classes under `data/<entity>/` (e.g. `FoodName`, `Price`, `Description`) rather than plain
primitives. Each one pairs with a `Utils` class under `util/.../XxxUtils.java` holding its validation
constants/messages, and a matching DTO under `dto/.../XxxDTO.java` for API I/O. Adding or changing a
field means touching all three. Reference example: `data/food/FoodName.java` +
`util/foodUtils/FoodNameUtils.java` + `dto/food/FoodNameDTO.java`, wired together in
`converter/FoodConverter.java`.

**Request validation**: request bodies live in `request/data/`, validated with `@Valid` plus custom
annotations in `validation/` (`NoForbiddenValue`, `NotEmptyList`, `ValidPhoneNumber`,
`NewPasswordMatchesConfirmNewPassword`, `NewPasswordMatchesCurrentPassword`), each backed by its own
`*Validator` class.

**Centralized error handling**: `exception/GlobalExceptionHandler.java` (`@ControllerAdvice`) is the
single place mapping both custom exceptions (`exception/exceptions/`) and framework/JPA exceptions
to consistent JSON error responses (`{"error": ...}` or a field→messages map for validation
failures). Extend this rather than handling errors ad hoc in controllers. Note
`NoResourceFoundException` (unmapped/mistyped URLs) has its own handler returning 404 — without it,
this being a global `@ControllerAdvice` means any unroutable path falls into the generic `Exception`
catch-all and misreports as 500.

**Health check**: `spring-boot-starter-actuator` exposes `GET /actuator/health`.
`management.health.mail.enabled=false` excludes the mail indicator (which would otherwise open a
live SMTP connection per check) from the aggregate status — email delivery is already async via the
outbox, so a transient SMTP outage shouldn't read as the whole app being down.

**Transactional outbox email**: services write an `EmailOutbox` row (`REGISTRATION_SUCCESS`,
`PASSWORD_RESET`, `PASSWORD_CHANGED`, `ORDER_CONFIRMATION`) inside the same `@Transactional`
business method as the domain change, so the row commits atomically with the business state.
`service/EmailOutboxWorker.java` polls pending rows every `app.email-outbox.poll-rate-ms`
(default 5s) and sends each via `EmailOutboxService`; failed sends retry up to 5 attempts before
being marked `FAILED`, and `FAILED` rows are bulk-requeued daily
(`app.email-outbox.requeue-cron`). This replaced an earlier `@TransactionalEventListener`-based
design whose synchronous, un-persisted sends could hang the request thread or be lost on a crash.

**Security** (`config/SecurityConfig.java`): session-based form login at `/login`, BCrypt passwords,
CORS locked to `app.frontend.url`, CSP + frame-deny headers, and a deliberately path-scoped
CSRF-exemption list (public GETs, guest checkout, password reset, etc.) — read the inline comments
there before changing any endpoint's public/authenticated status, since the reasoning per exemption
is non-obvious (e.g. guest order creation is CSRF-exempt but authenticated order creation is not).

**Redis-backed rate limiting**: `service/RedisService.java` exposes an atomic Lua incr+TTL script;
`config/LoginRateLimitFilter.java` uses it to cap login attempts per username and is registered
before `UsernamePasswordAuthenticationFilter`. It increments on every `POST /login` attempt (not
just failures) because check-then-increment as two steps allowed concurrent requests to race past
the limit. In tests, Redis is never actually contacted —
`src/test/java/bar/imagine/demo/config/TestRedisConfig.java` supplies a mocked
`RedisConnectionFactory` so `RedisAutoConfiguration` backs off.

**Testing conventions**: service-layer unit tests use JUnit 5 + Mockito
(`@ExtendWith(MockitoExtension.class)`, `@Mock`/`@InjectMocks`); the `test` profile runs against H2
in-memory instead of Postgres.
