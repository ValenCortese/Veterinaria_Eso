# Veterinaria Eso

API REST desarrollada con Java y Spring Boot para gestionar información de una veterinaria.

## Descripción

Este proyecto implementa una base para la gestión de dueños, mascotas, veterinarios y turnos. Actualmente, la funcionalidad principal disponible es el CRUD de dueños, y el resto de las entidades quedaron modeladas para continuar con la expansión del sistema.

## Stack tecnológico

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- MySQL Connector
- Maven
- Lombok

## Requisitos previos

- Java 21 o superior
- Maven
- MySQL corriendo en localhost:3306
- Base de datos creada con el nombre `veterinaria_eso`

## Configuración de la base de datos

Asegurate de tener un usuario y contraseña válidos en `src/main/resources/application.properties` y ajustar la conexión si es necesario:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_eso?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

> La aplicación usa JPA para crear/actualizar automáticamente las tablas según las entidades.

## Ejecutar el proyecto

Desde la raíz del proyecto:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

También podés levantar la app desde tu IDE ejecutando la clase principal `VeterinariaEsoApplication`.

## Endpoints disponibles

### Dueños

- GET `/api/duenio` → lista todos los dueños
- GET `/api/duenio/{id}` → obtiene un dueño por ID
- POST `/api/duenio` → crea un nuevo dueño
- PUT `/api/duenio/{id}` → actualiza un dueño existente
- DELETE `/api/duenio/{id}` → elimina un dueño

### Ejemplo de creación de dueño

```json
{
  "nombre": "Valentino",
  "apellido": "Cortese",
  "cedula": "12345678",
  "telefono": 1122334455,
  "email": "valentino@email.com"
}
```

## Estructura del proyecto

```text
src/
├── main/
│   ├── java/com/veterinariaEso/
│   │   ├── Controller/
│   │   ├── Exception/
│   │   ├── Model/
│   │   ├── Repository/
│   │   ├── Service/
│   │   └── VeterinariaEsoApplication.java
│   └── resources/
│       └── application.properties
└── test/
```

## Entidades modeladas

- `Duenio`
- `Mascota`
- `Turno`
- `Veterinario`
- `EstadoTurno`

## Estado actual

El proyecto está en una etapa inicial con la base de datos y el módulo de dueños funcionando. El siguiente paso natural es continuar con los CRUD de mascotas, veterinarios y turnos, junto con validaciones y documentación de endpoints adicionales.

## Autor

- Cortese Valentino
