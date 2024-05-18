package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.PostListDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.payload.post.request.CreatePostRequest;
import com.bloggio.api.bloggio.payload.post.response.PostResponse;
import com.bloggio.api.bloggio.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/Post")
@CrossOrigin
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final ObjectMapper objectMapper;

    @PostMapping("/Create")
    public ResponseEntity<PostResponse> Create(CreatePostRequest createPostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                PostResponse.builder()
                        .postId(postService.create(convert(createPostRequest.getPost()), createPostRequest.getFile()))
                        .message("Post creado correctamente")
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<List<PostListDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostListDTO> findById(@PathVariable("id") UUID uuid) {
        PostListDTO post = postService.findById(uuid);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostSaveDTO> updateById(@PathVariable("id") UUID id,
                                                  @Valid @RequestBody PostSaveDTO postSaveDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(id, postSaveDTO));
    }


    @SneakyThrows
    public PostSaveDTO convert(String source) {
        if (Objects.nonNull(source)) {
            return objectMapper.readValue(source, PostSaveDTO.class);
        }
        return null;
    }


}
