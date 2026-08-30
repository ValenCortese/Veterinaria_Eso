# Veterinaria Eso

API REST desarrollada con Java y Spring Boot para gestionar una clínica veterinaria.

## Descripción

El proyecto está centrado en la administración de dueños, mascotas, veterinarios y turnos de atención. La estructura actual ya incluye la capa de dominio, repositorios, servicios, DTOs, mappers, controladores y manejo de excepciones, con un backend funcional para la gestión veterinaria.

## Estado actual del proyecto

El sistema ya cuenta con las siguientes funcionalidades implementadas:

- Backend con Spring Boot 4.1.0
- Persistencia con Spring Data JPA y MySQL
- Entidades JPA para:
  - `Duenio`
  - `Mascota`
  - `Turno`
  - `Veterinario`
  - `EstadoTurno`
- CRUD completo para dueños (`Duenio`)
- CRUD completo para mascotas (`Mascota`)
- CRUD completo para veterinarios (`Veterinario`)
- CRUD de turnos con lógica de negocio
- Consulta de agenda por veterinario y fecha
- Cambio de estado de turnos (`PATCH`)
- DTOs y mappers para desacoplar entidades del API
- Manejo de errores con `ResourceNotFoundException` y validaciones básicas
- Prueba base de arranque del contexto Spring (`contextLoads`)

La base del proyecto ya está funcionando y la lógica principal del negocio está implementada. 

## Stack tecnológico

- Java 21
- Maven
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- MySQL Connector J
- Lombok
- JUnit 5

## Requisitos previos

- Java 21 o superior
- Maven instalado
- MySQL en `localhost:3306`
- Base de datos creada con el nombre: `veterinaria_eso`

## Configuración

Ajustá la conexión en `src/main/resources/application.properties`:

```properties
spring.application.name=veterinaria-eso
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_eso?useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_username
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.port=8080
```

> La aplicación usa JPA para crear y actualizar automáticamente las tablas según las entidades.

## Ejecución

Desde la raíz del proyecto:

Linux/macOS:

```bash
./mvnw clean package
./mvnw spring-boot:run
```

Windows:

```powershell
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

También podés levantar la app desde tu IDE ejecutando la clase `VeterinariaEsoApplication`.

## Endpoints actuales

### Dueños

- `GET /api/duenios` → Lista todos los dueños
- `GET /api/duenios/{id}` → Busca un dueño por ID
- `GET /api/duenios/email/{email}` → Busca un dueño por email
- `GET /api/duenios/{id}/mascotas` → Lista las mascotas de un dueño
- `GET /api/duenios/nombre/{nombre}` → Busca un dueño por nombre
- `POST /api/duenios` → Crea un dueño
- `PUT /api/duenios/{id}` → Actualiza un dueño
- `DELETE /api/duenios/{id}` → Elimina un dueño

### Mascotas

- `GET /api/mascotas` → Lista todas las mascotas
- `GET /api/mascotas/{id}` → Busca una mascota por ID
- `POST /api/mascotas?duenioId={id}` → Crea una mascota asociada a un dueño
- `PUT /api/mascotas/{id}` → Actualiza una mascota
- `DELETE /api/mascotas/{id}` → Elimina una mascota

### Veterinarios

- `GET /api/veterinarios` → Lista todos los veterinarios
- `GET /api/veterinarios/{id}` → Busca un veterinario por ID
- `POST /api/veterinarios` → Crea un veterinario
- `PUT /api/veterinarios/{id}` → Actualiza un veterinario
- `DELETE /api/veterinarios/{id}` → Elimina un veterinario

### Turnos

- `GET /api/turnos` → Lista todos los turnos
- `GET /api/turnos/{id}` → Busca un turno por ID
- `GET /api/turnos/agenda?veterinarioId={id}&fecha={yyyy-MM-dd}` → Agenda por veterinario y fecha
- `POST /api/turnos` → Crea un turno
- `PATCH /api/turnos/{id}/estado?estado={PENDIENTE|EN_CURSO|FINALIZADO|CANCELADO}&observaciones={textoOpcional}` → Actualiza estado del turno
- `DELETE /api/turnos/{id}` → Elimina un turno

## Ejemplos de payload

### Crear dueño

```json
{
  "nombre": "Valentino",
  "apellido": "Cortese",
  "cedula": "12345678",
  "telefono": 1122334455,
  "email": "valentino@email.com"
}
```

### Crear mascota

```json
{
  "nombre": "Luna",
  "especie": "Perro",
  "raza": "Golden Retriever",
  "fechaNacimiento": "2021-05-10"
}
```

### Crear veterinario

```json
{
  "nombre": "María",
  "apellido": "González",
  "matricula": "MN-2045",
  "especialidad": "Cardiología"
}
```

### Crear turno

```json
{
  "fecha": "2026-08-30",
  "hora": "10:30:00",
  "motivo": "Control general",
  "mascotaId": 1,
  "veterinarioId": 1
}
```

## Estructura principal del proyecto

```text
Veterinaria_Eso/
├── src/
│   ├── main/
│   │   ├── java/com/veterinariaEso/
│   │   │   ├── Controller/
│   │   │   ├── DTO/
│   │   │   ├── Exception/
│   │   │   ├── Mapper/
│   │   │   ├── Model/
│   │   │   ├── Repository/
│   │   │   ├── Service/
│   │   │   └── VeterinariaEsoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/veterinariaEso/
│           └── VeterinariaEsoApplicationTests.java
├── docs/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
├── HELP.md
└── .gitignore
```

## Modelo de dominio

Las entidades principales están definidas con relaciones JPA y representan el funcionamiento básico de la clínica:

- `Duenio`: propietario del animal, con datos personales.
- `Mascota`: animal asociado a un dueño.
- `Turno`: cita programada con motivo, fecha, hora, veterinario y mascota.
- `Veterinario`: profesional responsable de atención.
- `EstadoTurno`: enum con los estados: `PENDIENTE`, `EN_CURSO`, `FINALIZADO`, `CANCELADO`.

## Autor

- Cortese Valentino
