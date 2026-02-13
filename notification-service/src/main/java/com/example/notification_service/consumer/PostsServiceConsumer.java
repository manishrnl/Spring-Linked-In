package com.example.notification_service.consumer;

import com.example.notification_service.clients.ConnectionClient;
import com.example.notification_service.dto.PersonDto;
import com.example.notification_service.repository.NotificationsRepository;
import com.example.notification_service.servvice.SendNotification;
import com.example.post_service.events.PostCreatedEvents;
import com.example.post_service.events.PostLikedEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class PostsServiceConsumer {
    private final SendNotification sendNotification;
    private final ConnectionClient connectionClient;
    private final NotificationsRepository notificationsRepository;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvents postCreatedEvents) {
        log.info("Sending Notitfications: handlePostCreated for Post ID: {}", postCreatedEvents.getPostId());
        List<PersonDto> connections = connectionClient.get1DegreeConnections(postCreatedEvents.getCreatorId());
        for (PersonDto connection : connections) {
            sendNotification.send(connection.getUserId(),
                    "Your connection " + postCreatedEvents.getCreatorId() + " has created a new " +
                            "post.");
        }
    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvents postLikedEvents) {
        log.info("Sending Notification: handlePostLiked for Post ID: {}", postLikedEvents.getPostId());
        String message = String.format("Your post with ID %d has been liked by user with ID " +
                "%d.", postLikedEvents.getPostId(), postLikedEvents.getLikedByUserId());
        sendNotification.send(postLikedEvents.getCreatorId(), message);
    }


}
