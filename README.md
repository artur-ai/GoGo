🚗 GoGo Carsharing
GoGo is a modern carsharing management system built with Spring Boot. The project provides a RESTful API for managing vehicle fleets, adding new cars, and retrieving information about available vehicles.

![Java](https://img.shields.io/badge/Java-23-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-green)
![Gradle](https://img.shields.io/badge/Maven-3.8+-red)

**Java CI**

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=bugs)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=artur-ai_GoGo&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=artur-ai_GoGo)

---

📋 Features
✅ Add new cars via REST API
✅ View the entire vehicle fleet
✅ Get random cars for quick selection
✅ DTO pattern for secure data transfer
✅ Input validation and error handling
✅ Clean architecture with service layer 

---

## 🚀 API Endpoints

### 1️⃣ Get all cars
``` /GET/api/cars
```

**Response:**
```json
[
  {
    "id": 1,
    "brand": "Skoda",
    "model": "Fabia",
    "year": 2013,
    "fuelType": "Petrol/Gas",
    "engine": "1.2L",
    "pricePerMinute": 2.2 ,
    "pricePerDay": 600,
    "insurancePrice": 0.96,
    "imageUrl": "[https://example.com/tesla.jpg](https://res.cloudinary.com/de6b0q56z/image/upload/v1762162805/skoda-fabia_nvxaiq.png)",
    "createdAt": "2024-12-01T10:30:00"
  }
]
```

---

### 2️⃣ Get random car
```
/GET /cars/random
```
Return random number of car(configured via`gogo.settings.random-number`)

---

### 3️⃣ Add new car
```http
POST /cars/add
Content-Type: application/json
```

**Body:**
```json
{
  "brand": "BMW",
  "model": "X5",
  "year": 2023,
  "fuelType": "Diesel",
  "engine": "3.0L I6",
  "pricePerMinute": 3.0,
  "pricePerDay": 200.00,
  "insurancePrice": 30.00,
  "imageUrl": "https://example.com/bmw.jpg"
}
```

**Response(201 Created):**
```json
{
  "id": 2,
  "brand": "BMW",
  "model": "X5",
  "year": 2023,
  "fuelType": "Diesel",
  "engine": "3.0L I6",
  "pricePerMinute": 3.0,
  "pricePerDay": 200.00,
  "insurancePrice": 30.00,
  "imageUrl": "https://example.com/bmw.jpg",
  "createdAt": "2024-12-01T11:45:30"
}
```

---

## 📁 Project structure
```
GoGo/
├── src/
│   ├── main/
│   │   ├── java/com/maiboroda/GoGo/
│   │   │   ├── controller/
│   │   │   │   └── CarController.java
│   │   │   ├── dto/
│   │   │   │   ├── CarRequestDto.java
│   │   │   │   └── CarResponseDto.java
│   │   │   ├── entity/
│   │   │   │   └── Car.java
│   │   │   ├── repository/
│   │   │   │   └── CarRepository.java
│   │   │   ├── service/
│   │   │   │   ├── CarService.java
│   │   │   │   └── CarServiceImpl.java
│   │   │   └── GoGoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
├── pom.xml
└── README.md
```

---

## 🧪 Testing

### Via Postman
1. Import collection from `/postman`
2. Run tests

---

## 👨‍💻 Author

**Maiboroda Artur**  
GitHub: [@artur-ai](https://github.com/artur-ai)

---

## 🤝 Contribution

I would be happy to receive your pull requests! For significant changes, please open an issue for discussion first

---

⭐ **If you liked the project, please give it a star!** ⭐
