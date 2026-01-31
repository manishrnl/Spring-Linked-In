## Steps to run this application

1. Start Docker Desktop application first


2. Start the Neo4j Desktop application and the Database so configured while setting up the
   project


3. Run docker via cmd inside root directory of the project using below command :

```
   docker-compose up -d
```

- Make sure to connect with Internet as it is required throughout the project && Run the
  Application in order as mentioned Below :

1. Discovery / Eureka Server
2. Connection , User , Post Service
3. Notification Service , Api Gateway

### Now Send request via postman at url @PostMapping http://localhost:8080/api/v1/posts/core and body as raw JSON

```json
{
  "content": "this is a content "
}
```

### Similarly send request via postman at url @PostMapping http://localhost:8080/api/v1/posts/likes/1 No body required

- Now navigate to the browser and open the below url to see the notifications created

```
    http://localhost:9021
```

# Enjoy Coding !