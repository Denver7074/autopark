# Тестовое задание "Управление атопарком"
Сервис позволяет создвать диллера, потребителя (владельца в будущем авто). Машина поступает сначала к диллеру, после этого машину можно передать/продать/назначить владельца.
## Условие задания
Первая часть:
1. Создать сервис, с использованием фреймворка Spring управляющей информацией  об автопарке.
2. У каждой машины есть уникальный номер, дата сборки и она принадлежит Владельцу
3. Владелец машины имеет ФИО, телефон, электронную почту и машины которыми владеет.
3. Дилер это организация у которой есть название, электронная почта, ФИО представителя и она обслуживает несколько пользователей.
5. Сервис позволяет создавать владельцев машин, машины и дилеров. Назначать и удалять машины владельцам, назначать и удалять владельцев дилерам.
6. Сервис должен выводить информацию по каждому дилеру (какие у дилеров обслуживаются владельцы, какие у дилера обслуживаются машины), по каждому владельцу какими машинами он владеет и подробную информацию о каждой машине.
7. Информацию необходимо хранить в базе данных.
 
Вторая часть:
1. Разработать в сервисе возможность загрузки файла с GPS логом (файл прилагается), посчитать и вывести пройденный путь. Если скорость равна нулю, изменение координат в пройденном пути не
учитывать.
## Требование к запуску
- Java 17
- Spring Boot 3.2.0
- PostgreSQL 15-alpine
- Docker 24.0.6
- Сборщик проектов Gradle
## Развертывание PostgreSQL с помощью Docker Compose
```yaml
version: "3.9"

services:
  postgres:
    container_name: postgres
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: "test"
      POSTGRES_USER: "test"
      POSTGRES_PASSWORD: "test"
    ports:
      - "5432:5432"
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U habrpguser -d habrdb"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s
    restart: unless-stopped
    networks:
      - postgres

networks:
  postgres:
    driver: bridge
```
## Конфигурации application.properties
### PostgreSQL
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=test
spring.datasource.password=test
spring.datasource.hikari.schema=test
```
### Установка максимального размера файла
```bash
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```
## Доступ к Swagger UI
Swagger UI доступен по следующему адресу:
```uri
http://localhost:8080/swagger-ui/index.html
```
## Метод создания автовладельца `POST`
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка

`Exception`:
- `E002` - Отсутствуют обязательные поля
- `E003` - Неккоректный ввод email

Пример cURL запроса:
```bash
curl -X 'POST' \
  'http://localhost:8080/owner/add' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "fullNameOwner": "example",
  "numberPhone": "example",
  "email": "example@yandex.ru"
}'
```
Пример ответа:
```json
{
  "isFinish": true,
  "data": {
    "fullNameOwner": "example",
    "numberPhone": "example",
    "email": "example@yandex.ru",
    "carResponses": []
  }
}
```
## Метод создания диллера `POST`
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка

`Exception`:
- `E002` - Отсутствуют обязательные поля
- `E003` - Неккоректный ввод email

Пример cURL запроса:
```bash
curl -X 'POST' \
  'http://localhost:8080/seller/add' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "nameCompany": "example",
  "emailCompany": "example@yandex.ru",
  "fullNameWorker": "example"
}'
```
Пример ответа:
```json
{
  "isFinish": true,
  "data": {
    "nameCompany": "example",
    "emailCompany": "example@yandex.ru",
    "fullNameWorker": "example",
    "carResponses": []
  }
}
```
## Основные методы у авто
### Метод создания автомобиля `POST`
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка
`Exception`:
- `E002` - Отсутствуют обязательные поля
- `E004` - Машина в vin номером уже сществует

Пример cURL запроса:
  ```bash
  curl -X 'POST' \
  'http://localhost:8080/car/1/add' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "vin": "example",
  "buildDate": "2024-01-31"
}'
  ```
Пример ответа:
```json
{
  "isFinish": true,
  "data": {
    "archived": false,
    "vin": "example",
    "buildDate": "2024-01-31",
    "owner": null,
    "seller": {
      "archived": false,
      "nameCompany": "string",
      "emailCompany": "denvr7074@yandex.ru",
      "fullNameWorker": "string"
    }
  }
}
```
### Метод смены диллера у автомобиля `PUT`
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка

`Exception`:
- `E001` - Сущность с id не найдена

Пример cURL запроса:
  ```bash
  curl -X 'PUT' \
  'http://localhost:8080/car/1/change/2' \
  -H 'accept: */*'
  ```
  Пример ответа:
```json
{
  "isFinish": true,
  "data": {
    "archived": false,
    "vin": "den",
    "buildDate": "2024-01-30",
    "owner": {
      "archived": false,
      "fullNameOwner": "string",
      "numberPhone": "string",
      "email": "dfgjjgf@yndex.ru"
    },
    "seller": {
      "archived": false,
      "nameCompany": "string",
      "emailCompany": "sdfsdf@yandex.ru",
      "fullNameWorker": "string"
    }
  }
}
```
### Метод смены владельца у автомобиля `PUT`
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка

`Exception`:
- `E001` - Сущность с id не найдена

Пример cURL запроса:
  ```bash
  curl -X 'PUT' \
  'http://localhost:8080/car/1/buy/2' \
  -H 'accept: */*'
  ```
Пример ответа:
```json
{
  "isFinish": true,
  "data": {
    "archived": false,
    "vin": "den",
    "buildDate": "2024-01-30",
    "owner": {
      "archived": false,
      "fullNameOwner": "example",
      "numberPhone": "example",
      "email": "example@yandex.ru"
    },
    "seller": {
      "archived": false,
      "nameCompany": "string",
      "emailCompany": "sdfsdf@yandex.ru",
      "fullNameWorker": "string"
    }
  }
}
```
## Метод получения диллера по id
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка

`Exception`:
- `E001` - Сущность с id не найдена

Пример cURL запроса:
```bash
curl -X 'GET' \
  'http://localhost:8080/seller/find/1' \
  -H 'accept: */*'
```

Пример ответа:
```json
{
  "isFinish": true,
  "data": {
    "nameCompany": "string",
    "emailCompany": "denvr7074@yandex.ru",
    "fullNameWorker": "string",
    "carResponses": [
      {
        "vin": "example",
        "buildDate": "2024-01-31",
        "ownerResponse": null
      },
      {
        "vin": "den3446546",
        "buildDate": "2024-01-30",
        "ownerResponse": null
      },
      {
        "vin": "den346546546",
        "buildDate": "2024-01-30",
        "ownerResponse": null
      },
      {
        "vin": "den346546",
        "buildDate": "2024-01-30",
        "ownerResponse": null
      },
      {
        "vin": "den34",
        "buildDate": "2024-01-30",
        "ownerResponse": null
      },
      {
        "vin": "den2234",
        "buildDate": "2024-01-30",
        "ownerResponse": null
      }
    ]
  }
```
Аналогично для двух остальных сущностей существуют такой же метод.
## Метод получения диллеров
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка

Пример cURL запроса:
```bash
curl -X 'GET' \
  'http://localhost:8080/seller/findAll' \
  -H 'accept: */*'
```

Пример ответа:
```json
{
  "isFinish": true,
  "data": [
    {
      "nameCompany": "string",
      "emailCompany": "denvr7074@yandex.ru",
      "fullNameWorker": "string",
      "carResponses": [
        {
          "vin": "example",
          "buildDate": "2024-01-31",
          "ownerResponse": null
        },
        {
          "vin": "den3446546",
          "buildDate": "2024-01-30",
          "ownerResponse": null
        },
        {
          "vin": "den346546546",
          "buildDate": "2024-01-30",
          "ownerResponse": null
        },
        {
          "vin": "den346546",
          "buildDate": "2024-01-30",
          "ownerResponse": null
        },
        {
          "vin": "den34",
          "buildDate": "2024-01-30",
          "ownerResponse": null
        },
        {
          "vin": "den2234",
          "buildDate": "2024-01-30",
          "ownerResponse": null
        }
      ]
    },
    {
      "nameCompany": "string",
      "emailCompany": "sdfsdf@yandex.ru",
      "fullNameWorker": "string",
      "carResponses": [
        {
          "vin": "den",
          "buildDate": "2024-01-30",
          "ownerResponse": {
            "fullNameOwner": "example",
            "numberPhone": "example",
            "email": "example@yandex.ru"
          }
        }
      ]
    },
    {
      "nameCompany": "string",
      "emailCompany": "shgfhuretwrw@yandex.ru",
      "fullNameWorker": "string",
      "carResponses": []
    },
    {
      "nameCompany": "string",
      "emailCompany": "sdfsdfhhhsssss@gmail.com",
      "fullNameWorker": "string",
      "carResponses": []
    },
    {
      "nameCompany": "example",
      "emailCompany": "example@yandex.ru",
      "fullNameWorker": "example",
      "carResponses": []
    }
  ]
}
```
Аналогично для двух остальных сущностей существуют такой же метод.
## Метод получения диллеров
При первом удалении по id сущность помечается как удаленной, а при второй попытке удалить сущность, то сущность полностью удаляется из бд.
`@ResponseStatus`:
- `200` - запрос выполнен успешно
- `400` - возникла непредвиденная ошибка

`Exception`:
- `E001` - Сущность с id не найдена

Пример cURL запроса:
```bash
curl -X 'DELETE' \
  'http://localhost:8080/seller/1' \
  -H 'accept: */*'
```

Пример ответа:
```json
{
  "isFinish": true,
  "data": "Ok"
}
```
Аналогично для других двух сущностей.

