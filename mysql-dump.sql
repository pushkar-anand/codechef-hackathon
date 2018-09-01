/*create database*/
CREATE DATABASE codechef;

/*create user and grant access to the database */
CREATE USER CREATE USER 'codechef_user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'm3Jy7^^l*C2GzE0H7wM2';
GRANT SELECT,INSERT,UPDATE ON codechef.* TO 'codechef_user'@'localhost';