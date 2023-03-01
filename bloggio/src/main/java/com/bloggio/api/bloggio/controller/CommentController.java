package com.bloggio.api.bloggio.controller;

import com.bloggio.api.bloggio.dto.CommentSaveDTO;
import com.bloggio.api.bloggio.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
