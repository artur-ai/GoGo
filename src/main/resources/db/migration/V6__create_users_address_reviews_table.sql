CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,

    first_name  VARCHAR(55) NOT NULL,
    last_name VARCHAR(55) NOT NULL,
    middle_name VARCHAR(55),
    date_of_birth DATE NOT NULL,
    driver_license_id VARCHAR(15) UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,

    review_text TEXT NOT NULL
);

CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,

    country         VARCHAR(50) NOT NULL,
    town            VARCHAR(50) NOT NULL,

    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE
);