## Steps to run this application

1. Start Docker Desktop application first

2. Start the Neo4j Desktop application and the Database so configured while setting up the
   project

- Configure neo4j db . Delete all data inside particular db used for this project by running
  command :

```
    // Deletes all relationships and all nodes
    MATCH (n)
    DETACH DELETE n;
```

- Now run below commands inside the terminal of neo4j db

```
    MERGE (a:Person {userId: 1, name: "Manish Kumar"});
    MERGE (a:Person {userId: 2, name: "Radhe Shyam"});
    MERGE (a:Person {userId: 3, name: "Premanand Ji Maharaj"});
    MERGE (b:Person {userId: 4, name: "Indresh Ji Maharaj"});  
```

3. Run docker via cmd inside root directory of the project using below command :

```
   docker compose down 
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