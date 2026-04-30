# Job Scraper – Techstars

## 📌 Description

This project is a Spring Boot application that scrapes job vacancies from
https://jobs.techstars.com/jobs and provides a REST API to access the data.

The application collects and stores job vacancies in a database and allows users to retrieve them with filtering and pagination.

---
## 📊 Data Dump

Sample scraped data is available in:

```
/data/vacancies.csv
/data/tags.csv
```

Or you can check it out using Google Sheets:
- vacancies: https://docs.google.com/spreadsheets/d/1-jYakiCDQje1GLZtni4AQ2ErbbZqRnHcFA25jMdA2vc/edit?usp=sharing
- tags https://docs.google.com/spreadsheets/d/1m_-hqRG9yo9oPJyxDnVgWHijfOTPar60qpejll78oNc/edit?usp=sharing

## 🚀 API Endpoints (example)
```
GET /vacancies
```

Supports pagination and filtering.


## ⚙️ Features

* Scrapes job listings using Playwright
* Stores data in a relational database (MySQL/PostgreSQL)
* REST API with pagination support
* Filtering vacancies by parameters
* Handles duplicates (update existing, add new, mark removed)
* Scheduled background scraping
* Data export (CSV)

---

## 🧠 Technical Overview

* Java 21
* Spring Boot
* Spring Data JPA (Hibernate)
* Playwright (for scraping dynamic content)
* Jsoup (HTML parsing)
* MySQL / PostgreSQL
* Maven

---
