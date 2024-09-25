# Budget Management Application

## Description
Budget Management is a Spring Boot application designed to help users manage their budgets effectively. It provides features such as user registration, login, profile updates, and wallet balance management.

## Version
1.0

## Prerequisites
- Java 17
- Maven 3.9.9
- Docker
- Docker Compose

## Installation

### Clone the Repository
```sh
git clone https://github.com/mohamed-dhia-ben-amar/BudgetManagment.git
cd BudgetManagment
```

### Build the Application
```sh
./mvnw clean package -DskipTests
```

### Run with Docker Compose
Ensure Docker and Docker Compose are installed and running on your machine.

```sh
docker-compose up --build
```

## Usage
The application will be accessible at `http://localhost:8080`.

### API Endpoints
- **Register**: `POST /api/auth/register`
- **Login**: `POST /api/auth/login`
- **Logout**: `POST /api/auth/logout`
- **Update Profile**: `PUT /api/auth/updateProfile`
- **Update Wallet Balance**: `GET /api/auth/updateWalletBalance`
- **Test**: `GET /api/auth/test`

## Configuration
Configuration settings can be found in the `src/main/resources/application.properties` file.

### MongoDB Configuration
```ini
spring.data.mongodb.uri=mongodb://root:root@cluster0.mongodb.net/budget_management?retryWrites=true&w=majority
spring.data.mongodb.database=budget_management
```

### Server Configuration
```ini
server.port=8080
```

## License
This project is licensed under the Apache License 2.0. See the `LICENSE` file for details.
