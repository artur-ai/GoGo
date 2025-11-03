CREATE TABLE IF NOT EXISTS cars (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INTEGER NOT NULL,
    fuel_type VARCHAR(50) NOT NULL,
    engine VARCHAR(50) NOT NULL,
    price_per_minute NUMERIC(19,2) NOT NULL,
    price_per_day NUMERIC(19,2) NOT NULL,
    insurance_price NUMERIC(19,2) NOT NULL DEFAULT 0,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
