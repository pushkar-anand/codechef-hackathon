/*create database*/
CREATE DATABASE IF NOT EXISTS codechef;

/*create user and grant access to the database */
CREATE USER IF NOT EXISTS 'codechef_user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'm3Jy7^^l*C2GzE0H7wM2';
GRANT SELECT,INSERT,UPDATE ON codechef.* TO 'codechef_user'@'localhost';

USE codechef;
/*users table */
CREATE TABLE IF NOT EXISTS users(id INT(11) AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50)  NOT NULL,
  email VARCHAR(50) NULL, password VARCHAR(512) NULL,
  codechef_username VARCHAR(50) NOT NULL,
  codechef_token VARCHAR(50) NOT NULL,
  login_token VARCHAR(256) NOT NULL);

ALTER TABLE `users` ADD UNIQUE( `codechef_username`);


