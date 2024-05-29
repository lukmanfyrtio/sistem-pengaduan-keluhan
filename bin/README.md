# Sistem Aplikasi Pengaduan Warga
Sistem Aplikasi Pengaduan Warga is a web application designed to manage complaints from residents. It provides functionality for submitting, viewing, and managing complaints by administrators.
The list of tools required to build and run the project:

- Java 11
- Spring Boot 2.7.18
- Spring Data JPA
- MySQL
- Swagger (for API documentation)
- Maven (for dependency management)

## Features

- Submit a new complaint
- View all complaints
- Filter complaints by creation date
- Filter complaints by status
- Paginate through the list of complaints
- View detailed information about a specific complaint
- Update the status of a complaint
- Log status changes in a trace table

Requirements for particular branch are provided in branch `README.md` file.

## Building

In order to build project use:

```bash
mvn clean package
```

If your default `java` is not from JDK 11 use (in `simple-rpg` directory):

```bash
JAVA_HOME=<path_to_jdk_home> mvn package
```

## Configuration

Configuration can be updated in `application.properties` or using environment variables.

## Running

In order to run using embedded Apache Tomcat server use:

```bash
java -jar <module>/target/app.jar
```

If your default `java` is not from JDK 11 or higher use:

```bash
<path_to_jdk_home>/bin/java -jar <module>/target/app.jar
```
## Access the application:
Open your web browser and go to http://localhost:8080/api/1.0/swagger-ui.html to access the Swagger UI for testing the APIs.
You can also use tools like Postman to test the APIs directly.

## Author

Copyright &copy; 2023 , Lukman (lukman.fyrtio@gmail.com)


