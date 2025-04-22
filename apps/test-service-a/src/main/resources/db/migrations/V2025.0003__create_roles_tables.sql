-- V2025.0003__create_roles_tables.sql
CREATE TABLE roles (
                       id VARCHAR(50) PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE account_roles (
                               account_id UUID NOT NULL,
                               role_id VARCHAR(50) NOT NULL,
                               PRIMARY KEY (account_id, role_id),
                               CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE,
                               CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);
