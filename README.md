## REST-API-STARTER
This project is a complet technical solution for achieving a REST API plateform.
This solution includes :
```
* Spring 4.1.7.RELEASE
* Hibernate 4.3.8.Final
* C3P0 0.9.1.2
* XDocReport 1.0.5
* JUnit 4.11
* DBUnit 2.4.9
* Mockito 1.9.5
* Hsqldb 2.2.4
```
## Features :
* Unit tests
All application layers are testable (Data, Service, Web) :
Data layer: all CRUD operations and specific queries are tested through a memory database (DBUnit). 
Service layer: to cover all requirements in tests, it is possible to use Mockito framework to adapt any input data to check the output. 
Web layer: even the web layer is testable with the Spring MVC-test. It allows us to check the REST service results.
* Apache Tomcat and MySql integration
Apache Tomcat is used with a MySql database.
Database configuration is externalized in Tomcat context file
MySql database driver is externalized in Tomcat.
* External configuration
The  parameters of configuration can be stored in the file system and can be changed with hot reloading.
* Multiple-Authentication support
In development phase, a simple value in http header is used to identify the connected user ( SPRING_USER_ID : roleName )
In production platform, Tomcat users and roles ( file tomcat-users.xml ) are used.
* External logging
The logging configuration of the application is stored in the file system and can be changed with hot reloading. For example, the logging level can be changed without restart the application.
* Multi profiles :
Application supports two different profiles : DEV and PROD
* Security layer
All user with his roles are managed by the application easily (with a Spring annotation).
* Crossdomain queries management
In development phase, the cross domain check is disabled (a back-end can be used from a web application that is not in the same domain. In production phase, for security reasons, the cross domain check is enabled.


## Setting up your DEV environment:
### Prerequisites
Git, JDK 7 and Maven.
Be sure that your JAVA_HOME environment variable points to the jdk1.7.0 folder extracted from the JDK download.
### Steps
1. Clone the repo :
```
	$ git clone git@github.com:haddouti-khalid/REST-API-STARTER.git
```
2. Build projects :
```
	$ mvn clean install -f rest-api-commons-core/pom.xml
	$ mvn clean install -f rest-api-commons-test/pom.xml
	$ cd rest-api-back-end/rest-api-parent
	$ mvn clean install
```
3. Generate Eclipse projects :
```
	$ mvn eclipse:clean eclipse:eclipse -f rest-api-commons-core/pom.xml
	$ mvn eclipse:clean eclipse:eclipse -f rest-api-commons-test/pom.xml
	$ cd rest-api-back-end/rest-api-parent
	$ mvn eclipse:clean eclipse:eclipse -Dwtpversion=2.0
```
4. Open Eclipse and import all projects.
5. Add a Tomcat server.
6. Add MySQL driver in Tomcat user libraries.
7. Add the jndi.name properties in Tomcat configuration.
```
	-Djndi.name=jdbc/com.sqli.jdbc.myApp
```
8. Add the jndi in the context.xml file of Tomcat.
```
	<Resource auth="Container" driverClassName="com.mysql.jdbc.Driver"
		maxActive="100" maxIdle="30" maxWait="10000" name="jdbc/com.sqli.jdbc.myApp"
		username="root" password="" type="javax.sql.DataSource"
		url="jdbc:mysql://localhost:3306/myapp" />
```
9. Run Tomcat server.

### Check installation
1. Check that all unit tests are OK.
2. Start the Tomcat server and send the following request using Postman extension:
```
GET http://localhost:8080/XXXXx/me With HTTP Header ( XXX : xXXx )
```

## Default Rest resources
The REST-API-STARTER provides the following preconfigured resources  :

## Commands:
* Package the application for Dev
```
mvn clean install
```
* Package the application for Prod
```
mvn clean install -Ppackage
```

## Issues and bugs
If you find a bug in the source code or a mistake in the documentation, you can help us by submitting a ticket to our <GitHub issues>

## Contributing
[Pull requests][] are welcome; see the [contributor guidelines][] for details.


https://github.com/spring-projects/spring-framework#prerequisites
 








 
