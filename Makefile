build: ## build all or c=<name> containers images
	docker compose -f compose.yaml build

up: ## Start all or c=<name> containers in foreground
	docker compose -f compose.yaml up $(c)

start: ## Start all or c=<name> containers in background
	docker compose -f compose.yaml up -d $(c)

stop: ## Stop all or c=<name> containers
	docker compose -f compose.yaml stop $(c)

status: ## Show status of containers
	docker compose -f compose.yaml ps

restart: ## Restart all or c=<name> containers
	docker compose -f compose.yaml stop $(c)
	docker compose -f compose.yaml up $(c) -d

logs: ## Show logs for all or c=<name> containers
	docker compose -f compose.yaml logs --tail=100 -f $(c)

clean: ## Clean all data
	docker compose -f compose.yaml down
