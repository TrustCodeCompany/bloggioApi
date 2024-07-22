package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.CommentDTO;
import com.bloggio.api.bloggio.dto.CommentSaveDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.mapper.CommentMapperImpl;
import com.bloggio.api.bloggio.mapper.UsersMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Comment;
import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.repository.CommentRepository;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CommentService {

    private final CommentRepository commentRepository;

    private final UsersRepository usersRepository;

    private final CommentMapperImpl commentMapper;

    private final UsersMapperImpl usersMapper;


    public CommentService(CommentRepository commentRepository, UsersRepository usersRepository, CommentMapperImpl commentMapper, UsersMapperImpl usersMapper) {
        this.commentRepository = commentRepository;
        this.usersRepository = usersRepository;
        this.commentMapper = commentMapper;
        this.usersMapper = usersMapper;
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

        var commentList = this.commentRepository.findAllByPost(UUID.fromString(postId));

        return commentList.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            if (Objects.nonNull(comment.getUserId())) {
                var user = this.usersRepository.findById(comment.getUserId());
                if (user.isPresent()) {
                    var userDto = usersMapper.usersToUsersDTO(user.get());
                    commentDTO.setUsersDTO(userDto);
                }
            }
            commentDTO.setCommentId(comment.getCommentId());
            commentDTO.setCommentContent(comment.getCommentContent());
            commentDTO.setCommentState(comment.getCommentState());
            commentDTO.setCommentLikes(comment.getCommentLikes());
            commentDTO.setCommentIdReply(comment.getCommentIdReply());
            commentDTO.setCommentTimestampCreate(comment.getCommentTimestampCreate());
            commentDTO.setCommentTimestampUpdate(comment.getCommentTimestampUpdate());
            commentDTO.setPostId(comment.getPost().getPostId());
            return commentDTO;
        }).collect(Collectors.toList());

    }
}
