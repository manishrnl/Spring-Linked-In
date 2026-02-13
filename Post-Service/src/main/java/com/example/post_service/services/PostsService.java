package com.example.post_service.services;

import com.example.post_service.auth.UserContextHolder;
import com.example.post_service.clients.ConnectionClient;
import com.example.post_service.dto.PersonDto;
import com.example.post_service.dto.PostCreateRequestDto;
import com.example.post_service.dto.PostDto;
import com.example.post_service.entity.PostEntity;
import com.example.post_service.events.PostCreatedEvents;
import com.example.post_service.exceptions.ResourceNotFoundException;
import com.example.post_service.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final KafkaTemplate<Long, PostCreatedEvents> kafkaTemplate;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME = "posts";
    private final ConnectionClient connectionClient;

    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    public PostDto createPosts(PostCreateRequestDto postsReqDto) {
        Long userId = UserContextHolder.getCurrentUserId();
        PostEntity postEntity = modelMapper.map(postsReqDto, PostEntity.class);
        postEntity.setUserId(userId);

        PostEntity savedPost = postRepository.save(postEntity);

        PostCreatedEvents postCreatedEvents = PostCreatedEvents.newBuilder()
                .setPostId(savedPost.getId())
                .setCreatorId(userId)
                .setContent(savedPost.getContent())
                .build();
        kafkaTemplate.send("post-created-topic", postCreatedEvents);
        log.info("Posts created and is being sent as a notification via Kafka");
        return modelMapper.map(savedPost, PostDto.class);

    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#postsId")
    public PostDto getPostsById(Long postsId) {
        log.debug("Retrieving post with ID: {}", postsId);
        Long userId = UserContextHolder.getCurrentUserId();

        List<PersonDto> firstDegConn = connectionClient.get1DegreeConnections();

//        TODO : send Notification to All clients
        log.info("Printing 1st degree  connections: {} ", firstDegConn.toString());
        Optional<PostEntity> postEntity = Optional.of(postRepository.findById(postsId).orElseThrow(() -> new ResourceNotFoundException("User does not exists")));

        return modelMapper.map(postEntity, PostDto.class);
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#userId")
    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<PostEntity> postEntities = postRepository.findByUserId(userId);
        List<PostDto> allPosts = postEntities
                .stream()
                .map(entity -> modelMapper.map(entity, PostDto.class))
                .collect(Collectors.toList());
        return allPosts;
    }
}
