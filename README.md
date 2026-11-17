# Veterinaria ESO

Sistema de gestión veterinaria construido como una arquitectura de
microservicios. Permite administrar dueños, mascotas, veterinarios y turnos,
con autenticación JWT y un frontend web servido por el Gateway.

## Arquitectura

| Componente | Puerto | Responsabilidad |
|---|---:|---|
| Config Server | 8888 | Configuración centralizada en perfil `native` |
| Eureka Server | 8761 | Registro y descubrimiento de servicios |
| API Gateway | 8080 | Entrada única, routing, JWT, CORS y rate limiting |
| ms-duenio | 8081 | CRUD de dueños |
| ms-mascota | 8082 | CRUD de mascotas |
| ms-veterinario | 8083 | CRUD y búsqueda de veterinarios |
| ms-turno | 8084 | Agenda y cancelación de turnos |
| ms-auth | 8085 | Registro, login y emisión de JWT |

Cada servicio de negocio tiene su propia base MySQL: `duenio_db`,
`mascota_db`, `veterinario_db`, `turno_db` y `auth_db`.

## Tecnologías y decisiones

- Java 21, Maven y Spring Boot.
- Spring Cloud Config para no duplicar configuración común.
- Eureka para que los servicios se encuentren por nombre.
- Spring Cloud Gateway como único punto de entrada público.
- Spring Data JPA y MySQL para persistencia independiente por contexto.
- OpenFeign en `ms-turno` para validar mascota y veterinario.
- Resilience4j con retry y circuit breaker para llamadas remotas.
- Spring Security, BCrypt y JWT HMAC para autenticación stateless.
- Redis para limitar solicitudes de turnos por IP.
- Frontend estático HTML/CSS/JavaScript con Bootstrap, login y registro.

La organización interna sigue un estilo hexagonal: controladores y DTOs son
adaptadores de entrada; los servicios contienen los casos de uso; los puertos
desacoplan el dominio; y JPA/Feign son adaptadores de salida.

## API principal

Todas estas rutas se consumen normalmente a través de `http://localhost:8080`.

| Recurso | Operaciones |
|---|---|
| `/auth` | `POST /register`, `POST /login` |
| `/api/duenios` | `GET`, `GET /{id}`, búsquedas por `email`/`nombre`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| `/api/mascotas` | `GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| `/api/veterinarios` | `GET`, `GET /{id}`, `GET /especialidad/{especialidad}`, `POST`, `PUT /{id}`, `DELETE /{id}` |
| `/api/turnos` | `GET`, `GET /{id}`, búsquedas por mascota/veterinario, `POST`, `PATCH /{id}/cancelar` |

Un turno nuevo comienza en estado `PENDIENTE`. Antes de guardarlo, `ms-turno`
consulta por Feign a mascotas y veterinarios. Cancelar es una transición de
estado a `CANCELADO`, no un borrado físico.

## Seguridad

El registro guarda la contraseña con BCrypt. El login devuelve un JWT con
usuario y rol; el filtro global del Gateway deja pasar solo `/auth/**` sin
token y exige `Authorization: Bearer <token>` para el resto. Al validar el
token, agrega `X-Auth-User` para los servicios downstream.

El secreto JWT está definido actualmente en la configuración de desarrollo.
Para producción debería moverse a un secreto externo. El acceso directo a los
microservicios no aplica el filtro del Gateway, por lo que deben quedar
protegidos en red.

## Ejecución rápida

Consultar [UP.md](UP.md) para requisitos, creación de bases, orden de arranque,
comandos Maven y prueba de autenticación. El orden es:

```text
MySQL + Redis → Config Server → Eureka → servicios → Gateway → frontend
```

Frontend (la primera pantalla es el acceso):

- <http://localhost:8080/auth.html>
- <http://localhost:8080/index.html>
- <http://localhost:8080/duenio.html>
- <http://localhost:8080/mascota.html>
- <http://localhost:8080/veterinario.html>
- <http://localhost:8080/turno.html>

La pantalla `auth.html` permite iniciar sesión o registrar usuarios. Después
del login, el token se guarda en `localStorage`; `app.js` lo agrega a las
peticiones CRUD y redirige al login si la sesión expira. La barra de navegación
incluye **Cerrar sesión**. El formulario deshabilita el botón mientras guarda
para evitar crear dos veces el mismo registro.

## Flujo de autenticación del frontend

`auth.html` consume `POST /auth/login` y `POST /auth/register`. El JWT
resultante se guarda en `localStorage` y se envía en cada llamada protegida.
Las páginas de gestión redirigen al login sin token y el menú compartido
permite cerrar sesión. El formulario deshabilita **Guardar** durante el envío
para evitar duplicados.

## Estado actual y consideraciones

- El frontend estático incluye login/registro, guarda el JWT en el navegador y
  lo envía automáticamente en las llamadas CRUD. Las páginas protegidas
  redirigen a `auth.html` cuando no hay sesión.
- Redis debe estar disponible porque el rate limiter de `/api/turnos` lo utiliza.
- `ddl-auto: update`, credenciales locales y CORS abierto son decisiones de
  desarrollo, no una configuración final de producción.
- Conviene revisar la configuración de Resilience4j antes de producción y
  verificar que las propiedades estén correctamente escritas.
