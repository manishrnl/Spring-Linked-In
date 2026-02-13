### Run the bat file "Build and push to dockerHub.bat"  present inside root directory via cmd and with for its build to finish

### Once image is build and is uploaded to Docker Hub , run the below command inside the terminal

```
    docker-compose down --remove-orphans
    docker volume prune -f
    cls
    docker-compose up
    
```

```      
    docker push manishrnl/linked-in-app-connection-service:v0.0.1
    docker push manishrnl/linked-in-app-post-service:v0.0.1
    docker push manishrnl/linked-in-app-user-service:v0.0.1
    docker push manishrnl/linked-in-app-notification-service:v0.0.1
    docker push manishrnl/linked-in-app-discovery-service:v0.0.1
    docker push manishrnl/linked-in-app-api-gateway:v0.0.1
    
```

## Once application runs paste the data inside neo4j web page at URL

```
        https://browser.neo4j.io/
```

- Configure neo4j db . Delete all data inside particular db used for this project and insert
  sample data by running command :

```
    // Deletes all relationships and all nodes
    MATCH (n) DETACH DELETE n;

   // 1. Create 50 Users
    UNWIND range(1, 50) AS id
    MERGE (p:Person {userId: id})
    SET p.name = "Manish " + id;
    
    // 2. Create a "Chain" to ensure 5-degree depth (1-2-3-4-5-6)
    // This guarantees that User 6 is 5 degrees from User 1
    MATCH (u1:Person {userId: 1}), (u2:Person {userId: 2}) MERGE (u1)-[:CONNECTED_TO]-(u2);
    MATCH (u2:Person {userId: 2}), (u3:Person {userId: 3}) MERGE (u2)-[:CONNECTED_TO]-(u3);
    MATCH (u3:Person {userId: 3}), (u4:Person {userId: 4}) MERGE (u3)-[:CONNECTED_TO]-(u4);
    MATCH (u4:Person {userId: 4}), (u5:Person {userId: 5}) MERGE (u4)-[:CONNECTED_TO]-(u5);
    MATCH (u5:Person {userId: 5}), (u6:Person {userId: 6}) MERGE (u5)-[:CONNECTED_TO]-(u6);
    
    // 3. Create a "Web" for variety (Connect User 1 to many 1st degrees)
    UNWIND range(7, 15) AS id
    MATCH (u1:Person {userId: 1}), (target:Person {userId: id})
    MERGE (u1)-[:CONNECTED_TO]-(target);
    
    // 4. Create 2nd Degree connections (Connect User 7 to 16-25)
    UNWIND range(16, 25) AS id
    MATCH (u7:Person {userId: 7}), (target:Person {userId: id})
    MERGE (u7)-[:CONNECTED_TO]-(target);
    
    // 5. Create 3rd Degree connections (Connect User 16 to 26-35)
    UNWIND range(26, 35) AS id
    MATCH (u16:Person {userId: 16}), (target:Person {userId: id})
    MERGE (u16)-[:CONNECTED_TO]-(target);
    
    // 6. Create 4th Degree connections (Connect User 26 to 36-45)
    UNWIND range(36, 45) AS id
    MATCH (u26:Person {userId: 26}), (target:Person {userId: id})
    MERGE (u26)-[:CONNECTED_TO]-(target);
    
    // 7. Create 5th Degree connections (Connect User 36 to 46-50)
    UNWIND range(46, 50) AS id
    MATCH (u36:Person {userId: 36}), (target:Person {userId: id})
    MERGE (u36)-[:CONNECTED_TO]-(target);
       
```

## Track all notifications at URL below

```

    http://localhost:9021/clusters

```

## You are now ready to make connections and api call via postman

# HAPPY coding !!!