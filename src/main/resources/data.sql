INSERT INTO customer (id, name) VALUES (1, 'John');
INSERT INTO customer (id, name) VALUES (2, 'Alex');

INSERT INTO `order` (id, customer_id, dispatched) VALUES (1, 1, FALSE);
INSERT INTO `order` (id, customer_id, dispatched) VALUES (2, 1, TRUE);

INSERT INTO order_item (order_id, price_per_item, product, quantity)
VALUES (1, 33.12, 'brick', 10);

INSERT INTO order_item (order_id, price_per_item, product, quantity)
VALUES (2, 11.22, 'brick', 30);



