# Veterinaria Eso

Aplicación monolítica para gestionar una clínica veterinaria. Incluye una API
REST desarrollada con Spring Boot, persistencia en MySQL y una interfaz web
simple para consultar y registrar dueños.

## Estado actual

El proyecto cuenta con:

- Java 21 y Spring Boot 4.1.0.
- API REST para Dueños, Mascotas, Veterinarios y Turnos.
- Spring Data JPA con MySQL.
- DTOs, validaciones, mappers, repositorios, servicios y controladores.
- Manejo global de errores para respuestas `400`, `404`, `409` y `500`.
- Documentación OpenAPI/Swagger, incluyendo `@Operation`, `@ApiResponse` y
  `@Schema` en los endpoints y DTOs documentados.
- Frontend web en HTML, Bootstrap 5 CDN y JavaScript vanilla.
- `GET` y `POST` de dueños mediante Fetch API.
- Configuración CORS para usar el frontend desde Live Server.
- Análisis arquitectónico del monolito en `docs/analisis-monolito.md`.
- Colección Postman y diagramas en `docs/`.

## Stack tecnológico

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- MySQL Connector/J
- Spring Boot Validation
- Springdoc OpenAPI
- Lombok
- MapStruct
- Maven
- Bootstrap 5 CDN
- JavaScript vanilla

## Requisitos previos

- JDK 21 o superior.
- Variable de entorno `JAVA_HOME` configurada.
- MySQL ejecutándose en `localhost:3306`.
- Base de datos `veterinaria_eso` creada.

Maven Wrapper está incluido en el repositorio, por lo que no es necesario
instalar Maven globalmente.

## Configuración

La conexión a MySQL se configura en
`src/main/resources/application.properties`. No se deben subir contraseñas
reales al repositorio; se recomienda usar variables de entorno o un archivo de
configuración local.

Configuración principal:

```properties
spring.application.name=veterinaria-eso
spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria_eso?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=<tu-contraseña>
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

La aplicación usa `spring.jpa.hibernate.ddl-auto=update` para crear o
actualizar las tablas a partir de las entidades JPA.

## Ejecución

Desde la raíz del proyecto:

### Windows

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw clean package
./mvnw spring-boot:run
```

También se puede ejecutar la clase
`com.veterinariaEso.VeterinariaEsoApplication` desde el IDE.

Con la aplicación iniciada, la interfaz web se encuentra en:

```text
http://localhost:8080/
```

No se debe abrir `frontend/index.html` con `file:///`, porque en ese caso las
llamadas Fetch no se ejecutan contra el backend de Spring Boot. El mismo
frontend también se conserva en `frontend/index.html` para utilizarlo con
Live Server en `http://localhost:5500`.

## Frontend

El frontend servido por Spring Boot está en:

```text
src/main/resources/static/index.html
```

La página incluye:

- Navbar con el nombre de la clínica.
- Tabla de dueños.
- Modal para registrar un dueño.
- `GET /api/duenios` al cargar la página.
- `POST /api/duenios` al enviar el formulario.
- Mensajes para errores de API o de conexión.

Solo utiliza Bootstrap 5 desde CDN y JavaScript vanilla; no utiliza jQuery ni
otra librería JavaScript.

## Documentación de la API

Con la aplicación ejecutándose:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Especificación OpenAPI: `http://localhost:8080/v3/api-docs`

Los controladores incluyen descripciones de operaciones y respuestas HTTP
posibles. Los DTOs `DuenioDTO` y `TurnoRequestDTO` incluyen descripciones y
ejemplos de campos mediante `@Schema`.

## Endpoints

### Dueños

Las rutas `/api/duenios` y `/api/duenos` son alias equivalentes.

- `GET /api/duenios` - Lista todos los dueños.
- `GET /api/duenios/{id}` - Busca un dueño por ID.
- `GET /api/duenios/email/{email}` - Busca un dueño por email.
- `GET /api/duenios/{id}/mascotas` - Lista las mascotas de un dueño.
- `GET /api/duenios/nombre/{nombre}` - Busca un dueño por nombre.
- `POST /api/duenios` - Crea un dueño.
- `PUT /api/duenios/{id}` - Actualiza un dueño.
- `DELETE /api/duenios/{id}` - Elimina un dueño.

### Mascotas

- `GET /api/mascotas` - Lista todas las mascotas.
- `GET /api/mascotas/{id}` - Busca una mascota por ID.
- `POST /api/mascotas?duenioId={id}` - Crea una mascota asociada a un dueño.
- `PUT /api/mascotas/{id}` - Actualiza una mascota.
- `DELETE /api/mascotas/{id}` - Elimina una mascota.

### Veterinarios

- `GET /api/veterinarios` - Lista todos los veterinarios.
- `GET /api/veterinarios/{id}` - Busca un veterinario por ID.
- `POST /api/veterinarios` - Crea un veterinario.
- `PUT /api/veterinarios/{id}` - Actualiza un veterinario.
- `DELETE /api/veterinarios/{id}` - Elimina un veterinario.

### Turnos

- `GET /api/turnos` - Lista todos los turnos.
- `GET /api/turnos/{id}` - Busca un turno por ID.
- `GET /api/turnos/agenda?veterinarioId={id}&fecha={yyyy-MM-dd}` - Consulta la
  agenda de un veterinario.
- `POST /api/turnos` - Crea un turno.
- `PATCH /api/turnos/{id}/estado?estado={estado}&observaciones={texto}` -
  Actualiza el estado de un turno.
- `DELETE /api/turnos/{id}` - Elimina un turno.

## Ejemplos de payload

### Crear dueño

```json
{
  "id": 1,
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
  "nombre": "Maria",
  "apellido": "Gonzalez",
  "matricula": "MN-2045",
  "especialidad": "Cardiologia"
}
```

### Crear turno

```json
{
  "fecha": "2026-10-15",
  "hora": "10:30:00",
  "motivo": "Control general",
  "mascotaId": 1,
  "veterinarioId": 1
}
```

## Estructura principal

```text
Veterinaria_Eso/
├── docs/
│   ├── analisis-monolito.md
│   ├── CapturaTablas.png
│   ├── diagrama.png
│   └── veterinaria.postman_collection.json
├── frontend/
│   └── index.html
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
│   │   │   ├── config/
│   │   │   └── VeterinariaEsoApplication.java
│   │   └── resources/
│   │       ├── static/index.html
│   │       └── application.properties
│   └── test/
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Modelo de dominio

- `Duenio`: propietario del animal.
- `Mascota`: animal asociado a un dueño.
- `Turno`: cita con fecha, hora, motivo, estado, mascota y veterinario.
- `Veterinario`: profesional que atiende la consulta.
- `EstadoTurno`: `PENDIENTE`, `EN_CURSO`, `FINALIZADO` o `CANCELADO`.

La lógica de turnos impide que un veterinario tenga dos citas en la misma
fecha y hora.

## Arquitectura

La aplicación es un monolito modular: todos los módulos se ejecutan dentro
del mismo proceso, se empaquetan y se despliegan juntos, y utilizan MySQL como
persistencia principal. El análisis de sus ventajas y limitaciones está en
[`docs/analisis-monolito.md`](docs/analisis-monolito.md).

## Autor

- Cortese Valentino
