package com.example.post_service.controller;

import com.example.post_service.auth.UserContextHolder;
import com.example.post_service.clients.ConnectionClient;
import com.example.post_service.dto.PostCreateRequestDto;
import com.example.post_service.dto.PostDto;
import com.example.post_service.repository.PostRepository;
import com.example.post_service.services.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final ConnectionClient connectionClient;
    private final PostRepository postRepository;

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postsDto) {

        PostDto postDto1 = postsService.createPosts(postsDto);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{postsId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postsId, HttpServletRequest httpServletRequest) {
        String userId = httpServletRequest.getHeader("X-User-Id");
        log.info("User Id extracted from token is {} ", userId);


        PostDto postDto = postsService.getPostsById(postsId);
        return postDto != null ? new ResponseEntity<>(postDto, HttpStatus.OK) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("users/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser() {
        Long userId = UserContextHolder.getCurrentUserId();
        List<PostDto> postDto = postsService.getAllPostsOfUser(Long.valueOf(userId));
        return ResponseEntity.ok(postDto);
    }


}
