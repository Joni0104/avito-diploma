# Дипломный проект Avito - Spring Boot Backend

## Описание
Backend-часть для сервиса объявлений. Реализованы: регистрация, авторизация, создание объявлений, комментарии.

## Технологии
- Java 17
- Spring Boot 3.1.5
- Spring Security
- PostgreSQL / H2
- Swagger/OpenAPI
- MapStruct
- Liquibase

## Запуск
1. Клонируйте репозиторий
2. Запустите: `./gradlew bootRun`
3. Откройте: http://localhost:8080/swagger-ui.html

## Функциональность
- ✅ Регистрация и авторизация
- ✅ CRUD для объявлений  
- ✅ CRUD для комментариев
- ✅ Роли пользователей (USER/ADMIN)
- ✅ Загрузка изображений
- ✅ Документация API (Swagger)

## Тестирование
Используйте Swagger UI или Postman для тестирования API.
