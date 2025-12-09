# 🚗 GoGo Carsharing

**GoGo** — це сучасна система каршерінгу, розроблена на Spring Boot. Проект дозволяє керувати автопарком, додавати нові автомобілі та отримувати інформацію про доступні машини.

![Java](https://img.shields.io/badge/Java-23-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-green)
![Maven](https://img.shields.io/badge/Maven-3.8+-red)

![Java CI](https://github.com/artur-ai/GoGo/actions/workflows/sonarcloud.yml/badge.svg)

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

## 📋 Особливості

✅ Додавання нових автомобілів через REST API  
✅ Перегляд всього автопарку  
✅ Отримання випадкових автомобілів  
✅ Валідація даних через Bean Validation  
✅ DTO pattern для безпечної передачі даних  
✅ Логування операцій  

---

## 🛠 Технології

- **Java 23**
- **Spring Boot 3.4.0**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **Lombok** — зменшення boilerplate коду
- **PostgreSQL** — база даних
- **Maven** — система збірки
- **TestContainers DBRider** - тестування

---

## 📦 Встановлення та запуск

### Передумови
- Java 23+
- Maven 3.8+
- PostgreSQL

### Крок 1: Клонуйте репозиторій
```bash
git clone https://github.com/artur-ai/GoGo.git
cd GoGo
```

### Крок 2: Налаштуйте базу даних
Відредагуйте `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gogo_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update

gogo.settings.random-number=3
```

### Крок 3: Запустіть проект
```bash
mvn spring-boot:run
```

Додаток буде доступний за адресою: `http://localhost:8080`

---

## 🚀 API Endpoints

### 1️⃣ Отримати всі автомобілі
```http
GET /cars/all
```

**Відповідь:**
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

### 2️⃣ Отримати випадкові автомобілі
```http
GET /cars/random
```

Повертає випадкову кількість автомобілів (налаштовується через `gogo.settings.random-number`)

---

### 3️⃣ Додати новий автомобіль
```http
POST /cars/add
Content-Type: application/json
```

**Тіло запиту:**
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

**Відповідь (201 Created):**
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

## 📁 Структура проекту
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

## 🧪 Тестування

### Через Postman
1. Імпортуйте колекцію з папки `/postman`
2. Запустіть тести

### Через cURL
```bash
# Додати автомобіль
curl -X POST http://localhost:8080/cars/add \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Mercedes",
    "model": "E-Class",
    "year": 2024,
    "fuelType": "Hybrid",
    "engine": "2.0L Turbo",
    "pricePerMinute": 2.8,
    "pricePerDay": 180.00,
    "insurancePrice": 28.00,
    "imageUrl": "https://example.com/mercedes.jpg"
  }'

# Отримати всі автомобілі
curl http://localhost:8080/cars/all
```

---

## 🔧 Валідація

Проект використовує Bean Validation для перевірки даних:

- **brand** — не може бути порожнім, макс 50 символів
- **model** — не може бути порожнім, макс 50 символів
- **year** — від 1900 до 2030
- **fuelType** — не може бути порожнім
- **engine** — не може бути порожнім
- **pricePerMinute** — має бути позитивним числом
- **pricePerDay** — має бути позитивним числом
- **insurancePrice** — не може бути від'ємним

Приклад помилки валідації:
```json
{
  "brand": "Brand cannot be empty",
  "year": "Year must be after 1900",
  "pricePerMinute": "Price per minute must be positive"
}
```

---

## 🎯 Майбутні покращення

- [ ] Додати геолокацію автомобілів
- [ ] Додати систему рейтингів та відгуків
- [ ] Інтегрувати платіжну систему
- [ ] Додати Docker Compose для легкого розгортання

---

## 👨‍💻 Автор

**Майборода Артур**  
GitHub: [@artur-ai](https://github.com/artur-ai)

---

## 🤝 Contribution

Буду радий вашим pull request'ам! Для значних змін спочатку відкрийте issue для обговорення.

1. Fork проект
2. Створіть feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit зміни (`git commit -m 'Add some AmazingFeature'`)
4. Push в branch (`git push origin feature/AmazingFeature`)
5. Відкрийте Pull Request

---

⭐ **Якщо проект вам сподобався, поставте зірочку!** ⭐
