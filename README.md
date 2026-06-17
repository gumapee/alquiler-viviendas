# Alquiler de Viviendas

Proyecto desarrollado para la asignatura **Ingeniería del Software II**.

La aplicación implementa un sistema web básico de alquiler de viviendas, inspirado en plataformas como Airbnb. Permite gestionar propiedades, realizar reservas, utilizar una lista de deseos, simular pagos, ejecutar pruebas automáticas, generar cobertura y analizar la calidad del código mediante SonarCloud.

## 1. Empresa de desarrollo

Para la realización del proyecto se ha definido una empresa de desarrollo software simulada.

**Nombre de la empresa:** CasaSoft Solutions
**Proyecto:** Alquiler de Viviendas
**Repositorio:** https://github.com/gumapee/alquiler-viviendas
**SonarCloud:** https://sonarcloud.io/project/overview?id=gumapee_alquiler-viviendas

El proyecto ha sido desarrollado de forma individual, por lo que los distintos roles de la empresa han sido asumidos por el mismo estudiante dentro de la simulación del equipo de trabajo.

### Miembro

* Guillermo Marcos

### Roles asumidos

* CEO / Responsable de comunicación.
* Analista funcional.
* Desarrollador backend/frontend.
* Responsable de calidad, pruebas y mantenimiento.

### Remuneración simulada

La práctica plantea una empresa de desarrollo y una remuneración ficticia como parte de la simulación académica del proyecto. Al tratarse de una entrega individual, no se realiza reparto entre varios miembros, sino que todas las responsabilidades y roles han sido asumidos por el mismo estudiante.

## 2. Descripción del proyecto

El objetivo del proyecto es desarrollar una aplicación web para gestionar alquileres de viviendas. El sistema permite que los propietarios registren viviendas o habitaciones, y que los usuarios consulten alojamientos disponibles, filtren propiedades, añadan viviendas a una lista de deseos y realicen reservas.

La aplicación contempla dos tipos principales de reserva:

* **Reserva inmediata**: la reserva queda confirmada automáticamente.
* **Reserva bajo solicitud**: la reserva queda pendiente hasta que sea confirmada o rechazada.

También se controla que una propiedad no pueda tener dos reservas confirmadas solapadas para las mismas fechas.

## 3. Tecnologías utilizadas

El proyecto se ha desarrollado con las siguientes tecnologías:

* **Java 17**
* **Spring Boot**
* **Spring Web**
* **Thymeleaf**
* **Spring Data JPA**
* **H2 Database**
* **Maven**
* **JUnit 5**
* **Maven Surefire**
* **JaCoCo**
* **Git**
* **GitHub**
* **SonarCloud**

## 4. Funcionalidades implementadas

### 4.1. Gestión de propiedades

La aplicación permite:

* Listar propiedades disponibles.
* Registrar nuevas propiedades.
* Consultar información básica de cada propiedad.
* Filtrar propiedades por ciudad, tipo y reserva inmediata.
* Validar que el precio por noche sea mayor que cero.

Cada propiedad almacena información como:

* Título.
* Ciudad.
* Dirección.
* Tipo de alojamiento.
* Precio por noche.
* Descripción.
* Indicación de si permite reserva inmediata.

### 4.2. Gestión de reservas

La aplicación permite:

* Crear reservas asociadas a una propiedad.
* Introducir datos del inquilino.
* Seleccionar fecha de entrada y fecha de salida.
* Seleccionar un método de pago.
* Calcular el importe total de la reserva.
* Diferenciar entre reservas confirmadas y pendientes.
* Confirmar reservas pendientes.
* Rechazar reservas pendientes.
* Simular la devolución del dinero si una reserva pendiente es rechazada.
* Evitar reservas confirmadas solapadas sobre la misma propiedad.

El importe total se calcula multiplicando el número de noches por el precio por noche de la propiedad.

### 4.3. Lista de deseos

La aplicación incluye una lista de deseos con usuario simulado.

Permite:

* Añadir propiedades a la lista de deseos.
* Evitar duplicados en la lista.
* Consultar las propiedades guardadas.
* Eliminar propiedades de la lista.
* Reservar una propiedad directamente desde la lista de deseos.

## 5. Modelo de pagos

El sistema implementa una **simulación del proceso de pago**.

Al crear una reserva, el usuario indica un método de pago y la aplicación calcula el importe total de la reserva en función del número de noches y el precio de la propiedad.

No se ha integrado una pasarela de pago real, como Stripe, Redsys o PayPal, ya que el objetivo de la práctica es modelar el flujo funcional del sistema. El pago queda representado mediante:

* El método de pago seleccionado.
* El importe total calculado.
* El estado de la reserva.

### Flujo de pago simulado

Si la propiedad permite **reserva inmediata**, la reserva queda directamente en estado:

```text
CONFIRMADA
```

Si la propiedad requiere aprobación del propietario, la reserva queda en estado:

```text
PENDIENTE
```

Posteriormente, el propietario puede confirmar o rechazar la reserva.

Si la reserva se rechaza, el sistema la marca como:

```text
RECHAZADA - DEVOLUCIÓN REALIZADA
```

De esta forma, la aplicación simula la devolución del dinero al inquilino.

## 6. Persistencia con JPA y H2

La persistencia se ha implementado con **Spring Data JPA** y la base de datos **H2**.

Durante la ejecución normal, la aplicación utiliza una base de datos persistente en fichero:

```properties
spring.datasource.url=jdbc:h2:file:C:/ISO2/alquiler-viviendas/alquiler-viviendas/data/alquilerviviendas
```

Esto permite que los datos no se pierdan al reiniciar la aplicación.

La configuración principal se encuentra en:

```text
src/main/resources/application.properties
```

Para las pruebas automáticas se utiliza una base de datos H2 en memoria, configurada en:

```text
src/test/resources/application.properties
```

De esta forma, los tests no modifican la base de datos real de la aplicación.

## 7. Entidades principales

El proyecto trabaja principalmente con las siguientes entidades:

### Propiedad

Representa una vivienda o habitación disponible para alquilar.

Incluye datos como:

* Título.
* Ciudad.
* Dirección.
* Tipo.
* Precio por noche.
* Descripción.
* Reserva inmediata.

### Reserva

Representa una solicitud o confirmación de reserva realizada por un usuario.

Incluye datos como:

* Nombre del inquilino.
* Email del inquilino.
* Fecha de entrada.
* Fecha de salida.
* Método de pago.
* Estado.
* Importe total.
* Propiedad asociada.

### ListaDeseos

Representa una propiedad guardada por un usuario en su lista de deseos.

Incluye:

* Email del usuario.
* Propiedad guardada.

## 8. Arquitectura del proyecto

El proyecto sigue una estructura típica de Spring Boot:

```text
src/main/java/es/uclm/alquilerviviendas
├── config
├── controller
├── entity
├── repository
└── AlquilerViviendasApplication.java
```

### Paquete `config`

Contiene la clase de inicialización de datos de ejemplo.

### Paquete `controller`

Contiene los controladores web encargados de recibir peticiones HTTP y devolver vistas Thymeleaf.

### Paquete `entity`

Contiene las entidades JPA del dominio.

### Paquete `repository`

Contiene las interfaces de acceso a datos mediante Spring Data JPA.

### Carpeta `templates`

Contiene las vistas HTML desarrolladas con Thymeleaf.

```text
src/main/resources/templates
```

## 9. Pruebas automáticas con JUnit y Surefire

Se han implementado pruebas automáticas con **JUnit 5**.

Las pruebas se ejecutan mediante Maven y el plugin **Surefire**.

Se han creado pruebas para:

* Comprobar que el contexto de Spring Boot carga correctamente.
* Probar el controlador principal.
* Probar el controlador de propiedades.
* Probar el repositorio de propiedades.
* Probar el repositorio de reservas.
* Probar el repositorio de lista de deseos.

El comando utilizado para ejecutar las pruebas es:

```bash
./mvnw clean verify
```

En Windows:

```powershell
.\mvnw.cmd clean verify
```

El resultado de la ejecución de pruebas fue correcto, obteniendo:

```text
BUILD SUCCESS
```

## 10. Cobertura con JaCoCo

Se ha configurado **JaCoCo** para generar informes de cobertura de pruebas.

Tras ejecutar:

```powershell
.\mvnw.cmd clean verify
```

se genera el informe de cobertura en:

```text
target/site/jacoco/index.html
```

La cobertura local obtenida con JaCoCo es aproximadamente del **41%**.

Este resultado demuestra que se han configurado y ejecutado pruebas automáticas sobre distintas partes del proyecto.

La cobertura se ha consultado localmente mediante el informe HTML generado por JaCoCo.

## 11. Calidad del código con SonarCloud

El proyecto se ha integrado con **SonarCloud** para analizar la calidad del código.

Proyecto analizado:

```text
gumapee_alquiler-viviendas
```

URL del análisis:

```text
https://sonarcloud.io/project/overview?id=gumapee_alquiler-viviendas
```

Durante el desarrollo, SonarCloud detectó inicialmente varios problemas de mantenibilidad. Se aplicaron refactorizaciones para corregirlos, entre ellas:

* Extracción de constantes para evitar literales duplicados.
* Reducción de code smells en controladores.
* Eliminación de un constructor con demasiados parámetros.
* Mejora de pruebas para evitar advertencias de mantenibilidad.
* Corrección de issues detectados en código nuevo.

Resultado final del análisis:

```text
Quality Gate: Passed
New Issues: 0
Accepted Issues: 0
Duplications: 0.0%
Security Hotspots: 0
```

Esto demuestra que el proyecto cumple las condiciones de calidad configuradas en SonarCloud.

## 12. Mantenimiento y evolución

Se ha realizado una actividad de mantenimiento sobre el proyecto usando una rama específica de Git.

Rama utilizada:

```text
mantenimiento-mejora-validaciones
```

La mejora implementada consistió en añadir una validación al formulario de creación de propiedades para evitar que se registren alojamientos con precio por noche menor o igual que cero.

### Proceso seguido

1. Creación de una rama de mantenimiento.
2. Implementación de la validación.
3. Ejecución de pruebas automáticas con Maven.
4. Confirmación del cambio mediante commit.
5. Fusión de la rama con `main`.
6. Subida de los cambios a GitHub.
7. Nuevo análisis de calidad con SonarCloud.

Esta mejora puede considerarse mantenimiento preventivo y correctivo, ya que evita datos incorrectos en el sistema y mejora la robustez de la aplicación.

## 13. Control de versiones

El proyecto se ha gestionado con **Git** y **GitHub**.

Repositorio:

```text
https://github.com/gumapee/alquiler-viviendas
```

Se han realizado commits incrementales durante el desarrollo para reflejar la evolución del proyecto.

Entre los cambios principales se incluyen:

* Inicialización del proyecto Spring Boot.
* Implementación del listado de propiedades.
* Implementación del formulario de nuevas propiedades.
* Implementación de búsqueda por filtros.
* Configuración de persistencia con JPA y H2.
* Implementación de reservas.
* Gestión de reservas pendientes, confirmadas y rechazadas.
* Implementación de lista de deseos.
* Configuración de pruebas automáticas.
* Configuración de JaCoCo.
* Corrección de code smells detectados por SonarCloud.
* Actividad de mantenimiento mediante rama específica.
* Actualización de la memoria del proyecto.

## 14. Instrucciones de ejecución

Para ejecutar el proyecto en local:

### 1. Clonar el repositorio

```bash
git clone https://github.com/gumapee/alquiler-viviendas.git
```

### 2. Entrar en la carpeta del proyecto

```bash
cd alquiler-viviendas
```

### 3. Ejecutar la aplicación con Maven Wrapper

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

### 4. Abrir la aplicación en el navegador

```text
http://localhost:8080
```

## 15. Consola H2

La consola de H2 está habilitada en:

```text
http://localhost:8080/h2-console
```

Datos de conexión:

```text
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:file:C:/ISO2/alquiler-viviendas/alquiler-viviendas/data/alquilerviviendas
User Name: sa
Password:
```

La contraseña se deja vacía.

## 16. Comandos útiles

### Ejecutar la aplicación

```powershell
.\mvnw.cmd spring-boot:run
```

### Ejecutar pruebas

```powershell
.\mvnw.cmd test
```

### Ejecutar pruebas y generar cobertura

```powershell
.\mvnw.cmd clean verify
```

### Consultar el informe de cobertura

```text
target/site/jacoco/index.html
```

### Consultar el estado de Git

```powershell
git status
```

### Subir cambios a GitHub

```powershell
git add .
git commit -m "Mensaje del commit"
git push
```

## 17. Estado final del proyecto

El proyecto incluye una aplicación funcional con:

* Gestión de propiedades.
* Registro de nuevas propiedades.
* Validación del precio por noche.
* Búsqueda por filtros.
* Gestión de reservas.
* Control de reservas inmediatas y pendientes.
* Confirmación y rechazo de reservas.
* Simulación de devolución del dinero.
* Lista de deseos.
* Persistencia con JPA y H2.
* Pruebas automáticas con JUnit.
* Ejecución de pruebas con Maven Surefire.
* Cobertura local con JaCoCo.
* Análisis de calidad con SonarCloud.
* Mantenimiento mediante rama específica.
* Control de versiones con GitHub.

El estado final del proyecto es adecuado para la entrega, ya que integra los elementos principales solicitados en la práctica:

* Proyecto Maven con Spring Boot.
* Persistencia con JPA.
* Base de datos H2.
* Control de versiones con GitHub.
* Pruebas con JUnit y Surefire.
* Cobertura con JaCoCo.
* Análisis de calidad con SonarCloud.
* Actividad de mantenimiento documentada.
* Empresa de desarrollo simulada.
