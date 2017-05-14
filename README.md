## Proyecto Final: Diseño e implementación de una capa de persistencia para una aplicación web de reservas hoteleras

Universidad de Valencia, Ingeniería de Servicios y Aplicaciones Web, Bases de Datos en Sistemas Web 2016-17

-----
**Is under development...**

Java EE application server: **WildFly 10.1**

It is necessary to create a connection pool "datasource" on the server to connect to the database and import the base to your MySQL server:

* datasource JNDI name: ***java:jboss/datasources/BdwebDS***
* MySQL database nombre: ***bdweb_hotel***

<center>Logical model of data base:</center>
![modelo lógico de base da datos](https://raw.githubusercontent.com/lytves/hotel/master/datebase/modelo_logico_hotel.png)