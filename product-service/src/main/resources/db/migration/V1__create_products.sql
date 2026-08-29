CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    sku         VARCHAR(50)    NOT NULL,
    name        VARCHAR(120)   NOT NULL,
    description VARCHAR(500),
    price       NUMERIC(12, 2) NOT NULL CHECK (price > 0),
    active      BOOLEAN        NOT NULL,
    created_at  TIMESTAMPTZ    NOT NULL,
    CONSTRAINT uk_products_sku UNIQUE (sku)
);
