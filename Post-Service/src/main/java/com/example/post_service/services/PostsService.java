package com.example.post_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.post_service.dto.PostCreateRequestDto;
import com.example.post_service.dto.PostDto;
import com.example.post_service.entity.PostEntity;
import com.example.post_service.exceptions.ResourceNotFoundException;
import com.example.post_service.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {


    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final String CACHE_NAME = "posts";

    @CachePut(cacheNames = CACHE_NAME, key = "#result.id")
    public PostDto createPosts(PostCreateRequestDto postsReqDto, Long userId) {
        PostEntity postEntity = modelMapper.map(postsReqDto, PostEntity.class);
        postEntity.setUserId(userId);

        PostEntity savedPost = postRepository.save(postEntity);
        return modelMapper.map(savedPost, PostDto.class);

    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#postsId")
    public PostDto getPostsById(Long postsId) {

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
