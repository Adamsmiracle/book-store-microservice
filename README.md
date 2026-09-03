 # Book Store Microservice

 A backend service for managing books in an online bookstore. The service is designed to run independently and expose its functionality through a REST API.

 ## Responsibilities

 - Create, retrieve, update, and delete books
 - Store book details such as title, author, description, price, and inventory
 - Validate incoming requests
 - Return consistent JSON responses and HTTP status codes

 ## Getting started

 ### Prerequisites

 - Git
 - Docker and Docker Compose, or the runtime required by the application
 - A database configured for the service
 - [Task](https://taskfile.dev/) (`task`), a task runner used for project commands

 ### Run locally

 1. Clone the repository:

	 ```bash
	 git clone <repository-url>
	 cd book-store-microservice
	 ```

 2. Configure the required environment variables in a local `.env` file.

 3. Start the service using the project's configured development command or Docker Compose.

 4. Verify that the application is running by calling its health endpoint.

 ## API

 Typical book endpoints include:

 | Method | Endpoint | Description |
 | --- | --- | --- |
 | `GET` | `/books` | List books |
 | `GET` | `/books/:id` | Get a book |
 | `POST` | `/books` | Create a book |
 | `PUT` | `/books/:id` | Update a book |
 | `DELETE` | `/books/:id` | Delete a book |

 Request and response formats, authentication, and the exact port are defined by the application configuration.

 ## Container management (Portainer)

 [Portainer](https://www.portainer.io/) gives you a web UI for managing the Docker containers used by this project (e.g. `catalog-db`) instead of the CLI.

 **Local URL:** [https://localhost:9443](https://localhost:9443)

 ### Start Portainer

 ```bash
 docker run -d \
	 --name portainer \
	 -p 9443:9443 \
	 -p 8000:8000 \
	 -v /var/run/docker.sock:/var/run/docker.sock \
	 -v portainer_data:/data \
	 --restart unless-stopped \
	 portainer/portainer-ce:latest
 ```

 If a `portainer` container already exists (stopped), start it instead of creating a new one:

 ```bash
 docker start portainer
 ```

 ### First-time admin setup

 1. Open **https://localhost:9443** (self-signed cert — accept the browser warning).
 2. Within 5 minutes of the container starting, create an admin username and password.
	 - If it asks for a **setup token**, retrieve it from the container logs:

	   ```bash
	   docker logs portainer 2>&1 | grep setup_token | tail -1
	   ```
 3. If the 5-minute window expires before you finish, restart the container and try again:

	 ```bash
	 docker restart portainer
	 ```
 4. Select **Docker** as the environment type and connect via the local socket (already mounted above) — Portainer will auto-detect it.

 ### Managing containers

 Under **Environments → local → Containers** you can:

 - **Start / stop / restart** a container from the row actions
 - View **Logs** (live-tailed)
 - Open a **Console** shell inside a running container
 - View live **Stats** (CPU/memory)
 - **Inspect** full config, env vars, mounts, and networks

 For compose-based services, add `deployment/docker-compose/infra.yml` as a **Stack** in Portainer to pull, redeploy, or tear down the whole stack from the UI instead of the CLI.

 ## Configuration

 Keep secrets out of source control. Common configuration values include:

 - Database connection details
 - Application port
 - Logging level
 - Authentication settings

 ## Development

 Before opening a pull request:

 - Run the formatter (see [Code formatting](#code-formatting))
 - Run the full test suite
 - Update API documentation when behavior changes
 - Add tests for new functionality

 ### Code formatting

 Java code is formatted with [Spotless](https://github.com/diffplug/spotless) using Google Java Format. It's configured in both the root `pom.xml` and `catalog-service/pom.xml`, and is bound to the `check` goal, so `./mvnw verify` fails if any file isn't formatted.

 Check formatting:

 ```bash
 ./mvnw spotless:check
 ```

 Auto-format all files:

 ```bash
 ./mvnw spotless:apply
 ```

 Run against a single module, e.g. `catalog-service`:

 ```bash
 ./mvnw -pl catalog-service spotless:apply
 ```

 ## Building a container image

 Each service uses Spring Boot's Cloud Native Buildpacks support to build a container image without a Dockerfile.

 Build the `catalog-service` image:

 ```bash
 ./mvnw -pl catalog-service spring-boot:build-image -DskipTests
 ```

 ## License

 Add the project's license information here.
