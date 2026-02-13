package com.example.connection_service.service;

import com.example.connection_service.auth.UserContextHolder;
import com.example.connection_service.entity.PersonEntity;
import com.example.connection_service.events.AcceptConnectionRequestEvents;
import com.example.connection_service.events.SendConnectionRequestEvents;
import com.example.connection_service.repository.PersonRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvents> acceptKafkaTemplate;
    private final KafkaTemplate<Long, SendConnectionRequestEvents> sendKafkaTemplate;

    public List<PersonEntity> get1DegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting first degree connections for user with id: {}", userId);
        return personRepository.get1DegreeConnections(userId);

    }

    public List<PersonEntity> get2DegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting Second Degree Connections ");
        return personRepository.get2DegreeConnections(userId);
    }

    public List<PersonEntity> get3DegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting 3rd Degree Connections");
        return personRepository.get3DegreeConnections(userId);
    }

    public @Nullable List<PersonEntity> get4DegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Getting 4 degree Connections");
        return personRepository.get4DegreeConnections(userId);
    }

//


    @Transactional
    public Boolean sendConnectionRequest(Long receiverId) {
        log.info("Trying to send connection request to user with id: {}", receiverId);
        Long senderId = UserContextHolder.getCurrentUserId();

        if (senderId == null)
            throw new RuntimeException("Unauthorized User !!! Please login to continue.");
        if (senderId.equals(receiverId))
            throw new RuntimeException("Cannot send connection request to yourself !!!");

        // Validation logic - safe null handling
        Boolean requestExistsWrapper = personRepository.connectionRequestExists(senderId, receiverId);
        boolean alreadySentRequest = requestExistsWrapper != null && requestExistsWrapper;
        if (alreadySentRequest)
            throw new RuntimeException("Connection Request already exists . Cannot send request again !!!");

        Boolean connectedWrapper = personRepository.alreadyConnected(senderId, receiverId);
        boolean alreadyConnected = connectedWrapper != null && connectedWrapper;
        if (alreadyConnected)
            throw new RuntimeException("Already Connected . Cannot send again !!!");

        // 1. Update Neo4j Database
        personRepository.addConnectionRequest(senderId, receiverId);
        log.info("Successfully persisted connection request in DB for receiver: {}", receiverId);

        // 2. Build Avro Event
        SendConnectionRequestEvents sendEvent = SendConnectionRequestEvents.newBuilder()
                .setSenderId(senderId)
                .setReceiverId(receiverId)
                .build();

        // 3. Send to Kafka (Using receiverId as the KEY)
        sendKafkaTemplate.send("connection-request-topic", receiverId, sendEvent);
        log.info("Sent SendConnectionRequestEvent to Kafka for receiver: {}", receiverId);

        return true;
    }

    @Transactional
    public Boolean acceptConnectionRequest(Long senderId) {
        log.info("Trying to accept connection request from user with id: {}", senderId);
        Long receiverId = UserContextHolder.getCurrentUserId();

        if (receiverId == null) throw new RuntimeException("Unauthorized");

        // Safe null handling
        Boolean requestExistsWrapper = personRepository.connectionRequestExists(senderId, receiverId);
        boolean requestExists = requestExistsWrapper != null && requestExistsWrapper;
        if (!requestExists)
            throw new RuntimeException("No Connection Request found from user with id: " + senderId);

        Boolean connectedWrapper = personRepository.alreadyConnected(senderId, receiverId);
        boolean alreadyConnected = connectedWrapper != null && connectedWrapper;
        if (alreadyConnected)
            throw new RuntimeException("Already Connected . Cannot accept again !!!");

        // 1. Update Neo4j (Delete REQUESTED_TO, Create CONNECTED_TO)
        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Successfully accepted connection request in DB from sender: {}", senderId);

        // 2. Build Avro Event
        AcceptConnectionRequestEvents acceptEvent = AcceptConnectionRequestEvents.newBuilder()
                .setSenderId(senderId)
                .setReceiverId(receiverId)
                .build();

        // 3. Send to Kafka (Using senderId as the KEY)
        acceptKafkaTemplate.send("accept-connection-request-topic", senderId, acceptEvent);
        log.info("Sent AcceptConnectionRequestEvent to Kafka for sender: {}", senderId);

        return true;
    }

    @Transactional
    public Boolean rejectConnectionRequest(Long senderId) {
        log.info("Trying to reject connection request from user with id: {}", senderId);
        Long receiverId = UserContextHolder.getCurrentUserId();

        if (receiverId == null) throw new RuntimeException("Unauthorized");

        // Safe null handling
        Boolean requestExistsWrapper = personRepository.connectionRequestExists(senderId, receiverId);
        boolean requestExists = requestExistsWrapper != null && requestExistsWrapper;
        if (!requestExists)
            throw new RuntimeException("No Connection Request found from user with id: " + senderId);

        // Update Neo4j (Delete REQUESTED_TO)
        personRepository.rejectConnectionRequest(senderId, receiverId);
        log.info("Successfully rejected connection request in DB from user with id: {}", senderId);

        // Note: Usually, we don't send a Kafka event for rejections to avoid
        // notifying the sender they were rejected, but you can add one if needed.
        return true;
    }
}