# TaskManager Backend API

A RESTful backend application built with Spring Boot and PostgreSQL.  
The project demonstrates authentication (JWT), layered architecture (DTOs), and API documentation with Swagger.

---

## Features

- User registration with password encryption (BCrypt)
- User login with JWT authentication
- Role-based authorization (ADMIN / USER)
- Task management (CRUD)
- DTO-based architecture (request/response separation)
- Swagger API documentation
- PostgreSQL database integration
- Spring Data JPA
- Dockerized deployment
- RESTful API design

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- PostgreSQL
- Swagger (springdoc-openapi)
- Docker
- Maven

---

## Authentication

After successful login, the API returns a JWT token:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

To access protected endpoints:
```
Authorization: Bearer YOUR_TOKEN
```

---
## API Endpoints
### Register user
```
POST /api/register
```
### Login user
```
POST /api/login
```
### Get all users (ADMIN only)
```
GET /api/users
```
### Tasks (authenticated)
```
GET /api/tasks
POST /api/tasks
PUT /api/tasks/{id}
DELETE /api/tasks/{id}
```
---
## Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

---
## Database Setup (Local)

### Create database:
```sql
CREATE DATABASE taskdb;
```
---
## Environment Variables (IMPORTANT FOR DEPLOY)

### When deploying (Render / Railway), set:
```
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>

JWT_SECRET=your_secret_key
```
---
## Docker Deployment
### Build and run:
```bash
docker compose up --build
```
---
## Docker Compose Example
```yaml
version: "3.8"

services:
  db:
    image: postgres:16
    container_name: taskmanager-db
    environment:
      POSTGRES_DB: taskdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"

  app:
    build: .
    container_name: taskmanager-app
    ports:
      - "8080:8080"
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/taskdb
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: password
```
---
## How to Run Locally
````Bash
mvn clean package
mvn spring-boot:run
````
---
## Architecture
````
controller → service → repository → database
````
### Security layer:
````
JWT Filter → Security Config → Controller → Service
````
---
## Purpose
### This project was built to practice:

- REST API development
- JWT authentication
- Role-based security
- Clean architecture (DTO + Service layer)
- PostgreSQL integration
- Docker deployment
- Swagger documentation
---
## Deployment Ready Status

- Dockerized
- Stateless JWT authentication
- PostgreSQL support
- Environment variables configured
- Ready for Render / Railway / Fly.io deployment
--- 
# Author
Backend learning project for portfolio and job applications.