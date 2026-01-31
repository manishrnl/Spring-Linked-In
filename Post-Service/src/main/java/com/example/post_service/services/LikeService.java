package com.example.post_service.services;

import com.example.post_service.auth.UserContextHolder;
import com.example.post_service.entity.PostEntity;
import com.example.post_service.entity.PostLikeEntity;
import com.example.post_service.events.PostLikedEvents;
import com.example.post_service.events.PostUnLikedEvents;
import com.example.post_service.exceptions.BadRequestException;
import com.example.post_service.exceptions.ResourceNotFoundException;
import com.example.post_service.repository.PostLikeRepository;
import com.example.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final KafkaTemplate<Long, PostLikedEvents> kafkaLikedTemplate;
    private final KafkaTemplate<Long, PostUnLikedEvents> kafkaUnLikeTemplate;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public void likePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post Not Found"));


        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (alreadyLiked)
            throw new ResourceNotFoundException("User has already liked the posts");

        PostLikeEntity postLike = new PostLikeEntity();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        PostLikeEntity savedPosts = postLikeRepository.save(postLike);

        PostLikedEvents postLikedEvents = PostLikedEvents.newBuilder()
                .setPostId(savedPosts.getPostId())
                .setLikedByUserId(userId)
                .setCreatorId(postEntity.getUserId())
                .build();
        kafkaLikedTemplate.send("post-liked-topic", postId, postLikedEvents);

        log.info("Posts Liked successfully and is being sent as a notification via Kafka");

    }

    @Transactional
    public void unLikePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();

        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post Not Found"));

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (!alreadyLiked)
            throw new BadRequestException("Please like the Posts then try unliking it");


      postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        PostUnLikedEvents unLikedEvents = PostUnLikedEvents.newBuilder()
                .setPostId(postId)
                .setLikedByUserId(userId)
                .setCreatorId(postEntity.getUserId())
                .build();
        kafkaUnLikeTemplate.send("post-unliked-topic", postId, unLikedEvents);


        log.info("Post ID {} unliked by User ID {}", postId, userId);
    }
}
