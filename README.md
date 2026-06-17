# Alquiler de Viviendas

Proyecto desarrollado para la asignatura **Ingeniería del Software II**.

La aplicación simula un sistema de alquiler de viviendas similar a Airbnb, donde los propietarios pueden registrar alojamientos y los inquilinos pueden buscar propiedades, añadirlas a una lista de deseos y realizar reservas.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Maven
- Spring Web
- Thymeleaf
- Spring Data JPA
- H2 Database
- JUnit
- Git y GitHub

## Funcionalidades implementadas

### Gestión de propiedades

- Alta de propiedades.
- Listado de propiedades disponibles.
- Búsqueda de propiedades por ciudad, tipo y reserva inmediata.
- Persistencia de propiedades mediante Spring Data JPA.

### Gestión de reservas

- Creación de reservas sobre propiedades.
- Cálculo automático del importe total según el número de noches.
- Reserva inmediata para propiedades que lo permitan.
- Solicitudes pendientes para propiedades que requieren confirmación.
- Confirmación de reservas pendientes.
- Rechazo de reservas pendientes con devolución simulada.
- Control de disponibilidad para evitar reservas confirmadas solapadas.

### Lista de deseos

- Añadir propiedades a una lista de deseos.
- Evitar duplicados en la lista.
- Eliminar propiedades de la lista de deseos.
- Reservar propiedades desde la lista de deseos.

## Persistencia

La aplicación utiliza una base de datos H2 en modo fichero para mantener los datos durante la ejecución local.

La configuración principal se encuentra en:

```text
src/main/resources/application.properties
