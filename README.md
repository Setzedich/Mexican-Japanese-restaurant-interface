# Mexican-Japanese Restaurant Interface

A desktop application developed in **Java Swing** for managing a Mexican-Japanese restaurant.  
The system includes different interfaces for **customers**, **employees**, and **administrators**, allowing users to manage menus, orders, products, order status, and basic restaurant operations.

---

## Project Description

This project simulates a restaurant management system with a Mexican-Japanese theme.  
The application allows customers to view the menu, filter products by category, add items to a cart, and place orders. It also includes tools for employees and administrators to manage orders, update order status, control products, and review general restaurant information.

The project was developed as an academic application using **Object-Oriented Programming**, **Java Swing**, and a local **SQLite database**.

---

## Main Features

- Graphical user interface built with Java Swing
- Customer panel
- Employee panel
- Administrator panel
- Full menu visualization
- Product filtering by category
- Shopping cart system
- Order management
- Order status updates
- Product administration
- Local database using SQLite
- External libraries integration
- Custom visual design with a Mexican-Japanese restaurant theme
- Multimedia support

---

## Technologies Used

- Java
- Java Swing
- SQLite
- JDBC
- Eclipse IDE
- JLayer
- Jakarta Mail
- JGoodies Forms

---

## Project Structure

```text
Mexican-Japanese-restaurant-interface/
│
├── src/                  # Source code
├── musica/               # Music and multimedia resources
├── restaurante.db        # SQLite database
├── *.jar                 # External libraries
├── .gitignore
├── LICENSE
└── README.md
```

---

## System Roles

### Customer

The customer can:

- View the restaurant menu
- Browse products by category
- Add products to the cart
- Review the total purchase amount
- Place orders
- View promotions and special menu sections

### Employee

The employee can:

- View registered orders
- Update the status of an order
- Track orders in progress
- Support the order management process

### Administrator

The administrator can:

- Add new products to the menu
- Remove existing products
- View registered orders
- Check restaurant sales or cash information
- Manage general restaurant operations

---

## Installation and Execution

### Requirements

Before running the project, make sure you have installed:

- Java JDK 17 or higher
- Eclipse IDE
- Git, optional if you want to clone the repository

---

### How to Run the Project

1. Clone this repository or download it as a ZIP file.

```bash
git clone https://github.com/Setzedich/Mexican-Japanese-restaurant-interface.git
```

2. Open Eclipse IDE.

3. Go to:

```text
File > Import > Existing Projects into Workspace
```

4. Select the project folder.

5. Make sure the external `.jar` libraries are correctly added to the Build Path.

6. Run the main class of the project.

---

## External Libraries

This project uses several external libraries, including:

```text
jakarta.activation-api-2.1.3.jar
jakarta.mail-api-2.1.3.jar
angus-mail-2.0.3.jar
sqlite-jdbc-3.53.0.0.jar
jlayer-1.0.1.jar
slf4j-simple-2.0.9.jar
com.jgoodies.common
com.jgoodies.forms
```

These libraries are used for database connection, email services, audio playback, and additional graphical interface components.

---

## Database

The system uses a local SQLite database named:

```text
restaurante.db
```

This database stores information related to products, orders, and other data required for the restaurant management system.

---

## Academic Objective

The main objective of this project is to apply and practice concepts such as:

- Object-Oriented Programming
- Graphical user interfaces
- Event handling
- Database connection
- Class organization
- External library usage
- Separation of responsibilities
- Multi-role system design

---

## Author

**Marlon Sebastián Molina Rodríguez**  
Cybernetics Engineering in Computer Systems Student  

GitHub: [@Setzedich](https://github.com/Setzedich)

---

## Project Status

Academic project in development.  
The system currently includes the main restaurant management features, graphical interfaces, and local database connection.

---

## License

This project is licensed under the MIT License.  
See the `LICENSE` file for more information.
