# AGENTS.md

## Non-obvious stack details
- **Java 25** (`25.0.2`, BellSoft) — most tooling/docs assume 17 or 21; verify compatibility.
- **Spring Boot 4.0.6** — API differs from 3.x; check 4.x docs, not 3.x.
- **Maven wrapper** only — always use `./mvnw`, never bare `mvn`.
- **Lombok** available — omit handwritten getters/setters/constructors.
- **MapStruct** (`1.6.3`) — annotation processor runs after Lombok; use for DTO mappings.
- **SpringDoc OpenAPI** v3.0.2 — Swagger UI at `/swagger-ui.html` (whitelisted in SecurityConfig).

## Runtime dependencies
- **PostgreSQL**, **Redis** (ValKey in docker-compose), and **MinIO** must be running, even for `@SpringBootTest` context-load tests.
  - Use `docker compose up -d` to start them all.
  - Migrate tests to `@DataJpaTest` / `@WebMvcTest` slices (or use testcontainers) to avoid this.

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
./mvnw test
./mvnw test -Dtest=SomeTestClass
```
