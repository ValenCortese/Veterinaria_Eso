# Cómo levantar el proyecto

## Requisitos

- Java JDK 21.
- Maven instalado y disponible como `mvn` (no hay wrapper Maven en la raíz).
- MySQL Server en `localhost:3306`.
- Redis en `localhost:6379` para el rate limiter del Gateway.

Comprobar Java:

```powershell
java -version
```

Debe ser Java 21. Si hace falta, configurar `JAVA_HOME`:

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

## Preparar MySQL

Crear las cinco bases usadas por los servicios:

```sql
CREATE DATABASE auth_db;
CREATE DATABASE duenio_db;
CREATE DATABASE mascota_db;
CREATE DATABASE veterinario_db;
CREATE DATABASE turno_db;
```

La configuración de desarrollo espera `root`/`root`. Si las credenciales
cambian, editar los archivos de `config-server/src/main/resources/config/`.
Hibernate crea o actualiza las tablas con `ddl-auto: update`.

## Compilar

Desde la raíz del repositorio:

```powershell
cd C:\Users\valen\Desktop\eso
mvn clean install -DskipTests
```

## Iniciar los servicios

Abrir una terminal por proceso y ejecutar los comandos en este orden.

### 1. Config Server (puerto 8888)

```powershell
mvn -pl config-server spring-boot:run
```

Sirve la configuración de `config-server/src/main/resources/config/`.

### 2. Eureka Server (puerto 8761)

```powershell
mvn -pl eureka-server spring-boot:run
```

Panel: <http://localhost:8761>

### 3. Microservicios de negocio y autenticación

```powershell
mvn -pl ms-auth spring-boot:run        # 8085
mvn -pl ms-duenio spring-boot:run      # 8081
mvn -pl ms-mascota spring-boot:run     # 8082
mvn -pl ms-veterinario spring-boot:run # 8083
mvn -pl ms-turno spring-boot:run      # 8084
```

Esperar a que cada aplicación aparezca como registrada en Eureka antes de
probar peticiones.

### 4. Gateway (puerto 8080)

Levantar docker desktop y luego en la terminal ejecutar:

```terminal
docker run -d --name redis-gateway -p 6379:6379 redis:alpine
```

```powershell
mvn -pl ms-gateway spring-boot:run
```

El Gateway concentra las rutas, valida JWT y aplica el rate limiting de turnos.

## Frontend

Abrir (la primera pantalla será el acceso si no hay sesión):

- <http://localhost:8080/auth.html>
- <http://localhost:8080/index.html>
- <http://localhost:8080/duenio.html>
- <http://localhost:8080/mascota.html>
- <http://localhost:8080/veterinario.html>
- <http://localhost:8080/turno.html>

Al registrarse o iniciar sesión, el frontend guarda el JWT en el navegador y lo
envía automáticamente en las llamadas protegidas. El botón **Cerrar sesión**
elimina la sesión.

Las llamadas usan rutas relativas del Gateway:

```text
/api/duenios
/api/mascotas
/api/veterinarios
/api/turnos
```

## Crear el primer usuario

`/auth/register` y `/auth/login` son públicos; el resto de rutas exige un token
JWT. Por ejemplo:

```powershell
curl.exe -X POST http://localhost:8080/auth/register `
  -H "Content-Type: application/json" `
  -d '{"username":"admin","password":"admin123","rol":"ADMIN"}'
```

Después, iniciar sesión en `auth.html` con esas credenciales. Para probar la
API manualmente, usar el token devuelto:

```powershell
curl.exe http://localhost:8080/api/duenios `
  -H "Authorization: Bearer <token>"
```

## Notas del frontend

- `auth.html` es la pantalla inicial para login y registro.
- Las páginas de gestión requieren una sesión válida.
- El token se guarda en `localStorage` y se envía como
  `Authorization: Bearer <token>`.
- **Cerrar sesión** elimina el token y vuelve a `auth.html`.
- El formulario deshabilita **Guardar** durante la petición para evitar
  duplicados.
- Redis debe estar activo para consultar o crear turnos mediante el Gateway.

## Orden resumido

```text
MySQL + Redis → Config Server → Eureka → ms-auth y microservicios → Gateway → frontend
```
