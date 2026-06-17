# Alquiler de Viviendas

Proyecto desarrollado para la asignatura **Ingeniería del Software II**.
La aplicación implementa un sistema web básico de alquiler de viviendas, inspirado en plataformas como Airbnb, utilizando Java, Spring Boot, Maven, JPA, H2, Thymeleaf, JUnit, JaCoCo y SonarCloud.

## 1. Descripción del proyecto

El objetivo del proyecto es desarrollar una aplicación web que permita gestionar propiedades de alquiler y reservas. El sistema permite que los propietarios registren viviendas o habitaciones, y que los usuarios puedan consultar alojamientos disponibles, buscar por filtros, añadir propiedades a una lista de deseos y realizar reservas.

La aplicación contempla dos tipos de reserva:

* **Reserva inmediata**: la reserva queda confirmada automáticamente.
* **Reserva bajo solicitud**: la reserva queda pendiente hasta que sea confirmada o rechazada.

Además, se controla que una propiedad no pueda tener dos reservas confirmadas solapadas para las mismas fechas.

## 2. Tecnologías utilizadas

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
* **Git y GitHub**
* **SonarCloud**

## 3. Funcionalidades implementadas

### Gestión de propiedades

La aplicación permite:

* Listar propiedades disponibles.
* Registrar nuevas propiedades.
* Consultar información básica de cada propiedad.
* Filtrar propiedades por ciudad, tipo y reserva inmediata.
* Validar que el precio por noche sea mayor que cero.

Cada propiedad almacena información como título, ciudad, dirección, tipo de alojamiento, precio por noche, descripción y si permite reserva inmediata.

### Gestión de reservas

La aplicación permite:

* Crear reservas para una propiedad.
* Introducir datos del inquilino.
* Seleccionar fechas de entrada y salida.
* Calcular el importe total de la reserva según el número de noches.
* Diferenciar entre reservas confirmadas y pendientes.
* Confirmar o rechazar reservas pendientes.
* Rechazar automáticamente solicitudes pendientes solapadas cuando se confirma una reserva.
* Marcar una reserva rechazada como devolución realizada.

También se valida que la fecha de salida sea posterior a la fecha de entrada y que no exista una reserva confirmada solapada para la misma propiedad.

### Lista de deseos

La aplicación incluye una lista de deseos con un usuario simulado. Permite:

* Añadir propiedades a la lista de deseos.
* Evitar propiedades duplicadas en la lista.
* Consultar la lista de deseos.
* Eliminar propiedades de la lista.
* Reservar una propiedad desde la lista de deseos.

## 4. Persistencia con JPA y H2

La persistencia se ha implementado con **Spring Data JPA** y la base de datos **H2**.

Durante la ejecución normal, la aplicación utiliza una base de datos persistente en fichero:

```properties
spring.datasource.url=jdbc:h2:file:C:/ISO2/alquiler-viviendas/alquiler-viviendas/data/alquilerviviendas
```

Esto permite que los datos no se pierdan al reiniciar la aplicación.

Para las pruebas automáticas se utiliza una base de datos H2 en memoria, definida en:

```text
src/test/resources/application.properties
```

De esta forma, los tests no modifican la base de datos real de la aplicación.

## 5. Pruebas automáticas con JUnit y Surefire

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

## 6. Cobertura con JaCoCo

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

## 7. Calidad del código con SonarCloud

El proyecto se ha integrado con **SonarCloud** para analizar la calidad del código.

Repositorio analizado:

```text
gumapee_alquiler-viviendas
```

Durante el desarrollo, SonarCloud detectó inicialmente varios problemas de mantenibilidad. Se aplicaron refactorizaciones para corregirlos, entre ellas:

* Extracción de constantes para evitar literales duplicados.
* Reducción de code smells en controladores.
* Eliminación de un constructor con demasiados parámetros.
* Mejora de pruebas para evitar advertencias de mantenibilidad.
* Corrección de issues detectados en código nuevo.

Resultado final del análisis:

* **Quality Gate: Passed**
* **New Issues: 0**
* **Accepted Issues: 0**
* **Duplications: 0.0%**
* **Security Hotspots: 0**

Esto demuestra que el proyecto cumple las condiciones de calidad configuradas en SonarCloud.

## 8. Mantenimiento y evolución

Se ha realizado una actividad de mantenimiento sobre el proyecto usando una rama específica de Git.

Rama utilizada:

```text
mantenimiento-mejora-validaciones
```

La mejora implementada consistió en añadir una validación al formulario de creación de propiedades para evitar que se registren alojamientos con precio por noche menor o igual que cero.

Proceso seguido:

1. Creación de una rama de mantenimiento.
2. Implementación de la validación.
3. Ejecución de pruebas automáticas con Maven.
4. Confirmación del cambio mediante commit.
5. Fusión de la rama con `main`.
6. Subida de los cambios a GitHub.
7. Nuevo análisis de calidad con SonarCloud.

Esta mejora puede considerarse mantenimiento preventivo y correctivo, ya que evita datos incorrectos en el sistema y mejora la robustez de la aplicación.

## 9. Instrucciones de ejecución

Para ejecutar el proyecto en local:

1. Clonar el repositorio:

```bash
git clone https://github.com/gumapee/alquiler-viviendas.git
```

2. Entrar en la carpeta del proyecto:

```bash
cd alquiler-viviendas
```

3. Ejecutar la aplicación con Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

4. Abrir la aplicación en el navegador:

```text
http://localhost:8080
```

## 10. Consola H2

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

## 11. Control de versiones

El proyecto se ha gestionado con Git y GitHub.

Repositorio:

```text
https://github.com/gumapee/alquiler-viviendas
```

Se han realizado commits incrementales durante el desarrollo para reflejar la evolución del proyecto, incluyendo:

* Inicialización del proyecto Spring Boot.
* Implementación de propiedades.
* Persistencia con JPA y H2.
* Implementación de reservas.
* Implementación de lista de deseos.
* Configuración de pruebas.
* Configuración de JaCoCo.
* Corrección de code smells detectados por SonarCloud.
* Mejora de mantenimiento mediante rama específica.

## 12. Estado final del proyecto

El proyecto incluye una aplicación funcional con persistencia, gestión de propiedades, reservas, lista de deseos, pruebas automáticas, cobertura local y análisis de calidad con SonarCloud.

El estado final del proyecto es adecuado para la entrega, ya que integra los elementos principales solicitados en la práctica:

* Proyecto Maven con Spring Boot.
* Persistencia con JPA.
* Base de datos H2.
* Control de versiones con GitHub.
* Pruebas con JUnit y Surefire.
* Cobertura con JaCoCo.
* Análisis de calidad con SonarCloud.
* Actividad de mantenimiento documentada.
