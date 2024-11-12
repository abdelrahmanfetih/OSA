
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


CREATE TABLE user (
	`username` VARCHAR(255) PRIMARY KEY ,
	`password` VARCHAR(255)
);


CREATE TABLE product (
`productID` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL,
`description` VARCHAR(255) NOT NULL,
`price` DECIMAL(10, 2) NOT NULL,
`productQuantity` INT NOT NULL
);


Create Table Shopping (
`username` VARCHAR(255),
`productID` int,
Foreign key (username) References user(username), 
Foreign key (productID) References product(productID)

);

DELIMITER //
CREATE TRIGGER enforce_max_products
BEFORE INSERT ON shopping
FOR EACH ROW
BEGIN
    DECLARE product_count INT;
    
    SELECT COUNT(*) INTO product_count
    FROM shopping
    WHERE username = NEW.username AND productID = NEW.productID;
    
    IF product_count > 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Product is already in the shopping cart of the user.';
    END IF;
    
    SELECT COUNT(*) INTO product_count
    FROM shopping
    WHERE username = NEW.username;
    
    IF product_count >= 5 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot insert more than 5 products per user.';
    END IF;
END //
DELIMITER ;

