CREATE DATABASE Quan_ly_ban_hang;
USE Quan_ly_ban_hang;

-- Bảng sản phẩm
CREATE TABLE products(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL
);

-- Danh sách khách hàng
CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20)
);

-- Thông tin đơn hàng
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DOUBLE,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Chi tiết đơn hàng
CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    unit_price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);