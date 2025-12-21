# Teacher-Student Manager

This project is a simple REST API for managing teachers and students, built with Java and Spring Boot. It provides CRUD (Create, Read, Update, Delete) operations for both entities.

## About The Project

A demonstration project showcasing a modern Java backend application. It includes features like:

*   RESTful API for managing teachers and students.
*   Database schema management with Flyway.
*   Automatic API documentation with OpenAPI (Swagger UI).
*   Production-ready features like health checks with Spring Boot Actuator.

## Built With

*   [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
*   [Spring Boot](https://spring.io/projects/spring-boot)
*   [Maven](https://maven.apache.org/)
*   [MySQL](https://www.mysql.com/)
*   [Flyway](https://flywaydb.org/)
*   [JPA / Hibernate](https://spring.io/projects/spring-data-jpa)
*   [springdoc-openapi](https://springdoc.org/) for API documentation
*   [Lombok](https://projectlombok.org/)

## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

*   Java 21 or later
*   Maven
*   A running MySQL database instance.

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone <your-repository-url>
    cd Project_TeacherStudentManager
    ```

2.  **Configure the database:**
    *   Create a MySQL database.
    *   Update the database connection settings in `src/main/resources/application.properties`. The current configuration expects a database named `teacher_students_db` with a user `appuser` and password `apppass`.
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/teacher_students_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
      spring.datasource.username=appuser
      spring.datasource.password=apppass
      ```
    *   The database schema will be automatically created and managed by Flyway when the application starts.

## Usage

### Running the Application

You can run the application using the Maven wrapper:

```sh
./mvnw spring-boot:run
```

The application will start on port `8080` by default.

### API Documentation

Once the application is running, you can access the interactive API documentation (Swagger UI) at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

You can also get the raw OpenAPI specification from:

*   **JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
*   **YAML:** [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

This is useful for importing the API into tools like Insomnia or Postman.

### API Endpoints

Here is a summary of the available endpoints:

#### Alunos (Students)

*   `GET /api/alunos`: List all students.
*   `POST /api/alunos`: Create a new student.
*   `GET /api/alunos/{id}`: Get a student by ID.
*   `PUT /api/alunos/{id}`: Update a student by ID.
*   `DELETE /api/alunos/{id}`: Delete a student by ID.
*   `GET /api/alunos/cpf/{cpf}`: Get a student by CPF.
*   `GET /api/alunos/matricula/{matricula}`: Get a student by matricula (enrollment number).

#### Professores (Teachers)

*   `GET /api/professores`: List all teachers.
*   `POST /api/professores`: Create a new teacher.
*   `GET /api/professores/{id}`: Get a teacher by ID.
*   `POST /api/professores/{id}`: Update a teacher by ID.
*   `DELETE /api/professores/{id}`: Delete a teacher by ID.
*   `GET /api/professores/cpf/{cpf}`: Get a teacher by CPF.
*   `GET /api/professores/departamento/{departamento}`: Get teachers by department.

## Health Check

The application exposes a health check endpoint provided by Spring Boot Actuator:

[http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
