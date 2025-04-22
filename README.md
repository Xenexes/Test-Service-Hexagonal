# Test Service Hexagonal

A simple Spring Boot/Kotlin service for user account management and administration.  
This project is a playground for experimenting with a strict hexagonal (DDD), multi-module project structure and event-based decoupling.  
It exposes REST endpoints for registration (user & admin), authentication (JWT), activation (email token), and standard CRUD operations—enforcing that only activated accounts may access protected routes.

---

## Overview

Test Service Hexagonal handles user accounts: registration (user & admin), authentication via JWT, activation via email token, and standard CRUD operations on accounts. The service enforces that only activated accounts may access protected endpoints.

## Architecture

- **Kotlin + Spring Boot**
- **Hexagonal architecture** (ports & adapters)
- **JPA / Hibernate** for persistence
- **JWT** for stateless authentication
- **Event publishing** for account lifecycle events
- **multi-module** project

## Prerequisites

- JDK 17+
- Gradle 8+
- Docker (for local Postgres, if desired)

## Application Environment Variables

| Env Var                                         | Default                  | Spring Property                           | Description                                                                                 |
|-------------------------------------------------|--------------------------|-------------------------------------------|---------------------------------------------------------------------------------------------|
| `DB_HOST`                                       | `localhost`              | `spring.datasource.url`                   | Postgres host                                                                                |
| `DB_PORT`                                       | `5432`                   | ―                                         | Postgres port                                                                                |
| `DB_NAME`                                       | `test-service-hexagonal` | ―                                         | Database name                                                                                |
| `DB_USERNAME`                                   | `test`                   | `spring.datasource.username`              | Database user                                                                                |
| `DB_PASSWORD`                                   | `test`                   | `spring.datasource.password`              | Database password                                                                            |
| `MAIL_USERNAME`                                 | (none)                   | `spring.mail.username`                    | SMTP username (e.g. Gmail address)                                                           |
| `MAIL_PASSWORD`                                 | (none)                   | `spring.mail.password`                    | SMTP password (e.g. app password or OAuth token)                                             |
| `TEST_SERVICE_A_EMAIL`                          | (none)                   | `service.service-email`                   | “From” address for outgoing emails                                                           |
| `CREATE_ADMIN_ENABLED`                          | `false`                  | `service.account.creation.admin.enabled`  | On startup, create the admin account if missing                                               |
| `ADMIN_EMAIL`                                   | (none)                   | `service.account.creation.admin.email`    | Initial admin user’s email                                                                   |
| `ADMIN_PASSWORD`                                | (none)                   | `service.account.creation.admin.password` | Initial admin user’s password                                                                |
| `TEST_SERVICE_A_LOGIN_JWT_KEYS_PRIVATE`         | (none)                   | `service.login.jwt.key.private-key`       | PEM-formatted RSA private key for signing JWTs                                               |
| `TEST_SERVICE_A_LOGIN_JWT_KEYS_PUBLIC`          | (none)                   | `service.login.jwt.key.public-key`        | PEM-formatted RSA public key for verifying JWTs                                              |

## REST API

### Authentication

- **POST** `/auth/login`
    - Request: `{ "email": "<email>", "password": "<password>" }`
    - Response: `{ "accessToken": "<jwt>", "tokenType": "Bearer" }`

### Activation

- **POST** `/api/accounts/{accountId}/activate`
    - Request: `{ "token": "<activation-token>" }`
    - Publicly accessible (no JWT required).

### Account Management

All endpoints below require a valid, activated JWT (except the two above).

- **POST** `/api/accounts`

    - Create a new user account
    - Body: `{ "email": "<email>", "password": "<password>" }`

- **POST** `/api/accounts/admin`

    - Create a new admin account
    - Body: `{ "email": "<email>", "password": "<password>" }`

- **GET** `/api/accounts`

    - List accounts
    - Query parameters (optional):
        - `searchTerm`, `email`, `roles` (comma-separated), `active`, `createdAfter`, `createdBefore`

- **PUT** `/api/accounts/{accountId}`

    - Update account details
    - Body: `{ "newEmail": "<email>?", "newPassword": "<password>?" }`

- **DELETE** `/api/accounts/{accountId}`

    - Delete an account

## IntelliJ HTTP Client

A sample HTTP client file (`service.http`) is included for quick testing. Select the environment (e.g., **Local**) from the top-right dropdown. Requests will automatically reuse the JWT token and host/port settings.

### HTTP client environment

You can use the IntelliJ HTTP client environment file in the doc folder:

```json
// .http-client.env.json
{
  "Local": {
    "host": "localhost",
    "port": "8080",
    "authEmail": "admin@example.com",
    "authPassword": "Admin#123",
    "activationToken": "",
    "newUserEmail": "user@example.com",
    "newUserPassword": "User#123",
    "newAdminEmail": "newadmin@example.com",
    "newAdminPassword": "Admin#2025",
    "accountId": ""
  }
}
```

