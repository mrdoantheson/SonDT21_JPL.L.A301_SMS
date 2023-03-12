CREATE DATABASE SMS;

USE SMS;

CREATE TABLE Customer
(
    customer_id   INT PRIMARY KEY AUTO_INCREMENT,
    customer_name NVARCHAR(100) NOT NULL
);

CREATE TABLE Employee
(
    employee_id   INT PRIMARY KEY AUTO_INCREMENT,
    employee_name NVARCHAR(100) NOT NULL,
    salary        DECIMAL(15, 2),
    supervisor_id INT,
    FOREIGN KEY (supervisor_id) REFERENCES Employee (employee_id)
);

CREATE TABLE Product
(
    product_id   INT PRIMARY KEY AUTO_INCREMENT,
    product_name NVARCHAR(100) NOT NULL,
    list_price   DECIMAL(15, 2)
);

CREATE TABLE `Order`
(
    order_id    INT AUTO_INCREMENT,
    order_date  DATE NOT NULL,
    customer_id INT  NOT NULL,
    employee_id INT  NOT NULL,
    total       DECIMAL(15, 2),
    PRIMARY KEY (order_id),
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id),
    FOREIGN KEY (employee_id) REFERENCES Employee (employee_id)
);

CREATE TABLE LineItem
(
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    quantity   INT,
    price      DECIMAL(15, 2),
    FOREIGN KEY (order_id) REFERENCES `Order` (order_id) ,
    FOREIGN KEY (product_id) REFERENCES Product (product_id)
);

INSERT INTO Customer(customer_name) VALUES ('Trần Duy Hiệp');
INSERT INTO Customer(customer_name) VALUES ('Cao Bá Quyền');
INSERT INTO Customer(customer_name) VALUES ('Nguyễn Ngọc Dũng');

ALTER TABLE LineItem ADD PRIMARY KEY (order_id, product_id);

INSERT INTO Employee (employee_name, salary, supervisor_id) VALUES ('Trần Đức Hiệp', 1000, NULL);
INSERT INTO Employee (employee_name, salary, supervisor_id) VALUES ('Trần Xuân Mạnh', 1500, 1);
INSERT INTO Employee (employee_name, salary, supervisor_id) VALUES ('Lương Quốc Trung', 2000, 1);
INSERT INTO Employee (employee_name, salary, supervisor_id) VALUES ('Đỗ Trung Kiên', 2500, 2);

INSERT INTO Product (product_name, list_price) VALUES ('Sữa chua', 5);
INSERT INTO Product (product_name, list_price) VALUES ('Kem đánh răng', 10);
INSERT INTO Product (product_name, list_price) VALUES ('Bao cao su', 15);
INSERT INTO Product (product_name, list_price) VALUES ('Thuốc lá', 20);

INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 1, 1, 0);
INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 1, 2, 0);
INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 1, 2, 0);
INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 2, 3, 0);
INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 2, 1, 0);
INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 5, 1, 0);
INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 5, 3, 0);
INSERT INTO `Order` (order_date, customer_id, employee_id, total) VALUES (CURDATE(), 6, 1, 0);


SELECT c.customer_id, c.customer_name FROM Customer c
                                               INNER JOIN `Order` o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name;

SELECT o.order_id, o.order_date, o.customer_id, o.employee_id, o.total
FROM `Order` o
WHERE o.customer_id = 1;

SELECT i.order_id, i.product_id, i.quantity, i.price
FROM LineItem i WHERE I.order_id = ?;

INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (1, 1, 2, 1000);
INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (1, 2, 3, 6000);
INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (5, 2, 6, 2000);
INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (6, 2, 6, 2500);

SELECT SUM(I.price * i.quantity) AS total_price
FROM LineItem i WHERE I.order_id = 6
GROUP BY i.order_id;

ALTER TABLE LineItem ADD CONSTRAINT FOREIGN KEY (order_id) REFERENCES `Order` (order_id);
ALTER TABLE LineItem ADD CONSTRAINT FOREIGN KEY (product_id) REFERENCES Product (product_id);

ALTER TABLE `Order` ADD FOREIGN KEY (customer_id) REFERENCES Customer (customer_id);
ALTER TABLE `Order` ADD FOREIGN KEY (employee_id) REFERENCES Employee (employee_id);

SELECT  * FROM `Order`;

UPDATE `Order` SET total =
                       (SELECT SUM(I.price * i.quantity) AS total_price
                        FROM LineItem i WHERE I.order_id = ?
                        GROUP BY i.order_id
                       ) WHERE order_id = ?;

INSERT INTO LineItem (order_id, product_id, quantity, price) VALUES (6, 1, 2, 1000);

delete from LineItem where order_id = 6  and product_id = 1;

UPDATE Customer SET customer_name = ? WHERE customer_id = ?;

DELIMITER $$
CREATE PROCEDURE usp_Customer_deleteById(
    CustomerID INT
)
BEGIN
DELETE FROM LineItem WHERE order_id IN (SELECT order_id FROM  `Order` WHERE customer_id = @CustomerID);
DELETE FROM `Order` WHERE customer_id = @CustomerID;
DELETE FROM Customer WHERE customer_id = @CustomerID;
END; $$
DELIMITER $$

CALL usp_Customer_deleteById (?);