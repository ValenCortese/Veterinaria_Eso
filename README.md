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

La API de dueños está disponible únicamente mediante la ruta `/api/duenios`.

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

### Medicamentos

- `GET /api/medicamentos` - Lista todos los medicamentos.
- `GET /api/medicamentos/{id}` - Busca un medicamento por ID.
- `POST /api/medicamentos` - Crea un medicamento.
- `PUT /api/medicamentos/{id}` - Actualiza un medicamento.
- `DELETE /api/medicamentos/{id}` - Elimina un medicamento.
- `GET /api/turnos/{id}/medicamentos` - Lista los medicamentos recetados en
  un turno.
- `POST /api/turnos/{turnoId}/medicamentos/{medicamentoId}` - Asocia un
  medicamento al turno y descuenta una unidad del stock. Devuelve `422` si
  no hay stock disponible.

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

### Crear medicamento

```json
{
  "nombre": "Amoxicilina",
  "principioActivo": "Amoxicilina trihidrato",
  "stock": 25,
  "precioUnitario": 1250.50
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
- `Medicamento`: medicamento disponible para receta, con nombre, principio
  activo, stock y precio unitario.
- `Veterinario`: profesional que atiende la consulta.
- `EstadoTurno`: `PENDIENTE`, `EN_CURSO`, `FINALIZADO` o `CANCELADO`.

## Arquitectura

La aplicación es un monolito modular: todos los módulos se ejecutan dentro
del mismo proceso, se empaquetan y se despliegan juntos, y utilizan MySQL como
persistencia principal. El análisis de sus ventajas y limitaciones está en
[`docs/analisis-monolito.md`](docs/analisis-monolito.md).

## Autor

- Cortese Valentino

## Parcial 1 — Decisiones de diseño

### Relación Turno–Medicamento: explicá el tipo de relación elegida y por qué.

La relación entre `Turno` y `Medicamento` se modeló con `ManyToMany`. Un turno 
puede tener más de un medicamento recetado. Al mismo tiempo, un mismo medicamento 
pertenece al catálogo y puede aparecer en muchos turnos diferentes. JPA persiste esta 
relación con una tabla intermedia `turnos_medicamentos`, evitando duplicar los datos 
descriptivos del medicamento. Esta desición me parece la correcta mientras la receta no 
necesite atributos propios como dosis, frecuencia o durante cuanto timepo se debe
tomar el medicamento.

### Validación de stock: explicá cómo y en qué capa implementaste el control.

La validación de stock se implementó en `MedicamentoService`, dentro del método
que relaciona un medicamento con un turno. Primero se buscan el turno y el
medicamento por sus IDs correspondientes, y se da el codigo de estado de 
respuesta HTTP `404` si alguno no existe. Luego se verifica que el stock sea 
mayor que cero antes de modificar la relación. Cuando no hay unidades 
disponibles, se ejecuta la excepción `StockInsuficienteException`, que 
`GlobalException` transforma en un `ErrorResponse` con codigo de estado de 
respuesta HTTP `422`. Si las validaciones son exitosas, el servicio agrega 
la asociación y descuenta una unidad dentro de una transacción.

### Solapamiento: describí el algoritmo de detección (¿qué consulta hacés? ¿qué parámetros comparás?).

El algoritmo de detección se ejecuta en `TurnoService` antes de guardar un 
turno nuevo. El repositorio de turno consulta si existe un turno usando el 
ID del veterinario, la fecha y la hora seleccionadas. Los tres valores 
deben coincidir para considerar que dos turnos ocupan el mismo espacio en 
la agenda. Si la consulta encuentra una coincidencia, se recupera el 
primer turno conflictivo con esos mismos valores. La excepción lanzada 
incluye el ID, la fecha y la hora existentes, y el handler global responde 
con el codigo HTTP `409 Conflict`.

### Cupo de mascotas: describí la consulta y el criterio de "mascotas activas".

Cuando se crea una mascota, `MascotaService` busca primero al dueño para confirmar
que exista. Después ejecuta `countByDuenioId`, que cuenta los registros
asociados a ese dueño en la tabla `mascotas`. La creación se rechaza cuando 
la cuenta de mascotas de un dueño es mayor o igual a cinco, porque la nueva mascota 
ocuparía una posición adicional. En el modelo actual no existe un campo de baja de 
mascotas ni un estado para una mascota. Por ese motivo, se consideran activas todas 
las mascotas que se encuentran en la base de datos y solo dejan de contarse cuando 
se eliminan directamente, devolviendo un codigo HTTP `422` cuando se alcanza el 
límite.

### Decisión más difícil: contá cuál fue el punto más complejo y cómo lo resolviste.

El punto más complejo fue mantener las reglas de negocio en los servicios sin
exponer entidades JPA desde los endpoints. La asociación de medicamentos
requería actualizar al mismo tiempo una colección y el stock disponible, por
lo que se resolvió con una operación transaccional en `MedicamentoService`.
También era necesario diferenciar los errores de recursos inexistentes, faltas de
stock, solapamiento y exceso de mascotas mediante excepciones específicas.
`GlobalException` centraliza la conversión de esas excepciones a respuestas
HTTP consistentes, incluyendo el mensaje útil para cuando se consume la API.
De esta forma, los controladores quedan enfocados en HTTP y la lógica puede
ser reutilizada y probada en la capa de servicio.