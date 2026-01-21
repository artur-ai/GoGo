CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    users_id BIGINT NOT NULL users(id) ON DELETE CASCADE

    first_name VARCHAR(55) NOT NULL,
    town       VARCHAR(50) NOT NULL,
    user_age   DATE
);