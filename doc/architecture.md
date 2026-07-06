# SkillForge Architecture

## Overview

SkillForge is an online coding platform designed to help users improve their programming skills by solving coding challenges, executing code, submitting solutions, and tracking their progress. The platform also provides administrators with tools to create, manage, and maintain a repository of coding problems.

The first version focuses on delivering the core online judge functionality. However, the architecture is intentionally designed to evolve into a comprehensive developer learning platform with features such as coding contests, AI-powered interview preparation, resume analysis, personalized recommendations, and distributed code execution.

---

# Objectives

## Primary Objective

Develop a scalable, maintainable, and secure backend using:

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA (Hibernate)
* PostgreSQL

## Long-Term Objective

Design the application so that it can later evolve into a distributed system without major architectural changes.

Future enhancements include:

* Distributed Code Execution
* AI-Based Code Review
* Coding Contests
* Leaderboards
* Resume Analysis
* AI Interview Assistant
* Recommendation Engine
* Event-Driven Communication (Kafka)
* Microservices Architecture

---

# Architecture Style

SkillForge follows a **Modular Monolithic Architecture**.

A modular monolith combines the simplicity of a monolithic application with well-defined module boundaries. Each module has a single responsibility and communicates with other modules through service interfaces.

This approach offers:

* Faster development
* Simpler deployment
* Easier debugging
* Better maintainability
* Straightforward migration to microservices in the future

### Initial Modules

* Authentication Module
* User Module
* Problem Module
* Submission Module
* Dashboard Module

These modules can later be extracted into independent services without significant redesign.

---

# Core Design Principles

## Separation of Concerns

Each layer has a well-defined responsibility.

* Controllers handle HTTP requests and responses.
* Services implement business logic.
* Repositories interact with the database.
* Entities represent database models.
* DTOs define API contracts.

---

## Single Responsibility Principle

Each class should have one clear responsibility.

---

## DTO-Based Communication

Entities are never exposed directly through REST APIs.

All communication between the backend and frontend uses dedicated Request and Response DTOs.

---

## Stateless Authentication

Authentication is implemented using JWT tokens.

No user session is stored on the server.

---

## RESTful API Design

Endpoints represent resources using standard HTTP methods.

---

## Future Scalability

Modules remain loosely coupled so they can later become independent microservices.

---

# MVP Scope

Version 1 focuses on the core online judge functionality.

Included features:

* User Registration
* User Login
* JWT Authentication
* Role-Based Authorization
* Problem Management
* Code Execution
* Code Submission
* Submission History
* User Dashboard

Not included in Version 1:

* Coding Contests
* Leaderboards
* AI Interview Assistant
* Resume Analysis
* Personalized Recommendations
* Notifications
* Kafka
* Microservices
* Distributed Judge Service

---

# User Roles

## USER

A user interacts with the platform to solve coding problems.

Capabilities:

* Register
* Login
* Browse Problems
* View Problem Details
* Run Code
* Submit Code
* View Submission History
* View Dashboard

---

## ADMIN

An administrator manages the coding platform.

Capabilities:

* Login
* Create Problems
* Update Problems
* Delete Problems
* Manage Test Cases

---

# Functional Modules

## Authentication Module

Responsible for user authentication and authorization.

Features:

* User Registration
* User Login
* JWT Token Generation
* JWT Validation
* Password Encryption
* Role-Based Authorization

---

## User Module

Responsible for user profile information.

Features:

* View Profile
* View Own Submissions

---

## Problem Module

Responsible for managing coding problems.

User Features:

* View Problems
* View Problem Details

Admin Features:

* Create Problem
* Update Problem
* Delete Problem
* Manage Test Cases

---

## Submission Module

Responsible for evaluating user solutions.

Features:

* Run Code
* Submit Code
* Store Submission History
* Execute Hidden Test Cases
* Return Verdict

Possible Verdicts:

* Accepted
* Wrong Answer
* Time Limit Exceeded
* Runtime Error
* Compilation Error

---

## Dashboard Module

Responsible for displaying coding statistics.

Features:

* Solved Problems
* Total Submissions
* Acceptance Rate
* Recent Submissions

---

# Layered Architecture

```
Client
   │
   ▼
REST Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

Each layer communicates only with adjacent layers.

Controllers never access repositories directly.

---

# Project Structure

```
backend/
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── skillforge/
        │           ├── config/
        │           ├── controller/
        │           ├── dto/
        │           │   ├── request/
        │           │   └── response/
        │           ├── entity/
        │           ├── exception/
        │           ├── mapper/
        │           ├── repository/
        │           ├── security/
        │           ├── service/
        │           │   └── impl/
        │           ├── util/
        │           ├── validator/
        │           └── SkillForgeApplication.java
        │
        └── resources/
            ├── application.yml
            ├── static/
            └── templates/
```

---

# Package Responsibilities

| Package      | Responsibility                       |
| ------------ | ------------------------------------ |
| config       | Application configuration            |
| controller   | REST API endpoints                   |
| service      | Business logic interfaces            |
| service.impl | Business logic implementation        |
| repository   | Database access                      |
| entity       | JPA entities                         |
| dto          | API request and response models      |
| mapper       | Entity ↔ DTO conversion              |
| security     | JWT authentication and authorization |
| exception    | Global exception handling            |
| validator    | Custom validation logic              |
| util         | Shared helper classes                |

---

# Database Design

## User

| Field     | Type                 |
| --------- | -------------------- |
| id        | Long                 |
| name      | String               |
| email     | String (Unique)      |
| password  | String (BCrypt Hash) |
| role      | Enum(USER, ADMIN)    |
| createdAt | LocalDateTime        |
| updatedAt | LocalDateTime        |

---

## Problem

| Field       | Type                     |
| ----------- | ------------------------ |
| id          | Long                     |
| title       | String                   |
| description | Text                     |
| difficulty  | Enum(EASY, MEDIUM, HARD) |
| timeLimit   | Integer                  |
| memoryLimit | Integer                  |
| createdBy   | User                     |
| createdAt   | LocalDateTime            |
| updatedAt   | LocalDateTime            |

---

## TestCase

| Field          | Type    |
| -------------- | ------- |
| id             | Long    |
| problemId      | Long    |
| input          | Text    |
| expectedOutput | Text    |
| isHidden       | Boolean |

---

## Submission

| Field         | Type          |
| ------------- | ------------- |
| id            | Long          |
| userId        | Long          |
| problemId     | Long          |
| language      | Enum          |
| sourceCode    | Text          |
| status        | Enum          |
| executionTime | Integer       |
| memoryUsed    | Integer       |
| submittedAt   | LocalDateTime |

---

# Entity Relationships

```
User
 ├──────────────┐
 │              │
 ▼              ▼
Problem     Submission
                 │
                 ▼
             Problem
                 │
                 ▼
            TestCase
```

Relationships:

* One User → Many Problems
* One User → Many Submissions
* One Problem → Many Test Cases
* One Problem → Many Submissions

---

# REST API Design

## Authentication

| Method | Endpoint           | Description   |
| ------ | ------------------ | ------------- |
| POST   | /api/auth/register | Register User |
| POST   | /api/auth/login    | Login User    |

---

## Users

| Method | Endpoint                  | Description             |
| ------ | ------------------------- | ----------------------- |
| GET    | /api/users/me             | Current User            |
| GET    | /api/users/me/submissions | User Submission History |

---

## Problems

| Method | Endpoint           | Description     |
| ------ | ------------------ | --------------- |
| GET    | /api/problems      | List Problems   |
| GET    | /api/problems/{id} | Problem Details |
| POST   | /api/problems      | Create Problem  |
| PUT    | /api/problems/{id} | Update Problem  |
| DELETE | /api/problems/{id} | Delete Problem  |

---

## Submissions

| Method | Endpoint              | Description     |
| ------ | --------------------- | --------------- |
| POST   | /api/submissions/run  | Execute Code    |
| POST   | /api/submissions      | Submit Solution |
| GET    | /api/submissions/{id} | View Submission |

---

# API Response Codes

| Status Code | Meaning               |
| ----------- | --------------------- |
| 200         | OK                    |
| 201         | Created               |
| 204         | No Content            |
| 400         | Bad Request           |
| 401         | Unauthorized          |
| 403         | Forbidden             |
| 404         | Not Found             |
| 409         | Conflict              |
| 500         | Internal Server Error |

---

# Request Lifecycle

```
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
   │
   ▼
Mapper
   │
   ▼
Response DTO
   │
   ▼
Client
```

---

# Class Blueprint

## Controllers

* AuthController
* UserController
* ProblemController
* SubmissionController
* DashboardController

## Services

* AuthService
* UserService
* ProblemService
* SubmissionService
* DashboardService

## Repositories

* UserRepository
* ProblemRepository
* TestCaseRepository
* SubmissionRepository

## Entities

* User
* Problem
* TestCase
* Submission

## Mappers

* UserMapper
* ProblemMapper
* SubmissionMapper

## Security

* SecurityConfig
* JwtService
* JwtAuthenticationFilter
* CustomUserDetailsService
* JwtAuthenticationEntryPoint

## Exceptions

* GlobalExceptionHandler
* ResourceNotFoundException
* DuplicateEmailException
* UnauthorizedException
* ForbiddenException

---

# Future Roadmap

The architecture is intentionally designed to support future modules without major refactoring.

Planned modules:

* Contest Module
* Leaderboard Module
* AI Interview Module
* Resume Analysis Module
* Recommendation Module
* Notification Module
* Distributed Judge Service
* Kafka Integration
* Microservices Architecture

---

# Conclusion

The SkillForge MVP is designed as a modular monolithic application with clear separation of concerns, layered architecture, RESTful APIs, JWT-based authentication, and a normalized relational database.

This architecture provides a strong foundation for the current MVP while enabling a smooth transition toward a distributed, microservices-based platform as the application evolves.
