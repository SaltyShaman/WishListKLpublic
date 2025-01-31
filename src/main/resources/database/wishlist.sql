CREATE DATABASE IF NOT EXISTS WishListKlDB DEFAULT CHARACTER SET utf8;
USE WishListKlDB;

-- Disable foreign key checks temporarily to avoid constraint errors
SET FOREIGN_KEY_CHECKS = 0;

-- Drop existing tables in the correct order
DROP TABLE IF EXISTS wishlist_items;
DROP TABLE IF EXISTS wishlist;

DROP TABLE IF EXISTS user;

-- Enable foreign key checks again
SET FOREIGN_KEY_CHECKS = 1;

-- Create the user table with correct primary key definition
CREATE TABLE user (
    id int AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    `name` VARCHAR(255) NOT NULL,  -- Use backticks around 'name'
    email VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL
);

-- Reset the AUTO_INCREMENT value (optional, if you want to start from a specific value)
ALTER TABLE user AUTO_INCREMENT = 30;  -- Set this to the next value you want for the ID

-- Create the wishlist table
CREATE TABLE wishlist (
    wishlistId INTEGER AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    FOREIGN KEY (username) REFERENCES user(username)
);

-- Create the wishlist_items table
CREATE TABLE wishlist_items (
    itemId INTEGER AUTO_INCREMENT PRIMARY KEY,
    wishlistId INTEGER,
    object VARCHAR(255),
    FOREIGN KEY (wishlistId) REFERENCES wishlist(wishlistId) ON DELETE CASCADE
);

-- Insert a user into the user table
INSERT INTO user (username, name, email, phoneNumber)
VALUES ('Tester', 'John Doe', 'johndoe@gmail.com', '88-77-66-55');

INSERT INTO user (username, name, email, phoneNumber)
VALUES ('TesterIJ', 'John Doe', 'gmail@gmail.com', '88-77-66-55');

-- inserted user suddenly recieved id 10
INSERT INTO user (username, name, email, phoneNumber)
VALUES ('TesterIAN', 'John Doe', 'gmail@gmail.com', '88-77-66-55');

-- View the inserted user data
SELECT * FROM user;

-- Insert a wishlist for the user 'Tester'
INSERT INTO wishlist (username) VALUES ('Tester');

-- Insert items into the wishlist (assuming wishlistId=1 was generated)
INSERT INTO wishlist_items (wishlistId, object) VALUES (1, 'Laptop');
INSERT INTO wishlist_items (wishlistId, object) VALUES (1, 'Smartphone');
INSERT INTO wishlist_items (wishlistId, object) VALUES (1, 'Headphones');

-- View the inserted wishlist and items
SELECT * FROM wishlist;
SELECT * FROM wishlist_items;

-- Check specific user by username
SELECT * FROM user WHERE username = 'testUsername1';

SELECT * FROM user WHERE id = '10';
SELECT * FROM user WHERE id = '35';

SELECT * FROM user WHERE username = 'testUsername27';

INSERT INTO user (username, name, email, phoneNumber) VALUES ('testUsername16', 'Test Name', 'test@example.com', '123456789');


-- Show table creation statement for the 'user' table
SHOW CREATE TABLE user;
