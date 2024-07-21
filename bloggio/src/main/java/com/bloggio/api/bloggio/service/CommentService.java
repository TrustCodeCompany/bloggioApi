package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.CommentDTO;
import com.bloggio.api.bloggio.dto.CommentSaveDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.mapper.CommentMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Comment;
import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.repository.CommentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapperImpl commentMapper;


    public CommentService(CommentRepository commentRepository, CommentMapperImpl commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentSaveDTO create(CommentSaveDTO commentSaveDTO) {
        Comment commentSave = null;

        try {
            Comment comment = commentMapper.commentDtoToComment(commentSaveDTO);
            commentSave = commentRepository.save(comment);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return commentMapper.commentToCommentDTO(commentSave);
    }

    public List<CommentDTO> findAll(String postId) {

        return this.commentRepository.findAllByPost(UUID.fromString(postId))
                .stream().map(this.commentMapper::commentEntityToCommentDTO)
                .collect(Collectors.toList());
    }
}
