### Run the bat file "Build and push to dockerHub.bat"  present inside root directory via cmd and with for its build to finish

### Once image is build and is uploaded to Docker Hub , run the below command inside the terminal

```
    
    docker-compose down --remove-orphans
    docker volume prune -f
    cls
    docker-compose up
    
      
    docker push manishrnl/linked-in-app-connection-service:v0.0.1
    
```

## Once application runs paste the data inside neo4j web page at URL

```
        http://localhost:7474
        #  OR
        https://browser.neo4j.io/
```

- Configure neo4j db . Delete all data inside particular db used for this project and insert
  sample data by running command :

```
    // Deletes all relationships and all nodes
    MATCH (n) DETACH DELETE n;

    MERGE (a:Person {userId: 1, name: "Manish Kumar"});
    MERGE (a:Person {userId: 2, name: "Radhe Shyam"});
    MERGE (a:Person {userId: 3, name: "Premanand Ji Maharaj"});
    MERGE (b:Person {userId: 4, name: "Indresh Ji Maharaj"});  
```

## Track all notifications at URL below

```
    http://localhost:9021/clusters
```

## You are now ready to make connections and api call via postman

# HAPPY coding !!!