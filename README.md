# 🛒 E-Commerce REST API

A production-style RESTful E-Commerce API built with **Spring Boot** that provides secure authentication, product management, shopping cart functionality, order processing, and payment integration using **Paystack**.

This project was built to demonstrate backend development concepts including JWT authentication, role-based authorization, RESTful API design, Spring Security, JPA/Hibernate, database relationships, payment gateway integration, and clean software architecture.

---

# 🚀 Features

## Authentication & Authorization

* User Registration
* User Login
* JWT Authentication
* Role-Based Authorization (USER / ADMIN)
* Password Encryption using BCrypt
* Protected API Endpoints

---

## Product Management

* Create Products (Admin Only)
* Update Products (Admin Only)
* Delete Products (Admin Only)
* View All Products
* View Product Details
* Search Products
* Product Inventory Management
* Product Activation/Deactivation

---

## Category Management

* Create Category
* Update Category
* Delete Category
* View Categories
* Assign Products to Categories

---

## Shopping Cart

* Add Product to Cart
* Update Cart Item Quantity
* Remove Item from Cart
* View Current Cart
* Clear Cart
* Automatic Cart Total Calculation

---

## Order Management

* Checkout
* Automatic Order Creation
* Generate Unique Order Number
* View User Orders
* View Single Order
* Order Status Tracking

Order Statuses:

* PENDING
* PROCESSING
* SHIPPED
* DELIVERED
* CANCELLED

---

## Payment Integration

Integrated with **Paystack** Test API.

Features include:

* Initialize Payment
* Generate Payment Authorization URL
* Verify Payment
* Automatic Order Status Update after Successful Payment

---

## Security

* JWT Authentication
* Stateless Authentication
* Spring Security
* Endpoint Authorization
* Admin-only Operations
* Custom Authentication Entry Point
* Custom Access Denied Handler
* Global Exception Handling

---

## Validation

Request validation using Jakarta Validation.

Examples include:

* Required fields
* Email validation
* Quantity validation
* Duplicate resource prevention
* Business rule validation

---

## API Documentation

Interactive API documentation using Swagger / OpenAPI.

Swagger UI:

http://localhost:8080/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8080/v3/api-docs

---

# 🛠 Tech Stack

## Backend

* Java 21
* Spring Boot 3
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate
* Maven

---

## Database

* MySQL

---

## Authentication

* JWT (JSON Web Token)

---

## Payment Gateway

* Paystack

---

## Documentation

* Swagger / OpenAPI

---

## Utilities

* Lombok
* Jakarta Validation

---

# 📂 Project Structure

```text
src
└── main
    └── java
        └── com.raphael.ecommerce
            ├── auth
            ├── cart
            ├── category
            ├── common
            ├── config
            ├── exception
            ├── order
            ├── payment
            ├── product
            └── security
```

---

# 🗄 Database Design

Main entities:

* User
* Category
* Product
* Cart
* CartItem
* Order
* OrderItem

Relationships:

```
User
 ├── Cart (1:1)
 └── Orders (1:M)

Category
 └── Products (1:M)

Cart
 └── CartItems (1:M)

Order
 └── OrderItems (1:M)

Product
 ├── CartItems
 └── OrderItems
```

---

# Authentication Flow

```
User Registers
        │
        ▼
User Logs In
        │
        ▼
JWT Token Generated
        │
        ▼
Client Stores Token
        │
        ▼
JWT Sent with Every Protected Request
        │
        ▼
Spring Security Validates Token
        │
        ▼
Access Granted
```

---

# Checkout Flow

```
Add Products to Cart
          │
          ▼
Checkout
          │
          ▼
Validate Stock
          │
          ▼
Create Order
          │
          ▼
Reduce Product Inventory
          │
          ▼
Initialize Paystack Payment
          │
          ▼
Return Payment URL
          │
          ▼
Customer Pays
          │
          ▼
Verify Payment
          │
          ▼
Update Order Status
```

---

# REST API Endpoints

## Authentication

| Method | Endpoint           |
| ------ | ------------------ |
| POST   | /api/auth/register |
| POST   | /api/auth/login    |

---

## Products

| Method | Endpoint             |
| ------ | -------------------- |
| GET    | /api/products        |
| GET    | /api/products/{id}   |
| GET    | /api/products/search |
| POST   | /api/products        |
| PUT    | /api/products/{id}   |
| DELETE | /api/products/{id}   |

---

## Categories

| Method | Endpoint             |
| ------ | -------------------- |
| GET    | /api/categories      |
| GET    | /api/categories/{id} |
| POST   | /api/categories      |
| PUT    | /api/categories/{id} |
| DELETE | /api/categories/{id} |

---

## Cart

| Method | Endpoint                     |
| ------ | ---------------------------- |
| GET    | /api/cart                    |
| POST   | /api/cart/items              |
| PUT    | /api/cart/items/{cartItemId} |
| DELETE | /api/cart/items/{cartItemId} |
| DELETE | /api/cart                    |

---

## Orders

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | /api/orders/checkout |
| GET    | /api/orders          |
| GET    | /api/orders/{id}     |

---

## Payments

| Method | Endpoint                         |
| ------ | -------------------------------- |
| GET    | /api/payments/verify/{reference} |

---

# ⚙ Installation

## Clone Repository

```bash
git clone https://github.com/Oche1-deals/E-Commerce-API.git
```

---

## Navigate into Project

```bash
cd ecommerce-api
```

---

## Configure Database

Create a MySQL database.

Example:

```
ecommerce_db
```

Update your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

## Configure JWT

```properties
jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

---

## Configure Paystack

```properties
paystack.secret-key=YOUR_PAYSTACK_SECRET_KEY
paystack.base-url=https://api.paystack.co
```

---

## Run Application

```bash
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8080
```

---

# Testing

The API can be tested using:

* Swagger UI
* Postman

Example Authentication Flow:

1. Register
2. Login
3. Copy JWT
4. Authorize Swagger
5. Access Protected Endpoints

---

# Error Handling

Global exception handling has been implemented.

Examples include:

* Resource Not Found
* Validation Errors
* Business Exceptions
* Authentication Errors
* Authorization Errors

---

# Future Improvements

* Email Verification
* Forgot Password
* Product Image Upload
* Customer Address Management
* Order Cancellation
* Payment Webhooks
* Unit Tests
* Integration Tests
* Docker Support
* CI/CD Pipeline
* Cloud Deployment
* React Frontend

---

# Learning Objectives

This project demonstrates:

* REST API Development
* Spring Boot Best Practices
* Layered Architecture
* DTO Pattern
* Repository Pattern
* Service Layer Design
* Authentication & Authorization
* Exception Handling
* Payment Gateway Integration
* Transaction Management
* Database Relationships
* API Documentation
* Clean Code Principles

---

# Author

**Raphael Odoh**

Backend Developer

Built with Java, Spring Boot, MySQL, Spring Security, JWT Authentication, Hibernate, and Paystack.

---

# License

This project is licensed under the MIT License.

# Project Link
link:https://roadmap.sh/projects/ecommerce-api