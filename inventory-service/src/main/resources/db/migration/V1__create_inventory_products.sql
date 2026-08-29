CREATE TABLE inventory_products
(
    id                 BIGSERIAL PRIMARY KEY,
    sku                VARCHAR(50)  NOT NULL,
    name               VARCHAR(120) NOT NULL,
    available_quantity INTEGER      NOT NULL CHECK (available_quantity >= 0),
    version            BIGINT       NOT NULL DEFAULT 0,
    created_at         TIMESTAMPTZ  NOT NULL,
    updated_at         TIMESTAMPTZ  NOT NULL,
    CONSTRAINT uk_inventory_products_sku UNIQUE (sku)
);
