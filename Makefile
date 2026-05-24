.PHONY: help dev migrate migrate-refresh docker-up docker-down docker-reset

help: ## List available commands
	@printf "Usage:\n  make <target>\n\n"
	@printf "Targets:\n"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "  \033[36m%-18s\033[0m %s\n", $$1, $$2}'

dev: ## Run app (default profile, web mode)
	@./mvnw spring-boot:run

migrate: ## Run pending migrations only
	@./mvnw spring-boot:run -Dspring-boot.run.profiles=migrator

migrate-refresh: ## Clean database + re-run all migrations
	@./mvnw spring-boot:run -Dspring-boot.run.profiles=migrator -Dspring-boot.run.arguments="--app.clean-on-start=true"

docker-up: ## Start PostgreSQL, ValKey (Redis), MinIO, Adminer
	@docker compose up -d

docker-down: ## Stop all services
	@docker compose down

docker-reset: ## Destroy and recreate all Docker services + re-migrate
	@docker compose down -v
	@docker compose up -d
	@sleep 3
	@./mvnw spring-boot:run -Dspring-boot.run.profiles=migrator -Dspring-boot.run.arguments="--app.clean-on-start=true"
