package com.example.notification_service.consumer;

import com.example.connection_service.events.SendConnectionRequestEvents;
import com.example.notification_service.servvice.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsServiceConsumer {
    private final SendNotification sendNotification;

    @KafkaListener(topics = "connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvents sendConnectionRequestEvents) {
        String message = String.format("You have received a connection request from user with ID %d.", sendConnectionRequestEvents.getSenderId());
        sendNotification.send(sendConnectionRequestEvents.getReceiverId(), message);
        log.info("Received connection request event");

    }

    @KafkaListener(topics = "accept-connection-request-topic")
    public void handleAcceptConnectionRequest(SendConnectionRequestEvents acceptConnectionRequestEvents) {
        String message = String.format("Your connection request to user with ID %d has been accepted.", acceptConnectionRequestEvents.getReceiverId());
        sendNotification.send(acceptConnectionRequestEvents.getSenderId(), message);
    }

}

