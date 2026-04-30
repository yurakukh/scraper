# Installation Guide

## Prerequisites

- **Java 21**
- **Maven 3.9+**
- **MySQL** (or compatible database)
- **Docker** (optional, for DB setup)

## Configuration

Application configuration is located in `application.properties`.

### Important Properties

```properties
scraper.max-vacancies=200
scraper.schedule.fixed-delay-ms=60000
spring.datasource.url=jdbc:mysql://localhost:3306/job_scraper
spring.datasource.username=root
spring.datasource.password=your_password
```

| Property | Description |
|----------|-------------|
| `scraper.max-vacancies` | Limits number of vacancies per scraping run (default: 200) |
| `scraper.schedule.fixed-delay-ms` | Scraping interval in milliseconds |
| `spring.datasource.url` | Database connection URL |
| `spring.datasource.username` | Database username |
| `spring.datasource.password` | Database password |

## Scheduled Scraping

- Runs based on `scraper.schedule.fixed-delay-ms`
- Each run processes up to `scraper.max-vacancies` vacancies

## Environment Variables

> **Note:** For simplicity of development and testing, sensitive configuration values are not externalized into a `.env` file. Instead, they are defined directly in `application.properties`.
>
> In a production-ready setup, these values should be moved to environment variables or a secure configuration source (e.g., `.env`, Vault, or cloud config services).

## Running the Application

Execute the following command:

```bash
mvn spring-boot:run
```

The application will start at: **http://localhost:8080**

## API Documentation

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **Base API path:** `/vacancies`
