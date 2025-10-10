# Zoo Challenge

This project is a small Zoo Management system built with **Java 21**, **Spring Boot 3.5**, **MongoDB**, and *
*Testcontainers**.  
The goal was to build a clean N-Tier architecture with focus on high performance and testability.

---

## Project Architecture

The app is designed with layered (n-tier) architecture:

web.rest (REST layer) </br>
↓ </br>
business (business logic) </br>
↓ </br>
data (data access) </br>
↓ </br>
MongoDB (database) </br>

### Layers in details:

- **web.rest layer** – expose REST APIs (CRUD for rooms, animals, etc.)
- **Business layer** – holds domain rules, validation, duplication checks
- **data layer** – use Spring Data Mongo repositories for persistence
- **Config layer** – setup Testcontainers, Mongo, and environment properties

Each module is separated by package to keep things clean and easy to test.

---

## Tech Stack

Java 21
Spring Boot 3.5.x
MongoDB 7.0.6
Testcontainers latest
Maven 3.9+
Docker / Docker Compose

---

## How to Run the Application

Make sure you have Java 21 and MongoDB running (or Docker Compose, see below).

```bash
mvn clean install
mvn spring-boot:run
```

## How to Make docker image and run container

Make sure you are in the root of project, and your docker is up, then run bellow command

```bash
docker build -t zoo-app:latest .

docker run -d -p 8080:8080 \    
  -e MONGODB_URI="mongodb://host.docker.internal:27017/zoo" \
  --name zoo-app \
  zoo-app:latest;
```

## Exposed api

to see all exposed api visit http://localhost:8080/swagger-ui/index.html  
</br><b> Notice:</b> SpringApiDoc only enables on dev profile.