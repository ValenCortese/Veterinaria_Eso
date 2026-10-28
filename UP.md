## 1. Requisitos

Instalá y verificá:

- Java JDK 21
- Maven, o usá los Maven Wrapper incluidos
- MySQL Server
- MySQL Workbench, opcional

Verificá Java:

```powershell
java -version
```

Debe mostrar Java 21.

Si aparece el error de `JAVA_HOME`, configurá la variable:

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
```

La ruta puede cambiar según dónde instalaste Java.

## 2. Preparar MySQL

El proyecto utiliza estas bases de datos:

```sql
CREATE DATABASE duenio_db;
CREATE DATABASE mascota_db;
CREATE DATABASE veterinario_db;
CREATE DATABASE turno_db;
```

La configuración actual espera:

```text
Usuario: root
Contraseña: root
Host: localhost
Puerto: 3306
```

Si tu usuario o contraseña son diferentes, modificá los archivos dentro de:

```text
config-server/src/main/resources/config/
```

## 3. Compilar el proyecto

Desde la raíz:

```powershell
cd C:\Users\valen\Desktop\eso
.\mvnw.cmd clean install -DskipTests
```

Si Maven está instalado globalmente también podés usar:

```powershell
mvn clean install -DskipTests
```

## 4. Iniciar los servicios

Abrí una terminal por servicio y ejecutalos en este orden.

### Terminal 1: Config Server

```powershell
cd C:\Users\valen\Desktop\eso
.\mvnw.cmd -pl config-server spring-boot:run
```

Puerto:

```text
http://localhost:8888
```

### Terminal 2: Eureka Server

```powershell
cd C:\Users\valen\Desktop\eso
.\mvnw.cmd -pl eureka-server spring-boot:run
```

Puerto:

```text
http://localhost:8761
```

Podés consultar el panel en:

```text
http://localhost:8761
```

### Terminales 3 a 6: Microservicios

Dueños:

```powershell
.\mvnw.cmd -pl ms-duenio spring-boot:run
```

Puerto `8081`.

Mascotas:

```powershell
.\mvnw.cmd -pl ms-mascota spring-boot:run
```

Puerto `8082`.

Veterinarios:

```powershell
.\mvnw.cmd -pl ms-veterinario spring-boot:run
```

Puerto `8083`.

Turnos:

```powershell
.\mvnw.cmd -pl ms-turno spring-boot:run
```

Puerto `8084`.

### Terminal 7: Gateway

```powershell
.\mvnw.cmd -pl ms-gateway spring-boot:run
```

Puerto:

```text
http://localhost:8080
```

## 5. Abrir el frontend

Con todos los servicios funcionando, ingresá en:

```text
http://localhost:8080/index.html
```

También podés acceder directamente a:

```text
http://localhost:8080/duenio.html
http://localhost:8080/mascota.html
http://localhost:8080/veterinario.html
http://localhost:8080/turno.html
```

El frontend realiza las llamadas a través del gateway:

```text
http://localhost:8080/api/duenios
http://localhost:8080/api/mascotas
http://localhost:8080/api/veterinarios
http://localhost:8080/api/turnos
```

El orden importante es:

```text
MySQL → Config Server → Eureka → Microservicios → Gateway → Frontend
```