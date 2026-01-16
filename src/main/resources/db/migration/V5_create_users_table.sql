CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(55) NOT NULL,
    last_name VARCHAR(55) NOT NULL,
    middle_name VARCHAR(55) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    passport_url VARCHAR() NOT NULL,
    role VARCHAR() NOT NULL
);