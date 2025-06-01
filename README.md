
# 🚗 Car Rental Backend System (Java + Spring Boot)

A full-featured car rental backend application developed using **Java 24**, **Spring Boot 3.4.5**, and **PostgreSQL 17**, following clean architecture and RESTful principles. This project focuses on building a modular and secure backend with a clear separation of concerns, robust authentication, and practical real-world use cases.

## Table of Contents

- [File Structure](#file-structure)
- [Project Modules](#project-modules)
- [Entity Relationships](#entity-relationships)
- [Entity Attributes Overview](#entity-attributes-overview)
  - [User](#user)
  - [Address](#address)
  - [Vehicle](#vehicle)
  - [Payment](#payment)
  - [Order](#order)
- [Tech Stack](#tech-stack)
- [REST API Endpoints](#rest-api-endpoints)
  - [User Controller](#user-controller)
  - [Vehicle Controller](#vehicle-controller)
  - [Order Controller](#order-controller)
- [Security Overview](#security-overview)
- [Key Features and Design](#key-features-and-design)
- [What I Learned](#what-i-learned)
- [Whats Next](#whats-next)
- [Feedback Welcome](#feedback-welcome)



## 📁 File Structure

```
src/java/org.example.carrentalbackend/
├── user/
├── address/
├── vehicle/
├── payment/
├── order/
├── dto/
├── error/
├── helper/
└── Application.java
```

## 🔍 Project Modules

### `user/`
- `Role.java` – Enum for user roles
- `User.java` – Entity
- `UserController.java` – API Controller
- `UserRepository.java` – JPA Repository
- `UserService.java` – Business Logic Layer

### `address/`
- `Address.java` – Entity
- `AddressRepository.java` – JPA Repository

### `vehicle/`
- `CarType.java` – Enum
- `Vehicle.java` – Entity
- `VehicleController.java`
- `VehicleRepository.java`
- `VehicleService.java`

### `payment/`(#payment)
- `CardNetwork.java` – Enum
- `CardType.java` – Enum
- `Payment.java` – Entity
- `PaymentRepository.java`

### `order/`
- `Order.java` – Entity
- `OrderController.java`
- `OrderService.java`
- `OrderStatus.java` – Enum

### `dto/`
- `LoginRequest.java`, `LoginResponse.java`
- `OrderRequest.java`, `OrderResponse.java`
- `PasswordRequest.java`

### `error/`
- `ErrorResponse.java`
- `ExceptionsName.java` – Enum
- `RequestStatus.java` – Enum

### `helper/`
- `ExpirationDataDeserializer.java`
- `RandomIdGenerator.java`
- `YearDeserializer.java`

## 🔗 Entity Relationships

- **User ↔ Address**: One-to-Many
- **User ↔ Payment**: One-to-Many
- **User ↔ Order**: One-to-Many
- **Vehicle ↔ Order**: One-to-Many (Vehicle must be available to be ordered)
- **Order**: References User, Vehicle, and Payment

## 🧩 Entity Attributes Overview

### `User`
- `userId: UUID` (PK)
- `firstName`, `lastName`, `username`, `email`, `password`, `phoneNumber`
- `userRole: Role`
- `addresses`, `orders`, `payments` – FK Lists
- `accountCreatedDate: LocalDateTime`

### `Address`
- `addressId: UUID` (PK)
- `user: User` (FK)
- `houseNumber`, `streetName`, `city`, `state`, `zipCode`
- `addressAddedOn: LocalDateTime`

### `Vehicle`
- `vinNumber: String` (PK)
- `make`, `model`, `year`, `color`, `licensePlate`
- `carType: CarType`, `isAvailable`, `rate`, `vehicleImgSrc`
- `currentMileage`, `cityMpg`, `highwayMpg`
- `orders: List<Order>` – FK

### `Payment`
- `paymentMethodId: UUID` (PK)
- `user: User` (FK)
- `cardType: CardType`, `cardNetwork: CardNetwork`
- `cardOwnerName`, `cardNumber`, `expirationDate`, `cardCvv`
- `addedOn: LocalDateTime`

### `Order`
- `orderId: String` (PK, human-readable)
- `user`, `vehicle`, `payment` – FKs
- `reservationDateTime`, `returnDateTime`
- `orderStatus: OrderStatus`

## 🛠️ Tech Stack

- **Language**: Java 24
- **Framework**: Spring Boot 3.4.5
- **Database**: PostgreSQL 17
- **Build Tool**: Maven
- **Security**: Spring Security, Bcrypt (strength: 10)
- **Testing**: Postman
- **Version Control**: GitHub
- **IDE**: IntelliJ IDEA

## 🌐 REST API Endpoints

### User Controller

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/user` | Get all users |
| POST   | `/user/register` | Register a new user |
| POST   | `/user/login` | Log in user |
| GET    | `/user/username/{username}` | Get user details |
| PUT    | `/user/username/{username}` | Update user |
| PUT    | `/user/password/username/{username}` | Update password |
| DELETE | `/user/username/{username}` | Delete user |
| POST   | `/user/{username}/save` | Add address |
| GET    | `/user/username/address/{username}` | Get all addresses |
| DELETE | `/user/username/address/{username}/index` | Delete address by index |
| POST   | `/user/{username}/create-order` | Create order |
| POST   | `/user/username/payment/{email}` | Add payment method |
| DELETE | `/user/username/payment/{username}` | Delete payment method |

### Vehicle Controller

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/vehicle` | Get all vehicles |
| GET    | `/vehicle/{vinNumber}` | Get vehicle by VIN |
| POST   | `/vehicle` | Add new vehicle |
| DELETE | `/vehicle/{vinNumber}` | Delete vehicle |
| PUT    | `/vehicle/{vinNumber}` | Update vehicle |

### Order Controller

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/order` | Get all orders (admin) |
| DELETE | `/order/cancel/{orderId}` | Cancel an order |
| GET    | `/order/user/{username}` | Get orders by username |
| GET    | `/order/user/{email}` | Get orders by email |
| GET    | `/order/user/{phoneNumber}` | Get orders by phone number |

## 🔐 Security Overview

- **Password Hashing**: Bcrypt with strength 10
- **Public Endpoints**:
  - `GET /vehicle`
  - `POST /user/register`
  - `POST /user/login`
- **Authentication Required**: All other endpoints
- **Spring Security**: Configured for role-based access and route protection

## ✅ Key Features & Design

- ✅ Strong validation (no duplicate VINs, logical reservations)
- ✅ Enum usage for controlled values (`Role`, `OrderStatus`, `CarType`, etc.)
- ✅ DTOs used for secure and efficient data exchange
- ✅ Jackson used for parsing and data formatting
- ✅ Manual UUID generation + custom readable order IDs
- ✅ Consistent naming conventions: `snake_case` for SQL, `camelCase` for Java

## 🧠 What I Learned

- **First full Spring Boot project** – previously tried ExpressJS and Django
- **Gained deeper understanding of OOP** – including interfaces, encapsulation, and services
- **Enums** – powerful for controlling app logic
- **Type Safety** – learned to appreciate Java’s static typing for preventing runtime errors
- **Design Patterns** – had to redesign my DB structure multiple times
- **Annotations** – became familiar with common Spring annotations
- **Postman** – learned effective API testing and debugging

## 🚀 What’s Next

- Currently building a **frontend** using:
  - **React**
  - **TypeScript**
  - **Vite**

## 🙏 Feedback Welcome!

> This was a massive learning experience. I welcome **any feedback** or code reviews on GitHub to help me improve. Your suggestions will be deeply appreciated!
