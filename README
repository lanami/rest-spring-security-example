# Secure RESTful Web Service on Spring Security

Three types of users:
USER - can CRUD own data
MANAGER - can CRU other users' data
ADMIN - can CRUD everybody's data

## Technology stack
- Java
- Spring (Core, MVC, ORM)
- Spring Security
- Hibernate
- Jackson

Dependency / Environment Management:
- Gradle
- Liquibase
- Docker


## Running this example
1. Install your tools
1.1 Gradle
Manually:
http://gradle.org/gradle-download/

With Homebrew:
```
brew install gradle
```

1.2 Docker Engine and Docker Compose
http://docs.docker.com/

Docker allows to manage the infrastructure required to run your app in a system-independent fashion. Compose is a tool for defining and running multi-container Docker applications.
In this case, you will be running two containers: a Tomcat container hosting the application and a MySQL container with the database. Docker handles images for the systems,
and Compose takes care of the choreography of multiple containers talking to each other.
See docker/appserver/Dockerfile and docker-compose.yml

2. Bring Docker environments up
From the project root directory
```
docker-compose up
```

A running db instance is required for a successful build as during the build process Liquibase will need it to run database management scripts, generate schema and load application seed data.

3. Build the application
From the project root directory
```
gradle build
```

build/exploded is the application working folder which is mapped directly to the Tomcat webapps folder. This is where the application runs from.
Every time the application is rebuilt Tomcat will pick up the changes.

build/logs is mapped to Tomcat logs folder and allows to view log files without remoting into the container.

See docker-compose.yml for configuration details.

4. Restart Tomcat
From the project root directory
```
docker restart tomcat
```
This is necessary since when the Tomcat container was first started the application was not yet built. Now that build/exploded folder has the binaries, we need Tomcat to reload load this context.

## Debugging and Troubleshooting
Remote debugging is enabled in Tomcat on port 9001, see docker/appserver/Dockerfile.
Remoting into a Docker container is possible with the following command:
```
docker exec -it <container_name> bash
```
Root password for MySQL instance is specified in docker-compose.yml



