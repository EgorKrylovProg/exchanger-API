# 💱 Проект "Обмен валют"

## 📌 Описание проекта

REST API для описания валют и обменных курсов. Позволяет просматривать и редактировать списки валют и обменных курсов, и совершать расчёт конвертации произвольных сумм из одной валюты в другую.

## 🎯 Мотивация проекта

Этот проект разработан в учебных целях для изучения:

- MVC-архитектуры.
- Корректного использования HTTP-кодов ответа.
- Развёртывания на сервере Tomcat .

## 🛠 Технологии

- **Java** – основной язык программирования.
- **Maven** – управление зависимостями.
- **Java-сервлеты** – обработка HTTP-запросов.
- **SQLite** – лёгкая реляционная база данных.
- **JDBC** – взаимодействие с БД.
- **Tomcat** – сервер для развертывания.

## 🔄 Работа с базой данных

API реализует CRUD-операции с валютами и обменными курсами.

## Методы API

### 📌 Работа с валютами

#### 🔹 Получение списка валют

**Запрос:**

```http
GET /currencies
```

**Ответ:**

```json
[
    {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },   
    {
        "id": 0,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    }
]
```

#### 🔹 Получение конкретной валюты

**Запрос:**

```http
GET /currency/EUR
```

**Ответ:**

```json
{
    "id": 0,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

#### 🔹 Добавление валюты

**Запрос:**

```http
POST /currencies?name=Euro&code=EUR&sign=€
```

**Ответ:**

```json
{
    "id": 0,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
```

### 📌 Работа с обменными курсами

#### 🔹 Получение всех курсов

**Запрос:**

```http
GET /exchangeRates
```

**Ответ:**

```json
[
    {
        "id": 0,
        "baseCurrency": {
            "id": 0,
            "name": "United States dollar",
            "code": "USD",
            "sign": "$"
        },
        "targetCurrency": {
            "id": 1,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        },
        "rate": 0.99
    }
]
```

#### 🔹 Получение конкретного курса

**Запрос:**

```http
GET /exchangeRate/USDRUB
```

**Ответ:**

```json
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 80
}
```

#### 🔹 Добавление курса

**Запрос:**

```http
POST /exchangeRates?baseCurrencyCode=USD&targetCurrencyCode=EUR&rate=0.99
```

**Ответ:**

```json
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    "rate": 0.99
}
```

#### 🔹 Обновление курса

**Запрос:**

```http
PATCH /exchangeRate/USDRUB?rate=80
```

**Ответ:**

```json
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 80
}
```

### 📌 Конвертация валют

**Запрос:**

```http
GET /exchange?from=USD&to=AUD&amount=10
```

**Ответ:**

```json
{
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "name": "Australian dollar",
        "code": "AUD",
        "sign": "A$"
    },
    "rate": 1.45,
    "amount": 10.00,
    "convertedAmount": 14.50
}
```

### ⚠ Ошибки

**Пример ответа:**

```json
{
    "message": "Валюта не найдена"
}
```

## 🚀 Деплой на Tomcat

1. **Клонирование репозитория:**

   ```bash
   git clone <URL_репозитория>
   ```

2. **Сборка проекта:**

   ```bash
   mvn clean package  # для Maven
   ```

3. **Создание WAR-файла:** Файл появится в `target/` (Maven).

4. **Размещение в Tomcat:** Переместите WAR в `webapps/`.

5. **Запуск сервера:**

   ```bash
   /path/to/tomcat/bin/startup.sh
   ```

6. **Доступ к API:** Перейдите в браузере:

   ```
   http://localhost:8080/exchanger-API
   ```
