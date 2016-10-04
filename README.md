# REST-API-STARTER
[![Build Status](https://travis-ci.org/haddouti-khalid/REST-API-STARTER.svg?branch=master)](https://travis-ci.org/haddouti-khalid/REST-API-STARTER)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/662db2383c764d1da9fca90b4534ce68)](https://www.codacy.com/app/haddouti-khalid/REST-API-STARTER?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=haddouti-khalid/REST-API-STARTER&amp;utm_campaign=Badge_Coverage)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/662db2383c764d1da9fca90b4534ce68)](https://www.codacy.com/app/haddouti-khalid/REST-API-STARTER?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=haddouti-khalid/REST-API-STARTER&amp;utm_campaign=Badge_Grade)

This project is a complete technical solution for achieving a REST API.
This solution includes:
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
### Unit tests
All application layers are testable (Data, Service, Web) :<br />
Data layer: all CRUD operations and specific queries are tested through a memory database (DBUnit).<br />
Service layer: to cover all requirements in tests, it is possible to use Mockito framework to adapt any input data to check the output. <br />
Web layer: even the web layer is testable with the Spring MVC-test. It allows us to check the REST service results.<br />
### Apache Tomcat and MySql integration
Apache Tomcat is used with a MySql database.<br />
Database configuration is externalized in Tomcat context file.<br />
MySql database driver is externalized in Tomcat.
### External configuration
The configuration parameters can be stored in the file system and changed with hot reloading.
### Multiple-Authentication support
In development phase, a simple value in http header is used to identify the connected user ( SPRING_USER_ID : roleName )<br />
In production platform, Tomcat users and roles ( file tomcat-users.xml ) are used.
### External logging
The application logging configuration is stored in the file system and can be changed with hot reloading. For example, the logging level can be changed without restarting the application.
### Multi profiles
The application supports two different profiles (Maven and Sprinf) : DEV and PROD
### Security layer
All users (and roles) are managed by the application easily (with a Spring annotation).
### Crossdomain queries management
In development phase, the cross domain check is disabled (a back-end can be used from a web application that is not in the same domain. In production phase, for security reasons, the cross domain check is enabled).


## Setting up your DEV environment:
### Prerequisites
Git, JDK 7 and Maven.<br />
Make sure that your JAVA_HOME environment variable points on the jdk1.7.0 folder extracted from the JDK download.
### Steps
* Clone the repo :
```
	$ git clone git@github.com:haddouti-khalid/REST-API-STARTER.git
```
* Build projects :
```
	$ cd REST-API-STARTER.git
	$ mvn clean install -f rest-api-commons-core/pom.xml
	$ mvn clean install -f rest-api-commons-test/pom.xml
	$ cd rest-api-back-end/rest-api-parent
	$ mvn clean install
```
* Generate Eclipse projects :
```
	$ mvn eclipse:clean eclipse:eclipse -f rest-api-commons-core/pom.xml
	$ mvn eclipse:clean eclipse:eclipse -f rest-api-commons-test/pom.xml
	$ cd rest-api-back-end/rest-api-parent
	$ mvn eclipse:clean eclipse:eclipse -Dwtpversion=2.0
```
* Open Eclipse and import all projects.
* Add a Tomcat server.
* Add MySQL driver in Tomcat user libraries.
* Add the jndi.name properties in Tomcat configuration.
```
	-Djndi.name=jdbc/com.sqli.jdbc.myApp
```
* Add the jndi in the context.xml file of Tomcat.
```
	<Resource auth="Container" driverClassName="com.mysql.jdbc.Driver"
		maxActive="100" maxIdle="30" maxWait="10000" name="jdbc/com.sqli.jdbc.myApp"
		username="root" password="" type="javax.sql.DataSource"
		url="jdbc:mysql://localhost:3306/myapp" />
```
* Run Tomcat server.

### Check installation
1. Check that all unit tests are OK.
2. Start the Tomcat server and send the following request using Postman extension:
```
GET http://localhost:9090/myApp/rest-api/me
```
With HTTP Header 
```
SPRING_USER_ID : admin
Content-Type : application/json
Accept : application/json
```

## Default Rest resources
The REST-API-STARTER provides the following preconfigured resources  :
```
GET     /books/?                bookController.all
GET    	/books/{id}/? 			bookController.get
POST    /books/?                bookController.create
PUT     /books/{id}/?           bookController.update
DELETE  /books/{id}/?           bookController.delete
GET     /me/?                	meController.get
POST    /errors/?               errorController.create
GET     /i18n/?              	i18nController.get
GET     /configurations/?     	configurationController.get
```
## Commands:
* To packaging the application in Dev profile :
```
mvn clean install
```
* To packaging the application in Prod profile :
```
mvn clean install -Ppackage
```

## Issues and bugs
If you find a bug in the source code or a mistake in the documentation, you can help us by submitting a ticket to our <GitHub issues>

## Contributing
[Pull requests][] are welcome.


[Pull requests]: https://help.github.com/categories/collaborating-on-projects-using-issues-and-pull-requests/

 








 
