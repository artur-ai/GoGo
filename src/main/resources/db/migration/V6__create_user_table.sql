CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    users_id BIGINT NOT NULL users(id) ON DELETE CASCADE
    first_name VARCHAR(55) NOT NULL,
    last_name VARCHAR(55) NOT NULL,
    middle_name VARCHAR(55),
    date_of_birth DATE NOT NULL
);