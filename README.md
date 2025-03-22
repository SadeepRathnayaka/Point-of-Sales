# POS System - Microservices Architecture

## Overview
This is a **Point of Sale (POS) System** built using **Spring Boot** in a **microservices architecture**. The system includes multiple services such as **User Service, Customer Service, Inventory Service, Order Service, API Gateway, and Discovery Service**. Each service is responsible for its own database and functionality. The services communicate synchronously using **Spring WebClient**.

## Microservices

### 1. User Service
Handles user management, authentication, and authorization.

**Features:**
- Register users (Admins, Cashiers, Managers) with **Role-Based Access Control** (RBAC)
- Login with **JWT Authentication**
- Forgot password: OTP generation, validation, and reset
- **Exception handling**
  
#### **User Entity**
| userId | userName | email | role   | password | otp  | otpExpireDate |
|--------|---------|-------|--------|----------|------|--------------|
| 1      | Admin   | a@example.com | ADMIN | hashed_pwd | 123456 | 2025-03-23 12:00:00 |

---

### 2. Customer Service
Manages customer-related operations such as adding, retrieving, and updating customer information.

**Features:**
- Add, get, and update customers
- Update and claim **loyalty points**

#### **Customer Entity**
| customerId | customerName | customerEmail | phoneNumber | loyaltyPoints |
|------------|-------------|--------------|-------------|---------------|
| 101        | John Doe    | jdoe@gmail.com | 1234567890  | 50            |

---

### 3. Inventory Service
Handles inventory management.

**Features:**
- Add, retrieve, and update inventory
- Search inventory using **custom query methods**

#### **Inventory Entity**
| inventoryId | name   | description  | brandId | category | unit  | stock | inventoryPrice | inventoryExpireDate |
|------------|--------|-------------|---------|----------|-------|-------|----------------|----------------------|
| 201        | Laptop | Gaming Laptop | 10      | ELECTRONICS | PIECE | 25    | 1200.00        | 2026-06-01          |

#### **Brand Entity**
| brandId | brandName |
|---------|----------|
| 10      | Dell     |

---

### 4. Order Service
Handles order processing and interacts with other microservices.

**Features:**
- Place orders and claim loyalty points
- **Transaction management** across services
- Calls **Inventory and Customer services** using **WebClient**

#### **Order Endpoints Communication**
```java
@Bean
@LoadBalanced
public WebClient.Builder webClientBuilder(){
    return WebClient.builder();
}

@Bean
public WebClient customerWebClient(){
    return webClientBuilder().baseUrl("http://customer-service/api/v1").build();
}

@Bean
public WebClient inventoryWebClient(){
    return webClientBuilder().baseUrl("http://inventory-service/api/v1").build();
}
```

---

### 5. API Gateway
- **Handles JWT Authentication** for every request
- Routes requests to respective microservices
- Provides a **single entry point** for the system

---

### 6. Discovery Service
Implemented using **Eureka Server-Client**.

- **Handles multiple instances** of microservices
- Avoids **hardcoding service ports**
- Implements **load balancing** to distribute requests efficiently

---

## Security Features
- **JWT Authentication** for all user requests
- **Role-Based Access Control (RBAC)** ensures restricted access
- **OTP-based password reset** for security
- **Exception Handling** to manage failures gracefully

---

## Inter-Service Communication
- **WebClient** is used for **synchronous communication** between microservices
- **Eureka Discovery Server** dynamically assigns service instances
- **Load Balancer** ensures efficient request handling

---

## Database Handling
Each microservice has its own database for scalability and independent management.

- **User Service:** Manages user credentials and roles
- **Customer Service:** Handles customer data and loyalty points
- **Inventory Service:** Stores inventory and brand details
- **Order Service:** Manages orders while interacting with inventory and customer databases

---

## Summary
This POS system is built using a **scalable and secure microservices architecture**. It uses **JWT authentication**, **role-based access control**, and **WebClient for inter-service communication**. The **API Gateway** ensures smooth request handling, while **Eureka Server** enables service discovery and load balancing.

