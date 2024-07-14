package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.CommentDTO;
import com.bloggio.api.bloggio.dto.CommentSaveDTO;
import com.bloggio.api.bloggio.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Comment")
public class CommentController {

    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/Create")
    public ResponseEntity<CommentSaveDTO> Create(@Valid @RequestBody CommentSaveDTO commentSaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(commentSaveDTO));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> findAll(@RequestParam("postId") String postId){
            return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(postId));
    }
}
