package com.example.connection_service.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Person")
@Data
public class PersonEntity {

    @Id
    @GeneratedValue(GeneratedValue.InternalIdGenerator.class)
    private Long id; // This will map to the internal Neo4j <id>

    @Property("name")
    private String name;

    @Property("userId") // Your business ID (e.g., 0, 1, 2)
    private Long userId;

}


