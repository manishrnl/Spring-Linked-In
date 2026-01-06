package com.example.post_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.post_service.entity.PostLikeEntity;
import com.example.post_service.exceptions.BadRequestException;
import com.example.post_service.exceptions.ResourceNotFoundException;
import com.example.post_service.repository.PostLikeRepository;
import com.example.post_service.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public void likePost(Long postId, Long userId) {
        boolean isExists = postRepository.existsById(postId);
        if (!isExists) throw new BadRequestException("You must be logged in to Like a post");
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (alreadyLiked)
            throw new ResourceNotFoundException("User has already liked the posts");

        PostLikeEntity postLike = new PostLikeEntity();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
        log.info("Posts Liked Successfully");

    }

    @Transactional
    public void unLikePost(Long postId, long userId) {
        boolean isExists = postLikeRepository.existsByPostId(postId);
        if (!isExists)
            throw new ResourceNotFoundException("Post Not Found");

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (!alreadyLiked)
            throw new BadRequestException("Please like the Posts then try unliking it");
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);

        log.info("Posts unliked ");
    }
}
