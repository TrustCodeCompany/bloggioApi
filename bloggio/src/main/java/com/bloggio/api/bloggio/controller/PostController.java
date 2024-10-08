package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.PostListDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.dto.post.response.PostByFilterResponse;
import com.bloggio.api.bloggio.dto.post.response.PostByTop4Response;
import com.bloggio.api.bloggio.dto.post.response.PostResponse;

import com.bloggio.api.bloggio.payload.post.request.PostByDateAndPageRequest;
import com.bloggio.api.bloggio.payload.post.request.PostByFiltersRequest;
import com.bloggio.api.bloggio.payload.post.request.PostLikeUpdateRequest;
import com.bloggio.api.bloggio.persistence.projection.PostByFilters;
import com.bloggio.api.bloggio.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/Post")
//@CrossOrigin
@AllArgsConstructor
@Log4j2
public class PostController {

    private final PostService postService;
    private final ObjectMapper objectMapper;

    /*@PostMapping("/Create")
    public ResponseEntity<PostResponse> Create(CreatePostRequest createPostRequest) {
        log.info("create endpoint");
        log.info("create postv2 - CreatePostRequest {} {}", Objects.nonNull(createPostRequest.getPost()), Objects.nonNull(createPostRequest.getFile().getOriginalFilename()));
        PostResponse response = PostResponse.builder()
                .postId(postService.create(convert(createPostRequest.getPost()), createPostRequest.getFile()))
                .message("Post creado correctamente")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }*/

    @PostMapping(value = "/Create-v2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> CreateV2(
            @RequestPart("post") PostSaveDTO postSaveDTO,
            @RequestPart("file") MultipartFile file) {
        log.info("createV2 - CreatePostRequest {} {}", Objects.nonNull(postSaveDTO), Objects.nonNull(file.getOriginalFilename()));
        PostResponse response = PostResponse.builder()
                .postId(postService.createV2(postSaveDTO, file))
                .message("Post creado correctamente")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostListDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll());
    }

    /*@GetMapping("/GetTop4Post")
    public ResponseEntity<List<PostListDTO>> getTop4Post() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getTop4Post());
    }*/

    @GetMapping("/GetTop4Post")
    public ResponseEntity<PostByTop4Response> GetTop4Post() {

        var response = postService.getTop4Post();

        return ResponseEntity.status(HttpStatus.OK).body(
                PostByTop4Response.builder().data(response).build()
        );
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
    public ResponseEntity<PostListDTO> findById(@PathVariable("id") String uuid) {
        PostListDTO post = postService.findById(uuid);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @PostMapping(value = "/edit-post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateById(@RequestPart("post") PostSaveDTO postSaveDTO,
                                                  @RequestPart("file") MultipartFile file) {
        postService.update(postSaveDTO, file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostSaveDTO> deleteById(@PathVariable("id") String id) {
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SneakyThrows
    public PostSaveDTO convert(String source) {
        if (Objects.nonNull(source)) {
            log.info("convert source - {}", source);
            return objectMapper.readValue(source, PostSaveDTO.class);
        }
        return null;
    }

    @GetMapping("/get-by-user/{id}")
    public ResponseEntity<PostByFilterResponse> getPostPublishedByUserId(@PathVariable("id") String userId,
                                                                @RequestParam("limit") int limit,
                                                                @RequestParam("offset") int offset) {
        List<PostByFilters> response = postService.getPostByUser(offset, limit, userId);
        return ResponseEntity.status(HttpStatus.OK).body(PostByFilterResponse.builder()
                .data(response)
                .limit(limit)
                .page(offset)
                .total(CollectionUtils.isNotEmpty(response) ? response.get(0).getFullCount() : 0)
                .build());
    }

    @PutMapping("/add-like/{id}")
    public ResponseEntity<Void> addLike(@PathVariable("id") String id,
                                                  @RequestBody PostLikeUpdateRequest PostLikeUpdateRequest) {
        postService.updateLike(id, PostLikeUpdateRequest, "add");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/minus-like/{id}")
    public ResponseEntity<Void> minusLike(@PathVariable("id") String id,
                                           @RequestBody PostLikeUpdateRequest PostLikeUpdateRequest) {
        postService.updateLike(id, PostLikeUpdateRequest, "minus");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/recommended-post")
    public ResponseEntity<List<PostByFilters>> GetRecommendedPost(@RequestParam("user-id") String user,
                                                                @RequestParam("category-name") String category) {
        var response = postService.getRecommendedPost(user, category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-posts-draft-by-user/{id}")
    public ResponseEntity<PostByFilterResponse> getPostSaveByUserId(@PathVariable("id") String userId,
                                                                @RequestParam("limit") int limit,
                                                                @RequestParam("offset") int offset) {
        List<PostByFilters> response = postService.getPostsDraftByUser(offset, limit, userId);
        return ResponseEntity.status(HttpStatus.OK).body(PostByFilterResponse.builder()
                .data(response)
                .limit(limit)
                .page(offset)
                .total(CollectionUtils.isNotEmpty(response) ? response.get(0).getFullCount() : 0)
                .build());
    }

}
