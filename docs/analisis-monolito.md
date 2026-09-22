# Análisis del monolito de Veterinaria Eso

El proyecto está organizado como un monolito Spring Boot: los módulos de
Dueños, Mascotas, Veterinarios y Turnos se ejecutan dentro de la misma
aplicación, comparten el proceso, la configuración y la base de datos MySQL.
Aunque el código está separado por controladores, servicios, repositorios y
modelos, esa separación es interna al mismo despliegue y no constituye una
separación de servicios independientes.

## ¿Qué pasa si solo queremos escalar el módulo de Turnos porque hay un pico de demanda?

No se puede escalar únicamente el módulo de Turnos de forma directa. Para
atender más solicitudes habría que levantar más instancias de toda la
aplicación monolítica. Eso también replica los módulos de Dueños, Mascotas y
Veterinarios, aunque no tengan un aumento de tráfico.

Este enfoque puede funcionar mientras la carga sea moderada y simplifica la
operación, pero consume más recursos y limita el escalado independiente. Todas
las instancias compartirían MySQL, por lo que la base de datos y las
transacciones de Turnos podrían convertirse en el cuello de botella. Para
escalar Turnos de manera independiente habría que extraerlo a un servicio
separado, definir una API para consultar Mascotas y Veterinarios y resolver
también la coordinación de datos y la consistencia.

## ¿Qué pasa si el módulo de Veterinarios falla por un bug — afecta a los demás?

Al estar dentro del mismo proceso, un bug puede afectar a toda la aplicación.
Por ejemplo, una excepción no controlada, un consumo excesivo de memoria o un
bloqueo de hilos en Veterinarios puede degradar o detener también los
endpoints de Dueños, Mascotas y Turnos.

Si el bug está aislado en una operación y el manejador global de excepciones
lo controla, el impacto puede limitarse a esa solicitud. Sin embargo, no hay
un aislamiento fuerte: el mismo artefacto se despliega completo y comparte
recursos, configuración y base de datos. Un despliegue independiente de
Veterinarios, con límites de recursos y monitoreo propios, reduciría el
alcance de una falla.

## ¿Qué pasa si queremos usar MongoDB para el historial de turnos y MySQL para los dueños?

Es posible hacerlo en un monolito, pero aumenta la complejidad. La aplicación
debería configurar dos fuentes de datos y dos tecnologías de persistencia:
JPA para MySQL y un cliente o repositorio de MongoDB para el historial. También
habría que decidir qué componente es dueño de cada dato, cómo se identifican
los turnos en ambas bases y cómo se sincroniza el historial.

No existiría una transacción ACID única entre MySQL y MongoDB. Si una operación
actualiza un dueño o un turno y además escribe un historial, una falla entre
ambas escrituras podría dejar estados inconsistentes. Sería necesario usar
transacciones locales cuando correspondan, eventos/outbox, reintentos e
idempotencia. En este proyecto, donde las entidades están relacionadas por
JPA y las relaciones `Turno`-`Mascota`-`Veterinario` viven en MySQL, introducir
MongoDB solo para el historial no es transparente: requiere adaptar servicios,
configuración, pruebas y operación.

## ¿Qué pasa si dos equipos trabajan en paralelo sobre el mismo repositorio?

Los equipos pueden trabajar en paralelo usando ramas, pero en un monolito es
frecuente que terminen modificando archivos y capas compartidas, como
controladores, servicios, `pom.xml`, configuración o migraciones de base de
datos. Eso aumenta los conflictos de merge y el riesgo de que un cambio de un
módulo rompa otro.

Para reducir el problema conviene acordar contratos de API, dividir el trabajo
por módulos y archivos, usar ramas y pull requests pequeños, revisión de
código y una compilación/prueba automática en cada pull request. También hay
que coordinar cambios en el esquema de MySQL: una modificación de una entidad
puede afectar a varios módulos. La organización por paquetes ayuda a ordenar
el código, pero no elimina la coordinación porque todos los equipos entregan
un único artefacto.

## ¿Qué pasa si queremos deployar solo una actualización del módulo de Mascotas?

No se puede desplegar únicamente Mascotas con el esquema actual. El artefacto
generado contiene todos los módulos, por lo que una actualización de
Mascotas implica construir, probar y desplegar la aplicación completa. Esto
puede aumentar el tiempo de entrega y el riesgo operativo: un cambio pequeño
queda acoplado al estado de Turnos, Dueños y Veterinarios.

Se puede reducir el riesgo con pruebas automatizadas, despliegues
versionados, rollback y estrategias como blue-green o canary, pero la unidad
de despliegue sigue siendo el monolito. Para publicar Mascotas de manera
independiente habría que separar el módulo en otro servicio o aplicación,
establecer contratos de comunicación y definir cómo se mantiene la relación
con Dueños y los datos existentes.

## Conclusión

El monolito ofrece una implementación inicial simple: un solo repositorio, un
despliegue, llamadas internas directas y una base de datos compartida. Ese
modelo es adecuado para comenzar, pero sus límites aparecen cuando se
necesita escalar, fallar o desplegar módulos de forma independiente, trabajar
con distintas bases de datos o permitir que varios equipos evolucionen el
sistema con bajo acoplamiento. La alternativa sería una migración gradual,
extrayendo primero los módulos que tengan una necesidad real de escalado,
aislamiento o despliegue independiente, en lugar de distribuir todo el
sistema desde el inicio.
