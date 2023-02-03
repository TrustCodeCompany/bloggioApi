package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.PostDTO;
import com.bloggio.api.bloggio.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/Create")
    public ResponseEntity<PostDTO> Create(@RequestBody PostDTO postDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(postDTO));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
