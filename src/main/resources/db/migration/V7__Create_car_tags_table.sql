CREATE TABLE car_tags (
    car_id INTEGER REFERENCES cars(id) ON DELETE CASCADE,
    tag_id INTEGER REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (car_id, tag_id)
);