CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    users_id BIGINT NOT NULL users(id) ON DELETE CASCADE
    first_name VARCHAR(55) NOT NULL,
    last_name VARCHAR(55) NOT NULL,
    middle_name VARCHAR(55),
    date_of_birth DATE NOT NULL
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    users_id BIGINT NOT NULL users(id) ON DELETE CASCADE

    first_name VARCHAR(55) NOT NULL,
    town       VARCHAR(50) NOT NULL,
    user_age   DATE
);

CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    users_id BIGINT NOT NULL users(id) ON DELETE CASCADE

    countries VARCHAR(50) NOT NULL,
    town      VARCHAR(50) NOT NULL
);