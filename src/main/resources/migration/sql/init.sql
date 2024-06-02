CREATE TABLE product (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     title VARCHAR(255) NOT NULL,
     price DOUBLE NOT NULL,
     description TEXT,
     category VARCHAR(255),
     image_link VARCHAR(255),
     image_base64 TEXT,
     rating_rate DOUBLE,
     rating_count INT
);

CREATE TABLE `orders` (
     `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
     `order_date` DATETIME NOT NULL,
     `customer_name` VARCHAR(255) NOT NULL,
     `address` VARCHAR(255) NOT NULL,
     `email` VARCHAR(255) NOT NULL,
     `phone_number` VARCHAR(255) NOT NULL,
     `status` VARCHAR(255) NOT NULL,
     `payment_amount` DECIMAL(10, 2) NOT NULL,
     PRIMARY KEY (`id`)
);

CREATE TABLE `order_detail` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
   `order_id` BIGINT(20) NOT NULL,
   `product_id` BIGINT(20) NOT NULL,
   `quantity` INT NOT NULL,
   PRIMARY KEY (`id`)
);

-- add index for table
CREATE INDEX idx_order_id ON `orders`(`id`);
CREATE INDEX idx_order_customer_name_id ON `orders` (`customer_name`);
CREATE INDEX idx_product_id ON `product` (`id`);
CREATE INDEX idx_product_price ON `product` (`price`);