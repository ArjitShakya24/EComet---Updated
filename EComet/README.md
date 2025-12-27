# 📌 E-Comet – Java GUI Cart Management System

A desktop-based cart management application built in Java using Swing GUI, JDBC, OOP concepts and MySQL database connectivity.  
This project simulates a mini e-commerce platform with product browsing, search, cart, and checkout features.

---

## 🚀 Recent Upgrades (Current Version Enhancements)

| Feature             | Description                                      |
|---------------------|--------------------------------------------------|
| 🔍 Search Bar       | Users can search for products by name            |
| ✨ Suggestions      | Auto-suggests product names while typing         |
| 🎨 Enhanced UI      | Rounded buttons, cleaner layout, hover effects   |
| 🖼 Product Images   | Products now display images in grid card layout  |

---

## 🧰 Tech Stack

- **Java (Swing GUI)**
- **OOP Concepts:** Inheritance, Polymorphism, Encapsulation, Interfaces
- **Multithreading:** Auto cart refresh (Swing Timer)
- **Database:** MySQL (JDBC)
- **IDE:** Eclipse

---

## 📂 Project Structure

```
E-Comet/
│
├── src/ecomet/
│   ├── ECometGUI.java
│   ├── LoginGUI.java
│   ├── Product.java
│   ├── ProductDAO.java
│   ├── Cart.java
│   ├── CartItem.java
│   ├── OrderDAO.java
│   └── AppException.java
│   └── AbstractDAO.java
│   └── CartAutoSaver.java
│   └── DAO.java
│   └── DashboardGUI.java
│   └── DBConnection.java
│   └── InventoryManager.java
│   └── Main.java
│   └── NavigationPanel.java
│   └── RecommendationEngine.java
│   └── SearchService.java
│   └── User.java
│   └── UserDAO.java
│   └── Utils.java
│
├── images/
│   ├── mouse.png
│   ├── keyboard.png
│   └── laptopstand.png
│   └── usbc_charger.png
│
├── lib/   ← MySQL JDBC Driver (mysql-connector.jar)
└── README.md
```

---

## 🛠 How to Run the Project

### 1️⃣ Install Requirements
- Install MySQL
- Install Eclipse IDE
- Add `mysql-connector.jar` to **Build Path → Classpath**

### 2️⃣ Create Database & Tables
```sql
CREATE DATABASE ecomet;
USE ecomet;

CREATE TABLE products (
 id INT PRIMARY KEY,
 name VARCHAR(100),
 price DOUBLE,
 stock INT,
 image VARCHAR(100)
);
```

### 3️⃣ Update DB Credentials in `ProductDAO.java`
```java
private static final String URL = "jdbc:mysql://localhost:3306/ecomet";
private static final String USER = "root";
private static final String PASS = "yourpassword";
```

### 4️⃣ Run the Program
- Right-click `main.java` → **Run As → Java Application**
- OR start from `LoginGUI.java` if login system is active

---

## 🎯 Features

- Grid-based product display with images
- Add to cart functionality
- Increase / decrease quantity
- Search + live suggestions
- Checkout with correct total display
- Modern UI elements

---
