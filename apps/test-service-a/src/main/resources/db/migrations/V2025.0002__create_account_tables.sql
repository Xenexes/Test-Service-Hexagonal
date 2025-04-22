-- V2025.0002__create_account_tables.sql
CREATE TABLE accounts
(
    id               UUID PRIMARY KEY,
    email            VARCHAR(255) NOT NULL UNIQUE,
    password_hash    VARCHAR(255) NOT NULL,
    activated        BOOLEAN      NOT NULL DEFAULT FALSE,
    activation_token VARCHAR(36)  NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);