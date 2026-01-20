CREATE TABLE countries
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE car_countries
(
    car_id     BIGINT NOT NULL,
    country_id BIGINT NOT NULL,
    PRIMARY KEY (car_id, country_id),
    FOREIGN KEY (car_id) REFERENCES cars (id) ON DELETE CASCADE,
    FOREIGN KEY (country_id) REFERENCES countries (id) ON DELETE CASCADE
);


INSERT INTO countries (name)
VALUES ('Ukraine'),
       ('Poland'),
       ('Germany'),
       ('France'),
       ('Italy'),
       ('Spain'),
       ('Netherlands'),
       ('Japan');

INSERT INTO car_countries (car_id, country_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (10, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (3, 3),
       (5, 3),
       (6, 3),
       (7, 3),
       (9, 3),
       (3, 4),
       (5, 4),
       (6, 4),
       (7, 4),
       (10, 4),
       (11, 4),
       (7, 5),
       (8, 5),
       (10, 5),
       (9, 5),
       (11, 5),
       (8, 2),
       (8, 3),
       (8, 4),
       (12, 1),
       (12, 2),
       (12, 3),
       (12, 4),
       (12, 5),
       (12, 6),
       (4, 6),
       (6, 6),
       (11, 6),
       (7, 7),
       (3, 7),
       (9, 7),
       (11, 8),
       (7, 8),
       (10, 8);