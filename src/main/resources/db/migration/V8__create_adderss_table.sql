CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    users_id BIGINT NOT NULL users(id) ON DELETE CASCADE

    countries VARCHAR(50) NOT NULL,
    town      VARCHAR(50) NOT NULL
);