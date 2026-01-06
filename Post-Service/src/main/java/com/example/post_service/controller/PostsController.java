package com.example.post_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.post_service.dto.PostCreateRequestDto;
import com.example.post_service.dto.PostDto;
import com.example.post_service.dto.PostsDto;
import com.example.post_service.services.PostsService;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postsDto) {
        PostDto postDto1 = postsService.createPosts(postsDto, 1L);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{postsId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postsId) {
        PostDto postDto = postsService.getPostsById(postsId);
        return postDto != null ? new ResponseEntity<>(postDto, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> postDto = postsService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(postDto) ;}


}
