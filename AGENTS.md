# AGENTS.md

## Non-obvious stack details
- **Java 25** (`25.0.2`, BellSoft) — most tooling/docs assume 17 or 21; verify compatibility.
- **Spring Boot 4.0.6** — API differs from 3.x; check 4.x docs, not 3.x.
- **Maven wrapper** only — always use `./mvnw`, never bare `mvn`.
- **Lombok** available — omit handwritten getters/setters/constructors.
- **SpringDoc OpenAPI** v3.0.2 included — Swagger UI auto-served at `/swagger-ui.html`.

## Dependencies required at runtime
- **PostgreSQL** and **Redis** must be running, even for `@SpringBootTest` context-load tests.
  - Migrate to `@DataJpaTest` / `@WebMvcTest` slices (or configure testcontainers) to avoid this.

## Base package
```
id.my.agungdh.puskes
```
All source lives under `src/main/java/id/my/agungdh/puskes/`.

## Commands
```bash
./mvnw spring-boot:run
./mvnw test
./mvnw test -Dtest=SomeTestClass
```
