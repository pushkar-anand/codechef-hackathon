/*create database*/
CREATE DATABASE IF NOT EXISTS codechef;

/*switch db*/
USE codechef;

/*users table */
CREATE TABLE IF NOT EXISTS users(id INT(11) AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50)  NOT NULL,
  codechef_username VARCHAR(50) NOT NULL,
  codechef_token VARCHAR(50) NOT NULL,
  login_token VARCHAR(256) NOT NULL);

ALTER TABLE `users` ADD UNIQUE( `codechef_username`);


