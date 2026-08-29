ALTER TABLE inventory_products
    DROP CONSTRAINT uk_inventory_products_sku,
    DROP COLUMN sku,
    DROP COLUMN name;

ALTER TABLE inventory_products
    RENAME COLUMN id TO product_id;

ALTER TABLE inventory_products
    ALTER COLUMN product_id DROP DEFAULT;

DROP SEQUENCE IF EXISTS inventory_products_id_seq;
