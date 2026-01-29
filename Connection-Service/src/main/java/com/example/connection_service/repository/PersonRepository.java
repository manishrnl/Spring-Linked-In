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

}
