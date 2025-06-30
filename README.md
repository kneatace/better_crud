User Management System – Java Swing + MySQL:

A simple desktop-based CRUD (Create, Read, Update, Delete) application to manage users using Java Swing and MySQL.

-Features
  ->Add new users (Name, Email, Password)

  ->Search users by name

  ->Update user info (requires correct password for the selected user)

  ->Delete selected users

  ->Interactive GUI with JTable

-Requirements
  ->Java JDK 8 or higher

  ->MySQL server (recommended: XAMPP)

  ->JDBC Driver (included by default in most IDEs)

-Setup
  ->Start MySQL via XAMPP
  
  ->Start Apache via XAMPP
  
  ->Create a crud database with a users_tb table:
    Code:
    CREATE TABLE users_tb (
      id INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(100),
      email VARCHAR(100),
      password VARCHAR(100)
    );
  ->Update database credentials in DataBaseConnection.java.

-Run Main.java from your IDE.
