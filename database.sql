CREATE DATABASE IF NOT EXISTS employee_db
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'employee_user'@'localhost'
    IDENTIFIED BY 'EmployeePass123!';

GRANT ALL PRIVILEGES ON employee_db.*
    TO 'employee_user'@'localhost';