# Inventory Management System

## Overview
The Inventory Management System is a menu-driven Java application developed using JDBC and MySQL. It helps a retail store manage product records, update stock, modify product prices, and calculate total inventory value.

## Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- Maven
- IntelliJ IDEA

## Database Details
**Database Name:** `retail_db`  
**Table Name:** `Product`

| Column | Data Type | Description |
|---|---|---|
| product_id | INT | Primary key |
| product_name | VARCHAR(100) | Product name |
| category | VARCHAR(50) | Product category |
| quantity | INT | Available stock |
| price | DOUBLE | Product price |

## Features

1. **Insert Product Records:** Inserts three product records using `PreparedStatement` and handles duplicate product IDs.
2. **Conditional Retrieval and Sorting:** Displays products belonging to a specified category with quantity less than 20, sorted by price in descending order.
3. **Update Product Stock:** Adds the specified quantity to the existing stock of a product.
4. **Search and Modify Product Details:** Updates the price only when the product exists and the new price is greater than zero. Displays the updated product details.
5. **Inventory Value and Conditional Deletion:** Calculates the total inventory value using `SUM(quantity * price)` and deletes a product only when its quantity is zero.

## Database Setup

Execute the following SQL commands in MySQL Workbench:

```sql
CREATE DATABASE IF NOT EXISTS retail_db;
USE retail_db;

CREATE TABLE IF NOT EXISTS Product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DOUBLE
);
```

## Maven Dependency

The MySQL Connector/J dependency is configured in `pom.xml`:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.5.0</version>
</dependency>
```

## How to Run

1. Open the project in IntelliJ IDEA.
2. Ensure MySQL Server is running.
3. Create the `retail_db` database and `Product` table.
4. Verify the MySQL connection URL, username, and password in `InventoryManagement.java`.
5. Reload the Maven project to download the required dependencies.
6. Run `InventoryManagement.java`.
7. Select an operation from the menu and follow the prompts.

## Sample Menu

```text
===== INVENTORY MANAGEMENT =====
1. Insert 3 Products
2. Search by Category
3. Update Stock
4. Update Product Price
5. Inventory Value and Delete
6. Exit

Enter choice:
```

## Learning Outcomes

- Connecting Java applications to MySQL using JDBC.
- Executing parameterized SQL queries using `PreparedStatement`.
- Performing CRUD operations.
- Applying filtering and sorting using SQL.
- Using aggregate functions to calculate inventory value.
- Handling duplicate product IDs and missing product records.

## Author
Student Project — Inventory Management System
# JDBC_QUES1
