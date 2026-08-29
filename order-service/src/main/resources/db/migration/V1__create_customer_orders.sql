CREATE TABLE customer_orders
(
    id             BIGSERIAL PRIMARY KEY,
    product_id     BIGINT       NOT NULL,
    quantity       INTEGER      NOT NULL CHECK (quantity > 0),
    customer_email VARCHAR(255) NOT NULL,
    status         VARCHAR(30)  NOT NULL,
    created_at     TIMESTAMPTZ  NOT NULL
);

CREATE INDEX idx_customer_orders_created_at
    ON customer_orders (created_at DESC);
