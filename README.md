# spring-boot-07
## 1. Initialize with spring-boot cli
```bash
  $ spring init -d=web spring-boot-07
  $ cd spring-boot-07
```

## 2. Add _Greeting.java_
```java
package com.example.springboot07;

public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
```

## 3. Add _GreetingController.java_
```java
package com.example.springboot07;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
            String.format(template, name));
    }
}
```

## 4. Run it
```bash
  $ cd src/main/resources/static/
  $ npm install
  $ bower install

  $ mvn spring-boot:run -Dserver.port=8094
  
  # use property file application-local-pg.yml
  $ mvn spring-boot:run -Dspring.profiles.active=local-pg
```

Open it on the browser [http://localhost:8094](http://localhost:8094)

## 5. Setup Angular UI
```bash
$ cd src/main/resources/static/
$ npm init

$ mkdir assets
$ mkdir assets/vendor
$ mkdir assets/js
$ mkdir assets/images
$ mkdir assets/styles
$ mkdir assets/includes

$ echo '{"directory" : "assets/vendor"}' > .bowerrc
$ npm install bower --save

$ bower init
$ bower install bootstrap --save
$ bower install angular --save
$ bower install angular-ui --save
$ bower install font-awesome --save
```

### 6. Debugging on javascript console
```javascript
  angular.element($0).controller();
```

### 7. Add BASIC Authentication
#### Add spring-boot security dependency in the `pom.xml`.
```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
</dependencies>
```

### Start the spring-boot
The username is __user__, and find the _password_ in the logs:
```
2017-08-16 11:29:56.815  INFO 2436 --- [           main] b.a.s.AuthenticationManagerConfiguration :

Using default security password: 5c8ff903-773e-49b7-b225-80ef88664386
```

### Call any service with Basic Auth.
```bash
$ curl -u user:5c8ff903-773e-49b7-b225-80ef88664386 http://localhost:8094/greeting
{"id":5,"content":"Hello, World!"}
```

### 8. Add web management
#### Add spring-boot actuator dependency in the `pom.xml`.
```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-actuator</artifactId>
	</dependency>
</dependencies>
```

Open it on the browser [http://localhost:8094/health](http://localhost:8094/health).

#### Move the management port to `:8194`. Add this line in `application.properties`.
```
management.port: 8194
```

Open it on the browser [http://localhost:8194/health](http://localhost:8194/health).

#### Web Management endpoints:
ID | Description | Sensitive Default
--- | --- | ---
`actuator` | Provides a hypermedia-based “discovery page” for the other endpoints. Requires Spring HATEOAS to be on the classpath. | true
`auditevents` | Exposes audit events information for the current application. | true
`autoconfig` | Displays an auto-configuration report showing all auto-configuration candidates and the reason why they ‘were’ or ‘were not’ applied. | true
`beans` | Displays a complete list of all the Spring beans in your application. | true
`configprops` | Displays a collated list of all @ConfigurationProperties. | true
`dump` | Performs a thread dump. | true
`env` | Exposes properties from Spring’s ConfigurableEnvironment. | true
`flyway` | Shows any Flyway database migrations that have been applied. | true
`health` | Shows application health information (when the application is secure, a simple ‘status’ when accessed over an unauthenticated connection or full message details when authenticated). | false
`info` | Displays arbitrary application info. | false
`loggers` | Shows and modifies the configuration of loggers in the application. | true
`liquibase` | Shows any Liquibase database migrations that have been applied. | true
`metrics` | Shows ‘metrics’ information for the current application. | true
`mappings` | Displays a collated list of all @RequestMapping paths. | true
`shutdown` | Allows the application to be gracefully shutdown (not enabled by default). | true
`trace` | Displays trace information (by default the last 100 HTTP requests). | true

If you are using Spring MVC, the following additional endpoints can also be used:

ID | Description | Sensitive Default
--- | --- | ---
`docs` | Displays documentation, including example requests and responses, for the Actuator’s endpoints. Requires spring-boot-actuator-docs to be on the classpath. | false
`heapdump` | Returns a GZip compressed hprof heap dump file. | true
`jolokia` | Exposes JMX beans over HTTP (when Jolokia is on the classpath). | true
`logfile` | Returns the contents of the logfile (if logging.file or logging.path properties have been set). Supports the use of the HTTP Range header to retrieve part of the log file’s content. | true

### 9. Account Management (with H2 database)
#### Add spring-boot security dependency in the `pom.xml`.
```xml
<dependencies>
	<!-- Database -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jpa</artifactId>
	</dependency>

	<dependency>
		<groupId>com.h2database</groupId>
		<artifactId>h2</artifactId>
		<scope>runtime</scope>
	</dependency>
</dependencies>
```

#### Default accounts:
Username | password | Role
--- | --- | ---
admin | admin | ADMIN
actuator | management | ACTUATOR
rwibawa | Ch@ng3M3! | USER

> Open the [H2 Console](http://localhost:8080/h2-console). The DB is `jdbc:h2:mem:testdb`.

> Only user with `ACTUATOR` role can access the web management endpoints.

##### Get `health`
> Basic Auth
> username: actuator
> password: management

```bash
curl -X GET \
  http://localhost:8194/health \
  -H 'authorization: Basic YWN0dWF0b3I6bWFuYWdlbWVudA=='
```
  
### 10. Setup _flyway_ for database migration.
#### Add `flyway-core` dependency in the `pom.xml`.
```xml
<dependencies>
	<dependency>
		<groupId>org.flywaydb</groupId>
		<artifactId>flyway-core</artifactId>
        <scope>provided</scope>
	</dependency>
</dependencies>
```

#### The migration scripts default location is in `db/migration`.
```bash
$ cd src/main/resources/
$ mkdir db
$ mkdir db/migration
```

### 11. Authentication Server
[Spring oauth2 server tutorial](https://spring.io/guides/tutorials/spring-boot-oauth2/#_social_login_authserver).
[https://github.com/spring-guides/tut-spring-boot-oauth2.git](https://github.com/spring-guides/tut-spring-boot-oauth2.git).

#### Get token as oauth client
```bash
curl -X POST \
  http://acme:acmesecret@localhost:8094/oauth/token \
  -d grant_type=client_credentials
```

#### Get token as a user
```bash
curl -X POST \
  http://acme:acmesecret@localhost:8094/oauth/token \
  -d grant_type=password
  -d username=rwibawa
  -d password=Ch%40ng3M3!
```

#### Get user info
```bash
curl -X GET \
  http://localhost:8094/me \
  -H 'authorization: bearer <token>'
```

#### Get the greeting
```bash
curl -X GET \
  http://localhost:8094/greeting \
  -H 'authorization: bearer <token>'
```

### 12. Deploy to Heroku
[Getting Started on Heroku with Java](https://devcenter.heroku.com/articles/getting-started-with-java#introduction)

#### Add `Procfile`
```
web:    java -Dspring.profiles.active=heroku-pg -jar target/spring-boot-07-0.0.1-SNAPSHOT.jar
```

### Create property file `src/main/resources/application-heroku-pg.yml`
Get Postgres env. var `JDBC_DATABASE_URL` for db connection string.
```yml
spring:
  jpa:
    database: POSTGRESQL
    show-sql: false
    generate-ddl: false
  datasource:
    platform: postgres
    url: ${JDBC_DATABASE_URL:}
    driverClassName: org.postgresql.Driver

flyway:
  baseline-on-migrate: false
  locations: db/local
```

#### Deploy the app
```bash
$ heroku create
$ git add .
$ git commit -m "Heroku configuration."
$ git push heroku master

$ heroku ps:scale web=1
$ heroku open
$ heroku logs

$ heroku addons
$ heroku config
$ heroku pg
```
