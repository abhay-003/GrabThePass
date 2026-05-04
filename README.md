# GrabThePass
Backend REST API for a movie ticket reservation platform. Features dynamic seat allocation, theater mapping, and secure booking flows using Spring Boot.

# 🎟️ GrabThePass - Movie Ticket Booking REST API

A robust, fully relational RESTful API for a movie theater booking system. Built with **Java** and **Spring Boot**, this project manages the entire lifecycle of a movie ticket reservation—from browsing movies and theaters to selecting seats, processing payments, and generating confirmed bookings.

## ✨ Key Features

* **🍿 Movie Management:** Full CRUD operations for movies. Search movies by title, language, or genre.
* **🏢 Theater & Screen Mapping:** Relational mapping of theaters, screens, and specific showtimes (shifts).
* **🪑 Dynamic Seat Allocation:** Real-time tracking of seat availability (`AVAILABLE`, `PENDING`, `BOOKED`) based on specific showtimes and screen layouts.
* **💳 Booking & Payment:** Secure transactional flow linking users, their chosen seats, and payment confirmation.
* **🛡️ Clean Architecture:** Strictly follows the Controller-Service-Repository pattern. Uses **DTOs (Data Transfer Objects)** to protect database entities and strictly adheres to modern REST API standards (proper HTTP verbs and Status Codes).

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot (Spring Web, Spring Data JPA, Lombok)
* **Database:** MySQL
* **ORM:** Hibernate
* **Build Tool:** Maven

## 🗄️ Database Schema

This project features a highly normalized relational database. The core entities include:
* `users`
* `movies`
* `theaters` & `screens`
* `seats` & `shift_seats` (Showtimes)
* `bookings` & `payments`

*(Note: Add your ER Diagram image here!)*

## 🚀 Getting Started

### Prerequisites
* Java Development Kit (JDK) 21 or higher
* Maven
* MySQL Workbench / Server running on port 3306


📍 Sample API Endpoints

Movies
    
    GET --> /api/movies/all - Fetch all movies
    
    GET -->  /api/movies/movie/title?title={title} - Search movies by title
    
    POST --> /api/movies - Add a new movie
    
    PUT --> /api/movies/{id} - Update movie details
    
    DELETE --> /api/movies/{id} - Delete a movie

Bookings
    
    POST --> /api/bookings - Create a new booking
    
    GET --> /api/bookings/user/{userId} - Get all bookings for a specific user
    
    DELETE --> /api/bookings/cancel/{id} - Cancel a booking

    GET --> /api/bookings/{bookingId} - Get booking details by bookingId.
