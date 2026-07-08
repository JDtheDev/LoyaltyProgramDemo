INSERT OR IGNORE INTO rewards (code, name, cost) VALUES ('Discount10', '10% off your next purchase', 100);
INSERT OR IGNORE INTO rewards (code, name, cost) VALUES ('Discount25', '25% off your next purchase', 250);
INSERT OR IGNORE INTO rewards (code, name, cost) VALUES ('TShirt', 'Free T-Shirt', 500);
INSERT OR IGNORE INTO rewards (code, name, cost) VALUES ('GiftCard100', '$100 gift card', 1000);

INSERT OR IGNORE INTO customers (external_id, display_name) VALUES ('carlos', 'Carlos Sainz');
INSERT OR IGNORE INTO customers (external_id, display_name) VALUES ('charles', 'Charles Leclerc');
INSERT OR IGNORE INTO customers (external_id, display_name) VALUES ('kimi', 'Kimi Antonelli');
INSERT OR IGNORE INTO customers (external_id, display_name) VALUES ('lewis', 'Lewis Hamilton');
INSERT OR IGNORE INTO customers (external_id, display_name) VALUES ('max', 'Max Verstappen');

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-1001', 120.50, CAST(strftime('%s','now','-5 days') AS INTEGER) * 1000, 120, 0 FROM customers WHERE external_id = 'carlos';

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-1002', 75.00, CAST(strftime('%s','now','-40 days') AS INTEGER) * 1000, 75, 0 FROM customers WHERE external_id = 'kimi';

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-1003', 300.00, CAST(strftime('%s','now','-400 days') AS INTEGER) * 1000, 300, 0 FROM customers WHERE external_id = 'lewis';

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-1004', 1044.00, CAST(strftime('%s','now','-10 days') AS INTEGER) * 1000, 1044, 0 FROM customers WHERE external_id = 'lewis';

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-2001', 45.00, CAST(strftime('%s','now','-2 days') AS INTEGER) * 1000, 45, 0 FROM customers WHERE external_id = 'max';

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-2002', 60.25, CAST(strftime('%s','now','-60 days') AS INTEGER) * 1000, 60, 0 FROM customers WHERE external_id = 'carlos';

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-3001', 1200.00, CAST(strftime('%s','now','-10 days') AS INTEGER) * 1000, 1200, 0 FROM customers WHERE external_id = 'charles';

INSERT OR IGNORE INTO purchases (customer_id, purchase_reference, amount, purchased_at, points, refunded)
SELECT id, 'order-3002', 20000.00, CAST(strftime('%s','now','-100 days') AS INTEGER) * 1000, 20000, 0 FROM customers WHERE external_id = 'max';
