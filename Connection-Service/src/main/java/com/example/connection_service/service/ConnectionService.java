package com.example.connection_service.service;

import com.example.connection_service.entity.PersonEntity;
import com.example.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final PersonRepository personRepository;

    public List<PersonEntity> get1DegreeConnections(Long userId) {
        log.info("Getting 1st Degree Connections ");
        List<PersonEntity> personEntity = personRepository.get1DegreeConnections(userId);
        return personEntity;


    }


    public List<PersonEntity> get2DegreeConnections(Long userId) {
        log.info("Getting Second Degree Connections ");
        List<PersonEntity> personEntity = personRepository.get2DegreeConnections(userId);
        return personEntity;
    }

    public List<PersonEntity> get3DegreeConnections(Long userId) {
        log.info("Getting 3rd Degree Connections");
        List<PersonEntity> personEntity = personRepository.get3DegreeConnections(userId);
        return personEntity;
    }

    public @Nullable List<PersonEntity> get4DegreeConnections(Long userId) {
        log.info("Getting 4 degree Connections");
        List<PersonEntity> personEntity = personRepository.get4DegreeConnections(userId);
        return personEntity;
    }
}
