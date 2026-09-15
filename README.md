# Multithreaded Global Hotel Reservation & Localization Service

A containerized full-stack web application built with a Java Spring Boot backend and an Angular frontend. The system provides hotel room reservation management alongside asynchronous internationalization (i18n), multi-time-zone event conversion, and multi-currency pricing display.

---

## Technical Highlights

- **Asynchronous Localization (i18n):** Uses Java concurrency with `ExecutorService` and worker threads alongside `ResourceBundle` to concurrently load and serve localized welcome messages across multiple locales.

- **Time Zone Conversion:** Uses the Java Time API, including `ZonedDateTime`, `ZoneId`, and `DateTimeFormatter`, to calculate and format scheduled presentation times across Eastern Time (ET), Mountain Time (MT), and Coordinated Universal Time (UTC).

- **Multi-Currency Representation:** Uses Angular localization and currency formatting to display room rates in multiple currencies, including USD ($), CAD (C$), and EUR (€).

- **Docker Containerization:** Includes a multi-stage `Dockerfile` for building the Angular frontend, compiling the Spring Boot backend, and running the completed application in a Java runtime container.

- **RESTful API Integration:** Connects the Angular frontend to Spring Boot REST endpoints for localized messaging, time-zone information, and hotel room data.

---

## Tech Stack

| Layer | Technologies |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot, Spring Web, Spring Data JPA, Hibernate, Lombok |
| **Frontend** | Angular, TypeScript, RxJS, Bootstrap / CSS3, HTML5 |
| **Concurrency & Core APIs** | Java Concurrency (`java.util.concurrent`), Java Time API (`java.time`), `ResourceBundle` |
| **Build & DevOps** | Docker, Apache Maven, Node.js / npm, Angular CLI |
| **Data Storage** | H2 / MySQL |
| **API Style** | REST, JSON |

---

## System Architecture

```text
+-------------------------------------------------------------+
|                        Docker Host                          |
|                                                             |
|  +-------------------------------------------------------+  |
|  |             Spring Boot Service (Port 8080)           |  |
|  |                                                       |  |
|  |  +------------------+         +--------------------+  |  |
|  |  |   Angular SPA    |         |  REST Controllers  |  |  |
|  |  |   Static Assets  | <-----> |  - /welcome        |  |  |
|  |  |   (UI Build)     |         |  - /presentation-  |  |  |
|  |  +------------------+         |    time            |  |  |
|  |                               |  - /api/rooms      |  |  |
|  |                               +---------+----------+  |  |
|  |                                         |             |  |
|  |         +-------------------------------+             |  |
|  |         |                               |             |  |
|  |         v                               v             |  |
|  |  +--------------------+       +--------------------+  |  |
|  |  | Concurrency Engine |       | Spring Data JPA /  |  |  |
|  |  | (Thread Pool /     |       | Persistence Layer  |  |  |
|  |  | Resource Bundles)  |       | (H2 / MySQL)       |  |  |
|  |  +--------------------+       +--------------------+  |  |
|  +-------------------------------------------------------+  |
+-------------------------------------------------------------+
```

---

## API Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/` | Serves the Angular single-page application |
| `GET` | `/welcome` | Returns localized welcome messages using asynchronous worker threads |
| `GET` | `/presentation-time` | Returns scheduled presentation times converted to ET, MT, and UTC |
| `GET` | `/api/rooms` | Retrieves hotel room inventory and pricing information |

---

## Deployment & Containerization

The repository includes a multi-stage `Dockerfile` that builds the Angular frontend and Spring Boot backend before packaging the application into its runtime container.

### 1. Build the Docker Image

From the project root, run:

```bash
docker build -t multithreaded-hotel-service:latest .
```

### 2. Run the Container

```bash
docker run -d -p 8080:8080 --name hotel-app multithreaded-hotel-service:latest
```

### 3. Verify the Deployment

Once the container is running, open:

```text
http://localhost:8080/
```

The welcome endpoint can also be accessed directly at:

```text
http://localhost:8080/welcome
```

### 4. Stop the Container

To stop and remove the container:

```bash
docker stop hotel-app
docker rm hotel-app
```

---

## Local Development Setup

The backend and frontend can also be run independently without Docker.

### Prerequisites

Make sure the following tools are installed:

- Java Development Kit (JDK) 17+
- Node.js and npm
- Angular CLI
- Git

The project includes the Maven Wrapper, so a separate Maven installation is not required.

---

### Running the Backend

#### Windows

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

#### macOS / Linux

```bash
./mvnw clean compile
./mvnw spring-boot:run
```

The Spring Boot server will start at:

```text
http://localhost:8080
```

---

### Running the Frontend

Navigate to the Angular project directory:

```bash
cd src/main/UI
```

Install the frontend dependencies:

```bash
npm install
```

Start the Angular development server:

```bash
ng serve
```

The Angular development server will start at:

```text
http://localhost:4200
```

Changes to the frontend source code will automatically reload during development.

---

## Project Structure

```text
multithreaded-hotel-service/
├── Dockerfile                  # Multi-stage container definition
├── pom.xml                     # Maven configuration and dependencies
├── mvnw                        # Maven Wrapper for macOS / Linux
├── mvnw.cmd                    # Maven Wrapper for Windows
├── .gitignore                  # Git exclusions
│
└── src/
    ├── main/
    │   ├── java/               # Spring Boot application source
    │   │   ├── controller/     # REST controllers
    │   │   ├── entities/       # JPA domain entities
    │   │   ├── repository/     # Persistence interfaces
    │   │   └── services/       # Business, localization, and concurrency logic
    │   │
    │   ├── resources/
    │   │   ├── application.properties
    │   │   └── messages*.properties   # Localization ResourceBundles
    │   │
    │   └── UI/                 # Angular frontend
    │       ├── src/
    │       ├── package.json
    │       └── angular.json
    │
    └── test/                   # Backend tests
```

---

## Core Functionality

### Multithreaded Localization

Localized welcome messages are loaded using Java's `ResourceBundle` API. Concurrent execution allows localized messages to be processed using separate worker threads through Java's concurrency framework.

This demonstrates:

- Java thread management
- `ExecutorService`
- Concurrent task execution
- `ResourceBundle`
- Internationalization (i18n)

### Time Zone Conversion

The application converts presentation times between:

- Eastern Time (ET)
- Mountain Time (MT)
- Coordinated Universal Time (UTC)

The implementation uses Java's modern date and time APIs to handle time-zone conversion and formatting.

### Multi-Currency Display

Room pricing can be presented in multiple currencies:

- USD — U.S. Dollar ($)
- CAD — Canadian Dollar (C$)
- EUR — Euro (€)

The Angular frontend handles localized currency presentation for the user interface.

### Hotel Room Management

Hotel room information is exposed through the Spring Boot REST API and consumed by the Angular frontend. Persistence is managed through Spring Data JPA and Hibernate.

---

## Project Purpose

This project demonstrates the development and deployment of a full-stack Java web application with an emphasis on concurrency, internationalization, localization, REST API integration, and containerization.

The application combines a Spring Boot backend with an Angular frontend while demonstrating several software engineering concepts:

- Object-oriented Java development
- Multithreading and asynchronous processing
- RESTful web services
- Spring Boot application architecture
- Angular frontend development
- Internationalization and localization
- Time-zone conversion
- Multi-currency presentation
- Relational persistence with Spring Data JPA
- Maven-based builds
- Docker containerization

---

## Running at a Glance

### Docker

```bash
docker build -t multithreaded-hotel-service:latest .
docker run -d -p 8080:8080 --name hotel-app multithreaded-hotel-service:latest
```

Then open:

```text
http://localhost:8080/
```

### Local Development

**Backend:**

```bash
./mvnw spring-boot:run
```

**Frontend:**

```bash
cd src/main/UI
npm install
ng serve
```

Then open:

```text
http://localhost:4200/
```