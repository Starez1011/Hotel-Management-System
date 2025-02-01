# Hotel Management System

## Overview
The **Hotel Management System** is a web application built using **Spring Boot** and **Maven**. It allows customers to check room availability and make reservations, while admins manage hotel rooms and reservations.

## Features

### Admin Role:
- Add, update, delete hotel rooms.
- View all reservations.
- Approve or reject room reservations.

### Customer Role:
- View available hotel rooms.
- Make room reservations.
- Check reservation status.

## Technologies Used
- **Spring Boot** (Java-based framework for web applications)
- **Spring Security** (Role-based access control for admin and customer)
- **Spring Data JPA** (Database interactions)
- **MySQL / PostgreSQL** (Relational database for storing rooms, reservations, and users)
- **Thymeleaf / React / Angular** (For frontend - optional based on implementation)
- **Maven** (Dependency management and build tool)

## Installation Guide

### Prerequisites
- Java 17+
- Maven
- MySQL / PostgreSQL

### Steps to Run the Application
1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-repo/hotel-management-system.git
   cd hotel-management-system
   ```
2. **Configure the database**
   - Update `application.properties` or `application.yml`:
   
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. **Build and run the application:**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. **Access the application:**
   - API: `http://localhost:8080/api/`
   - Admin Login: `http://localhost:8080/api/admin`
   - Customer Login: `http://localhost:8080/api/customer`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/login` - Login and get a token

### Admin APIs
- `POST /api/admin/room` - Add new room
- `GET /api/admin/rooms/0` - View all rooms
- `GET /api/admin/room/{id}` - Get room details by ID
- `PUT /api/admin/room/{id}` - Update room
- `DELETE /api/admin/room/{id}` - Delete room
- `GET /api/admin/reservations/0` - View all reservations
- `GET /api/admin/reservation/{id}/approve` - Approve reservation

### Customer APIs
- `GET /api/customer/rooms/0` - View available rooms
- `POST /api/customer/book` - Book a room
- `GET /api/customer/bookings/{userId}/0` - View reservation status

## User Roles & Access
| Role    | Permissions |
|---------|------------|
| Admin   | Manage rooms and reservations |
| Customer| View rooms and make reservations |
