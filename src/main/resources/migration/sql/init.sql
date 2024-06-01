CREATE TABLE `product` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(255) NOT NULL,
   `description` TEXT,
   `price` DECIMAL(10, 2) NOT NULL,
   `quantity` INT NOT NULL,
   PRIMARY KEY (`id`)
);

-- add constraint to name is not allowed duplicate
ALTER TABLE `product` ADD CONSTRAINT `unique_name` UNIQUE (`name`);

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