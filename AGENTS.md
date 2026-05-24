# AGENTS.md

## Non-obvious stack details
- **Java 25** (`25.0.2`, BellSoft) — most tooling/docs assume 17 or 21; verify compatibility.
- **Spring Boot 4.0.6** — API differs from 3.x; check 4.x docs, not 3.x.
- **Maven wrapper** only — always use `./mvnw`, never bare `mvn`.
- **Lombok** available — omit handwritten getters/setters/constructors.
- **MapStruct** (`1.6.3`) — annotation processor runs after Lombok; use for DTO mappings.
- **SpringDoc OpenAPI** v3.0.2 — Swagger UI at `/swagger-ui.html` (whitelisted in SecurityConfig).

## Runtime dependencies
- **PostgreSQL**, **Redis** (ValKey in docker-compose), and **MinIO** must be running.
  - Use `docker compose up -d` to start them all.

## Database & Migrations (Flyway)
- Schema managed by Flyway, **not** Hibernate. `ddl-auto` is `validate`.
- Place migration SQL files in `src/main/resources/db/migration/`.
- Two profiles:
  - **default** (web): `flyway.enabled=false` — app runs without running migrations.
  - **migrator**: `flyway.enabled=true`, `web-application-type=none` — runs migrations then exits via `MigratorRunner`.

```bash
./mvnw spring-boot:run                                   # web (no migrations)
./mvnw spring-boot:run -Dspring-boot.run.profiles=migrator  # migration only
```

## Base package
```
id.my.agungdh.puskes
```
All source lives under `src/main/java/id/my/agungdh/puskes/`.

## Security
- All endpoints require authentication except Swagger UI paths (`/swagger-ui/**`, `/v3/api-docs/**`, etc.).
- `SessionCreationPolicy.STATELESS`, CSRF disabled.
- See `config/SecurityConfig.java`.

## Commands
```bash
./mvnw spring-boot:run
```

## Entity conventions
Every entity must have these audit columns (all nullable):

| Column         | Type      | Description                  |
|---------------|-----------|------------------------------|
| `created_at`  | `BIGINT`  | Unix epoch (seconds)         |
| `updated_at`  | `BIGINT`  | Unix epoch (seconds)         |
| `deleted_at`  | `BIGINT`  | Unix epoch (seconds) — soft delete |
| `created_by`  | `VARCHAR` | Username who created         |
| `updated_by`  | `VARCHAR` | Username who last updated    |
| `deleted_by`  | `VARCHAR` | Username who deleted         |

- Soft delete: never hard-delete rows; set `deleted_at` instead.
- **Partial unique indexes**: unique constraints only apply when `deleted_at IS NULL`. For example, a `UNIQUE (email) WHERE deleted_at IS NULL`.

## ID conventions
- **Internal**: `SERIAL` (PostgreSQL-only). Used for PK and FK relationships. **Never exposed to clients.**
- **External**: `UUID` v4 with a hash index. Exposed in API responses / DTOs. Generated via `gen_random_uuid()` in PostgreSQL, or `UUID.randomUUID()` in Java.

## DTO conventions
- DTOs use Java **records**, not classes.
- Use **MapStruct** mappers for entity ↔ DTO conversions.
- Place mappers in `mapper/` package (e.g. `id.my.agungdh.puskes.mapper`).
