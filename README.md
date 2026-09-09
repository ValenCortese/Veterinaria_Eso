# Veterinaria Eso

API REST desarrollada en Java con Spring Boot para gestionar una clínica veterinaria. El proyecto reúne la estructura base del dominio y la capa REST, se encuentra en desarrollo y operativo.

## Estado actual del proyecto

La base funcional del backend ya está definida en el código:

- Java 21 + Spring Boot 4.1.0
- Spring Data JPA y MySQL como capa de persistencia
- Entidades JPA para `Duenio`, `Mascota`, `Turno`, `Veterinario` y `EstadoTurno`
- CRUD para dueños, mascotas, veterinarios y turnos
- Consulta de agenda por veterinario y fecha
- Cambio de estado de turnos mediante `PATCH`
- DTOs, mappers, repositorios, servicios y controladores organizados por capas
- Colección Postman y diagramas en la carpeta `docs/`
- Prueba de contexto Spring (`contextLoads`)

## Stack tecnológico

- Java 21
- Maven
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- MySQL Connector J
- Lombok
- MapStruct
- JUnit 5

## Requisitos previos

- Java 21 o superior
- Maven instalado
- MySQL corriendo en `localhost:3306`
- Base de datos creada con el nombre: `veterinaria_eso`

## Configuración

La conexión a MySQL se configura en `src/main/resources/application.properties`:

```properties
spring.application.name=veterinaria-eso
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_eso?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
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

También se puede levantar desde el IDE ejecutando `VeterinariaEsoApplication`.

## Endpoints actuales

### Dueños

- `GET /api/duenios` → Lista todos los dueños
- `GET /api/duenios/{id}` → Busca un dueño por ID
- `GET /api/duenios/email/{email}` → Busca un dueño por email
- `GET /api/duenios/{id}/mascotas` → Lista las mascotas asociadas a un dueño
- `GET /api/duenios/nombre/{nombre}` → Búsqueda por nombre (documentada en la colección Postman)
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
- `PATCH /api/turnos/{id}/estado?estado={PENDIENTE|EN_CURSO|FINALIZADO|CANCELADO}&observaciones={textoOpcional}` → Actualiza el estado del turno
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

## Documentación adicional

La carpeta `docs/` incluye:

- `veterinaria.postman_collection.json` con requests para probar la API
- `diagrama.png` y `CapturaTablas.png` con esquema y referencias del proyecto

## Estructura principal del proyecto

```text
Veterinaria_Eso/
├── docs/
│   ├── CapturaTablas.png
│   ├── diagrama.png
│   └── veterinaria.postman_collection.json
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
├── .gitignore
├── .gitattributes
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── .mvn/
```

## Modelo de dominio

Las entidades principales del sistema son:

- `Duenio`: propietario del animal
- `Mascota`: animal asociado a un dueño
- `Turno`: cita con motivo, fecha, hora, veterinario y mascota
- `Veterinario`: profesional que atiende la consulta
- `EstadoTurno`: enum con los valores `PENDIENTE`, `EN_CURSO`, `FINALIZADO` y `CANCELADO`

## Consideraciones

- El proyecto actualmente es un backend REST en desarrollo, no incluye frontend ni autenticación de usuarios.
- La lógica de turnos valida que un veterinario no tenga dos citas en la misma fecha y hora.
- El flujo de negocio está diseñado para extenderse con validaciones adicionales y más módulos.

## Autor

- Cortese Valentino