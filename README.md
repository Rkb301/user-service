# user-service

A standalone Java 17 profile service with a simple HTTP API and regression tests. Its source is part of the four-repository AI repair bot demonstration.

**Part of the [Microservice AI Repair Bot](https://github.com/Rkb301/microservice-ai-repair-bot) portfolio:** The orchestrator diagnoses error logs, prepares approval-gated repair plans and demonstrates fixes across user, order and payment services. It runs in deterministic local mode by default; actual external integrations require explicit configuration and authorization.

A standalone Java 17 + Maven demo service backed by small in-memory user and profile maps. It listens on port `8081` and uses only JDK HTTP server APIs at runtime.

## Build and run

```powershell
mvn test
mvn -q -DskipTests package
java -cp target/classes com.example.user.UserServiceApp
```

## Endpoints

- `POST /users` creates a sample user and returns HTTP 201.
- `GET /users/{id}` returns a known user or safe JSON with HTTP 404.
- `GET /users/{id}/profile` returns an uppercase known profile or safe JSON with HTTP 404.

Example: `curl http://localhost:8081/users/42/profile`

`application-example.yml` records the local demo port and in-memory mode. `repairbot.yaml` is a safe, non-secret AI repair configuration for use by the separate Python orchestrator; fake Jira/GitHub modes, dry-run behavior, and plan approval are enabled by default.
