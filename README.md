# Coffee Orders API

### Overview
This project is a RESTful API for managing coffee orders, built with Spring Boot. It demonstrates a modern, layered architecture and follows best practices for API design, configuration, and testing.

## Tech Stack
*   **Framework**: Spring Boot 3.2.5
*   **Language**: Java 21
*   **Build Tool**: Maven
*   **Database**: H2 In-Memory Database
*   **Testing**: JUnit 5, Mockito, and Spring Test (MockMvc)
*   **Utilities**: Lombok

## Architecture
The application follows a classic layered architecture to ensure separation of concerns and maintainability:
*   **Controller (`@RestController`)**: Exposes the REST endpoints and handles HTTP request/response cycles. It delegates business logic to the service layer.
*   **Service (`@Service`)**: Contains the core business logic of the application. It coordinates with the repository layer to perform operations.
*   **Repository (`@Repository`)**: Manages data persistence using Spring Data JPA. It abstracts the interaction with the H2 database.

## Getting Started

### Prerequisites
-   JDK 21 or later
-   Apache Maven

### Running the Application
1.  Clone the repository:
    ```shell
    git clone <repository-url>
    ```
2.  Navigate to the project directory:
    ```shell
    cd coffee-orders
    ```
3.  Run the application using the Maven wrapper:
    ```shell
    ./mvnw spring-boot:run
    ```
The API will be available at `http://localhost:4000`.

## API Endpoints

| Method | Endpoint                | Description                               |
|:-------|:------------------------|:------------------------------------------|
| `POST` | `/orders`               | Creates a new coffee order.               |
| `GET`  | `/orders`               | Retrieves a list of all coffee orders.    |
| `GET`  | `/orders/{id}`          | Retrieves a specific coffee order by ID.  |
| `PATCH`| `/orders/{id}/fulfill`  | Marks a specific order as fulfilled.      |

### Example cURL Requests

#### 1. Create a new coffee order
```shell
curl --request POST "http://localhost:4000/orders" \
--header "Content-Type: application/json" \
--data "{
    \"blend\": \"French roast\",
    \"size\": \"VENTI\",
    \"customer\": \"Joe\"
}"
```


#### Get coffee orders
```shell
curl --request GET "http://localhost:4000/orders"
```

#### Get coffee order 1
```shell
curl --request GET "http://localhost:4000/orders/1"
```

#### Fulfill coffee order 1
```shell
curl --request POST "http://localhost:4000/orders/1"
```