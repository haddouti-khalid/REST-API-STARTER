# Back-end

myApp - Airbus Helicopters project.

## Environment

-   [Java](http://www.java.com)
-   [Maven](http://maven.apache.org/)

## Getting started

From the source code, execute these commands:

    cd back-end/<app>-parent
	mvn clean install -DskipTests -Psqli-eurocopter-opdm
    mvn eclipse:eclipse


And the following steps:

1. Open Eclipse with workspace directory "back-end"

2. mport all projects.

3. Add a Tomcat server.

4. Correct the deployment assembly of the webapp application (to use correctly the projects).

5. Add the JNDI Driver (SQLServer 4.0) into the classpath of the Tomcat server

6. Add the "jndi.name" properties in the Tomcat configuration (with the value in the webapp pom.xml).

7. Add the jndi in the context.xml file of Tomcat

## Check installation


- Check that all unit tests are OK.

- Start the Tomcat server and check :
	- http://localhost:8080/`<app>`/ --> ready!
	- http://localhost:8080/`<app>`/dynamic/healthcheck.jsp --> OK
	- http://localhost:8080/`<app>`/rest-api/me --> user returned with `SPRING_USER_ID` in header


## Commands

Package the application for dev

	mvn clean install

Package the application for netweaver

	mvn clean install -Ppackage

Package the delivery package

	mvn clean install -Ppackage,delivery