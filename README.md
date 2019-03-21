# Backend-challenge


[Open heroku](https://backend-api-challenge.herokuapp.com/api/swagger-ui.html)

[Open local](http://localhost:5000/api/swagger-ui.html)

### Installation

```sh
$ mvn clean install
$ java -jar /target/backend-challenge-1.0-SNAPSHOT.jar
```

### Environment variables

| Environment variable | Default value |
| ------ | ------ |
| APPLICATION_PORT | 5000 |
| API_USERNAME | admin |
| API_PASSWORD | admin |
| DISCOVERY_ENABLED | false |
| DISCOVERY_URL | http://localhost:8761 |
| MYSQL_HOST | db4free.net |
| MYSQL_DATABASE | brwchallenge |
| MYSQL_USERNAME | backendchallenge |
| MYSQL_PASSWORD | Axqdjfjj |

### Asynchronous processing

API preconfigured to work with parallel and asynchronous processing.
All usecases are ready to work with the Hystrix circuit breaker, and you can configure the application to work in multiple instances.
| Environment variable |
| ------ |
| DISCOVERY_ENABLED |
| DISCOVERY_URL |

### Database

Application module spring-boot-data-jpa
| Environment variable |
| ------ |
| MYSQL_HOST |
| MYSQL_DATABASE |
| MYSQL_USERNAME |
| MYSQL_PASSWORD |

### Docker

Preconfigured application for build in Docker - Config: Dockerfile

```sh
$ docker build -t backend-challenge .
$ docker run -d -p 5000:5000 --net=host backend-challenge
```

### Heroku

Preconfigured application for build in Heroku - Config: Procfile

```sh
$ heroku apps:create backend-api-challenge
$ git add .
$ git commit -m "heroku: -- build"
$ git push heroku master
$ heroku ps:scale web=1
$ heroku open
```

### Security

Spring security configured, all requests pass through the spring security interface, it is possible to change the access credentials through environment variables

| Environment variable |
| ------ |
| API_USERNAME |
| API_PASSWORD |

### Swagger

Swagger configured and available on the route: /api/swagger-ui.html

### Architecture
Architecture based on onion architecture