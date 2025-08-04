# Java, Gradle, JDBC, JPA

This multi-part university project demonstrates the evolution of a Java-based application suite,
focusing on desktop and web development using JDBC, JPA, and various database configurations.
Each lab builds upon the previous one, adding more complexity and features.

## Technologies Used:
Java SE & EE
JDBC & JPA
Gradle
Apache Tomcat
Swing (Desktop UI)
RESTful APIs (Spring Boot)
PostgreSQL/MySQL (depending on setup)

## Lab 1: Simple Desktop Application
Interface: Desktop GUI (Swing)
Data Access: In-memory data and JDBC
Run: Main class
Database: Not persistent / memory only

## Lab 2: Desktop with JDBC Integration
Interface: Desktop GUI
Data Access: In-memory data and JDBC
Run: Main class
Database: Local JDBC setup

## Lab 3: Hybrid Application (Desktop + Web)
Endpoints exemple: GET http://localhost:8080/lkim2156-web/webshops
Interfaces: Desktop GUI
Web interface (Servlet-based)
Data Access: In-memory, JDBC
Run:
Desktop:
cd ./lkim2156-desktop  
 gradle run
Web:
gradle deploy  
 catalina run

## Lab 4: Expanded Web/Desktop Hybrid
Interface: Desktop GUI & Web interface
Data Access: In-memory, JDBC
Run:
gradle deploy  
 catalina run

Lab 5: RESTful Web Services with JPA
Endpoints exemples:
GET http://localhost:8080/webshops
GET http://localhost:8080/stores
GET http://localhost:8080/api/stores/1/webshops
Interface: Web-based frontend
Data Access: In-memory, JDBC, and JPA
Run:
gradle bootRun
Databases: iddelab4, iddelab4jpa
