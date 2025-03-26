## HRS Hotel Parcel Tracking Service

### Overview
HRS Hotel Parcel Tracking Service is a Spring Boot-based backend application that enables tracking and managing parcels within a hotel system. It provides RESTful APIs for parcel management, guest interactions, and tracking functionalities.

### Features
This project includes the following features:
### 1. Swagger API Documentation (`feature/configure-swagger-docs`)
- Configured OpenAPI and Swagger UI for better API documentation and testing.
- Accessible at `http://localhost:8080/swagger-ui/`

### 2. Parcel Management APIs (`feature/exposed-guest-and-parcel-apis`)
- API to receive parcels and assign them to guests.
- API to retrieve available parcels for a specific guest.

### 3. Mark Parcel as Picked Up (`feature/expose-mark-parcel-picked-up-api`)
- Endpoint to mark a parcel as picked up by the guest.

### 4. Refactored API Response Structure (`feature/refactoring-apis-to-response-json-payloads`)
- Standardized JSON response format for consistency across all APIs.
- Improved error handling and response message clarity.

### 5. Unit Testing and Test Coverage (`feature/setup-unit-tests-and-test-coverage`)
- Implemented unit tests using JUnit and Mockito.
- Achieved test coverage validation using Jacoco.

### Tech Stack
- **Java:** JDK 11
- **Spring Boot:** 2.7.18
- **Build Tool:** Gradle (Groovy DSL)
- **Database Migration:** Flyway
- **Configuration Management:** DotEnv
- **Testing Frameworks:** JUnit 5, Mockito
- **Code Coverage:** JaCoCo
- **API Documentation:** OpenAPI/Swagger

### Getting Started
### 1. Prerequisites
Ensure you have the following installed:
- Java 11 (JDK 11)
- Gradle 8+

### 2. Clone the Repository
`git clone https://github.com/your-repo/hrs-hotel-parcel-tracking.git`  
`cd hrs-hotel-parcel-tracking`

### 3. Setup Environment Variables
Create your .env.local file like .env.example file in the project root 
with database credentials:

### 4. Run the Application
#### Using Gradle
`./gradlew bootRun`

### 5. Verify API Documentation
- **Swagger UI:** http://localhost:8080/swagger-ui/
- **JSON Docs:** http://localhost:8080/v3/api-docs

### Running Tests
Run unit tests and generate coverage reports:
`./gradlew test jacocoTestReport`
#### Reports can be found at:
`build/reports/jacoco/test/html/index.html`

### API Endpoints
| Method  | Endpoint                           | Description                           |
|---------|------------------------------------|---------------------------------------|
| `POST`  | `/api/guests/check-in`             | Check-in a guest                      |
| `POST`  | `/api/guests/check-out/{id}`       | Check-out a guest                     |
| `GET`   | `/api/guests/checked-in`           | Get checked-in guests with pagination |
| `POST`  | `/api/parcels/receive`             | Receive a parcel                      |
| `POST`  | `/api/parcels/pickup/{parcelId}`   | Mark parcel as picked up              |
| `GET`   | `/api/parcels/available/{guestId}` | Get available parcels for a guest     |

### Code Quality & Best Practices
- **RESTful API Design:** Follows standard HTTP methods and resource-oriented structure.
- **Logging & Error Handling:** Uses SLF4J and global exception handling for consistent logging.
- **Database Migrations:** Uses Flyway to manage schema changes.
- **Environment Configurations:** Managed using DotEnv to separate config from code.

### License
This project is licensed by [Dao Nguyen](https://www.facebook.com/daonguyendev/) - see the LICENSE file for details.