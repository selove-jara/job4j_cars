CREATE TABLE car_models (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand_id INT REFERENCES car_brands(id) ON DELETE CASCADE
);