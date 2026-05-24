# Puskes

Sistem pencatatan kesehatan ibu dan anak di Puskesmas berbasis Spring Boot.

## Prasyarat

- Java 25 (BellSoft)
- Docker & Docker Compose

## Menjalankan

```bash
# 1. Jalankan infrastruktur (PostgreSQL, Redis, MinIO)
docker compose up -d

# 2. Jalankan migrasi database (sekali)
./mvnw spring-boot:run -Dspring-boot.run.profiles=migrator

# 3. Jalankan aplikasi
./mvnw spring-boot:run
```

Akses Swagger UI di http://localhost:8080/swagger-ui.html

## Profil

| Profil | Flyway | Web Server |
|--------|--------|------------|
| default | disabled | aktif |
| migrator | enabled | nonaktif |

## Pengguna Awal

| Username | Password | Role |
|----------|----------|------|
| admin | rahasia | ADMIN |
| petugas | rahasia | PETUGAS |
| bumil | rahasia | BUMIL |

## Teknologi

- **Java 25** / **Spring Boot 4.0.6**
- **PostgreSQL** — penyimpanan utama
- **Redis (ValKey)** — penyimpanan token autentikasi
- **MinIO** — object storage
- **Flyway** — migrasi database
- **Spring Security** — autentikasi stateless (opaque token)
- **SpringDoc OpenAPI** — dokumentasi API
- **Lombok, MapStruct** — boilerplate & DTO mapping

## Struktur Proyek

```
src/main/java/id/my/agungdh/puskes/
├── config/          # Security, Flyway, OpenAPI
├── controller/      # REST controllers
├── dto/             # Request/response records
├── entity/          # JPA entities
├── mapper/          # MapStruct entity ↔ DTO mappers
├── repository/      # Spring Data JPA repositories
├── security/        # Token filter & info
└── service/         # Business logic
```

## Konvensi

- DTO menggunakan Java **record**.
- Semua entity memiliki kolom audit: `created_at`, `updated_at`, `deleted_at`, `created_by`, `updated_by`, `deleted_by`.
- Soft delete — tidak pernah hapus baris secara fisik.
- Internal ID: `SERIAL` (tidak diekspos); External ID: `UUID` v4.
- Selalu gunakan `./mvnw`, bukan `mvn` biasa.
