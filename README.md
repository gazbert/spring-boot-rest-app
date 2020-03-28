# Spring Boot REST App

## What is this?

A simple [Spring Boot](http://projects.spring.io/spring-boot/) sample app that exposes a REST API.

It could be used as boilerplate for developing secure REST enabled microservices.

There are 2 REST endpoints:

1. An Authentication endpoint for authenticating with credentials and obtaining a [JWT](https://jwt.io/).
1. A business service endpoint that allows you to manage fake cryptocurrency Market data. This is
secured and requires a valid JWT to be passed in the request Authorization header.

The app has role based access control 
([RBAC](https://en.wikipedia.org/wiki/Role-based_access_control)): Users can view config, 
but only administrators can update it.

You can view the [Swagger](https://swagger.io/tools/swagger-ui/) docs at: 
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) once you've configured
and started the app.

## Build Guide
You'll need JDK 11 installed on your dev box.

You can use [Gradle](https://gradle.org/) or [Maven](https://maven.apache.org) to build the app.

Both [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and 
[Maven Wrapper](https://github.com/takari/maven-wrapper) are included in the project.
   
### Gradle
1. From the project root, run `./gradlew build`
1. To generate the Javadoc, run `./gradlew javadoc` and look in the `./build/docs/javadoc` folder.

### Maven
1. From the project root, run `./mvnw clean install`
1. Take a look at the Javadoc in the `./target/apidocs` folders after the build completes.

### Checkstyle
The app uses the [Google Style Guide](https://google.github.io/styleguide/javaguide.html)
which is enforced during both the Gradle and Maven build - see the [build.gradle](./build.gradle) 
and [pom.xml](./pom.xml) files respectively. The Checkstyle report locations are:

* Gradle - `./build/reports/checkstyle/main.html`
* Maven - `./target/checkstyle-result.xml`

### Code Coverage
Code coverage is provided by [JaCoCo](https://www.eclemma.org/jacoco/) and is enforced at build time.
It's currently set to 80% line coverage. See the build files. The coverage report locations are:

* Gradle - `./build/report/jacoco/test/html/index.html`
* Maven - `./target/jacoco-report/index.html`

### Code Quality
[SpotBugs](https://spotbugs.github.io/) is run at build time. Any bugs found will fail the build. 
The bug report locations are:

* Gradle - `./build/report/jacoco/test/html/index.html`
* Maven - `./target/spotbugsXml.xml`

### Tests
Unit tests are run as part of both Gradle and Maven builds.

The endpoints are tested using 
[Spring Boot Test utils](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/html/boot-features-testing.html).

The test report locations are:
* Gradle - `build/reports/tests/test/index.html`
* Maven - `./target/surefire-reports`

## Configuration
 
### JWT configuration
You _must_ change the `restapi.jwt.secret` value in the 
[./config/application.properties](./config/application.properties) before using the REST API over a public network.
This is the key that is used to sign your web tokens - the JWTs are signed using the HS512 algorithm.
  
Other interesting configuration includes:

* `restapi.jwt.expiration` - the expires time of the JWT. Set to 10 mins. Be sure you know the
risks if you decide to extend the expiry time.
* `restapi.jwt.issuer` - the issuer of the JWT.
* `restapi.jwt.audience` - the audience the JWT is meant for - your client.
* `restapi.jwt.allowed_clock_skew` - to allow for time differences between the server and the client.
* `server.port` - the port the app will listen on for incoming requests.

### Users
You _must_ change the `PASSWORD` values in the 
[./src/main/resources/import.sql](./src/main/resources/import.sql)
before using the REST API over a public network - see instructions in the file on how to 
[bcrypt](https://en.wikipedia.org/wiki/Bcrypt) your passwords.

2 users have been set up out of the box: `user` and `admin`. These users have `user` and `admin`
roles respectively. Passwords are the same as the usernames - remember to change these :-)

When the app starts up, Spring Boot will load the `import.sql` file and store the users and their 
access rights in its [H2](https://www.h2database.com/html/main.html) in-memory database.

## User Guide

### Running the App
From the project root folder: `./app.sh [start|stop|status]`

You can also run the app using Gradle or Maven:

* Gradle - `./gradlew bootRun`
* Maven - `./mvnw spring-boot:run`

Check out the Swagger docs once the app is up and running:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Authentication
The REST API endpoints require a valid JWT to be passed in the `Authorization` header of any requests.

To obtain a JWT, your REST client needs to call the `/api/token` endpoint with a valid username/password 
contained in the `import.sql` file. See the 
[Authentication](http://localhost:8080/swagger-ui.html#/Authentication/getTokenUsingPOST) 
Swagger docs for how to do this.

The returned JWT expires after 10 mins. Your client should call the `/api/refresh` endpoint with the
JWT before it expires in order to get a new one. Alternatively, you can re-authenticate using the
`/api/token` endpoint.

## TLS
The REST API _must_ be configured to use TLS before accessing it over a public network.

The 'TLS Configuration' section in the [./config/application.properties](./config/application.properties) 
file needs the following properties set:

``` properties
# Spring Boot profile for REST API.
# Must use https profile in Production environment.
spring.profiles.active=https

# SSL (TLS) configuration to secure the REST API.
# Must be enabled in Production environment.
server.port=8443
server.ssl.key-store=classpath:keystore.jks
server.ssl.key-store-password=secret
server.ssl.key-password=another-secret
```

You will need to 
[create your own keystore](https://docs.oracle.com/cd/E19509-01/820-3503/ggfen/index.html) 
and choose your own passwords. The keystore must be on the app's classpath - you can put it in
the [./src/main/resources](./src/main/resources) and re-build the app to get up and running fast.
 
## Logging
Logging for the app is provided by [log4j](http://logging.apache.org/log4j). 
The log file is written to `logs/app.log` using a rolling policy. When a log file size reaches 
100 MB or a new day is started, it is archived and a new log file is created. 

The app will create up to 7 archives on the same day; these are stored in a directory based on the 
current year and month. Only the last 90 archives are kept. Each archive is compressed using gzip.

The logging level is set at `info`. You can change this default logging configuration in 
the [`config/log4j2.xml`](./config/log4j2.xml) file.
