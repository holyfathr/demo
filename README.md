# Инструкция по развертыванию

## Шаги

1. **Компиляция проекта**: Выполните команду в корне проекта:

   ```bash
   mvn clean install
   ```

2. **Запуск сервисов**: Используйте Docker Compose для запуска сервисов:

   ```bash
   docker-compose up --build
   ```

3. **Доступ к сервисам**:
    - **PostgreSQL**: порт `5432`, пользователь: `user`, пароль: `password`, база данных: `db`.
    - **API Gateway**: `http://localhost:8080`.
    - **Core Service**: `http://localhost:8081`.

4. **Остановка сервисов**:

   ```bash
   docker-compose down
   ```
