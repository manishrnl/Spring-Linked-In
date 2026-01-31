package com.example.connection_service.repository;

import com.example.connection_service.entity.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {

    Optional<PersonEntity> getByName(String name);


    @Query("MATCH (personA:Person) -[CONNECTED_TO]-(personB:Person) " +
            "WHERE personA.userId = $userId " +
            "RETURN personB")
    List<PersonEntity> get1DegreeConnections(@Param("userId") Long userId);


    @Query("MATCH (p:Person {userId: $userId})-[:CONNECTED_TO*2]-(fof) " +
            "WHERE fof.userId <> $userId AND NOT (p)-[:CONNECTED_TO]-(fof) " +
            "RETURN  DISTINCT fof;")
    List<PersonEntity> get2DegreeConnections(@Param("userId") Long userId);


    @Query("MATCH (p:Person {userId: $userId})-[:CONNECTED_TO*3]-(threeHop) " +
            "WHERE threeHop.userId <> $userId " +
            "  AND NOT (p)-[:CONNECTED_TO*1..2]-(threeHop) " +  // Exclude yourself, 1st  degree, and 2nd degree
            "RETURN DISTINCT threeHop;")
    List<PersonEntity> get3DegreeConnections(@Param("userId") Long userId);


    @Query("MATCH (p:Person {userId:$userId}) -[:CONNECTED_TO*4]-(fourDeg) " +
            "WHERE fourDeg.userId <> $userId " +
            "AND NOT (p)-[:CONNECTED_TO*1..3]-(fourDeg)" +
            "RETURN DISTINCT fourDeg")
    List<PersonEntity> get4DegreeConnections(@Param("userId") Long userId);


    @Query("OPTIONAL MATCH (p1:Person {userId: $senderId})-[r:REQUESTED_TO]->" +
            "(p2:Person {userId: $receiverId}) RETURN r IS NOT NULL ")
    Boolean connectionRequestExists(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);


    @Query("OPTIONAL MATCH (p1:Person {userId: $senderId})-[r:CONNECTED_TO]-(p2:Person {userId: $receiverId}) " +
            "RETURN r IS NOT NULL")
    Boolean alreadyConnected(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId
    );


    // 3. Create connection request (directed)
    @Query("MATCH (p1:Person {userId: $senderId}) " +
            "MATCH (p2:Person {userId: $receiverId}) " +
            "MERGE (p1)-[:REQUESTED_TO]->(p2)  ")
    void addConnectionRequest(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);


    // 4. Accept → delete request + create undirected friendship
    @Query("MATCH (p1:Person {userId: $senderId})-[r:REQUESTED_TO]->(p2:Person {userId: $receiverId}) " +
            "DELETE r " +
            "MERGE (p1)-[:CONNECTED_TO]-(p2)")
    void acceptConnectionRequest(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);


    // 5. Reject → just delete the request
    @Query("""
            MATCH (p1:Person {userId: $senderId})-[r:REQUESTED_TO]->(p2:Person {userId: $receiverId})
            DELETE r
            """)
    void rejectConnectionRequest(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);


}
