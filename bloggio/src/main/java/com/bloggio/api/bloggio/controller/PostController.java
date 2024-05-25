package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.PostListDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.dto.post.response.PostByFilterResponse;
import com.bloggio.api.bloggio.dto.post.response.PostResponse;
import com.bloggio.api.bloggio.payload.post.request.CreatePostRequest;

import com.bloggio.api.bloggio.payload.post.request.PostByDateAndPageRequest;
import com.bloggio.api.bloggio.payload.post.request.PostByFiltersRequest;
import com.bloggio.api.bloggio.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/GetTop4Post")
    public ResponseEntity<List<PostListDTO>> getTop4Post() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getTop4Post());
    }

    @PostMapping("/GetAllPostByDateAndPage")
    public ResponseEntity<PostByFilterResponse> GetAllPostByDateAndPage(@RequestBody PostByDateAndPageRequest postByDateAndPageRequest) {

        var response = postService.getAllPostByDateAndPage(
                postByDateAndPageRequest.getOffset(), postByDateAndPageRequest.getLimit());

        return ResponseEntity.status(HttpStatus.OK).body(PostByFilterResponse.builder()
                .data(response)
                .limit(postByDateAndPageRequest.getLimit())
                .page(postByDateAndPageRequest.getOffset())
                .total(CollectionUtils.isNotEmpty(response) ? response.get(0).getFullCount() : 0)
                .build());
    }

    @PostMapping("/find-all-by-filters")
    public ResponseEntity<PostByFilterResponse> findAllByFilters(@RequestBody PostByFiltersRequest postByFiltersRequest) {

        var response = postService.getAllPostByFilters(
                postByFiltersRequest.getOffset(), postByFiltersRequest.getLimit(), postByFiltersRequest.getCategoryName(),
                postByFiltersRequest.getPostTitle(), postByFiltersRequest.getDate_start(), postByFiltersRequest.getDate_end());

        return ResponseEntity.status(HttpStatus.OK).body(PostByFilterResponse.builder()
                .data(response)
                .limit(postByFiltersRequest.getLimit())
                .page(postByFiltersRequest.getOffset())
                .total(CollectionUtils.isNotEmpty(response) ? response.get(0).getFullCount() : 0)
                .build());
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
