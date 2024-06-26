-- Inserting data into the Product table
INSERT INTO product (id, name, description, price, quantity)
VALUES
    (1, 'Product1', 'Description1', 100.0, 10),
    (2, 'Product2', 'Description2', 200.0, 20),
    (3, 'Product3', 'Description3', 300.0, 30);

-- Inserting data into the Order table
INSERT INTO `orders` (id, order_date, customer_name, address, email, phone_number, status, payment_amount)
VALUES
    (1, '2024-01-01 00:00:00', 'Customer1', 'Address1', 'email1@example.com', '1234567890', 'PENDING', 400.0),
    (2, '2024-02-01 00:00:00', 'Customer2', 'Address2', 'email2@example.com', '0987654321', 'SHIPPING', 600.0),
    (3, '2024-03-01 00:00:00', 'Customer3', 'Address3', 'email3@example.com', '1122334455', 'SHIPPED', 700.0);

-- Inserting data into the Order_Detail table
INSERT INTO order_detail (id, order_id, product_id, quantity)
VALUES
    (1, 1, 1, 2),
    (2, 1, 2, 1),
    (3, 2, 2, 3),
    (4, 3, 1, 1),
    (5, 3, 3, 2);