package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.CommentDTO;
import com.bloggio.api.bloggio.dto.CommentSaveDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.exception.Exception;
import com.bloggio.api.bloggio.mapper.CommentMapperImpl;
import com.bloggio.api.bloggio.mapper.UsersMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Comment;
import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.repository.CommentRepository;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
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

        List<CommentDTO> replyComments = new ArrayList<>();

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

            var replies = this.commentRepository.findAllByCommentIdReply(comment.getCommentId());

            if (Objects.nonNull(replies)) {
                replies.forEach(replyCommentsItem -> {
                    Optional<Comment> obj = this.commentRepository.findById(replyCommentsItem.getCommentId());
                    CommentDTO commentDTOReply = this.commentMapper.commentEntityToCommentDTO(obj.get());
                    if (Objects.nonNull(obj.get().getUserId())) {
                        var user = this.usersRepository.findById(obj.get().getUserId());
                        if (user.isPresent()) {
                            var userDto = usersMapper.usersToUsersDTO(user.get());
                            commentDTOReply.setUsersDTO(userDto);
                        }
                    }

                    replyComments.add(commentDTOReply);
                    commentDTO.setCommentsReply(replyComments);
                });
            }

            /*if (Objects.nonNull(comment.getCommentIdReply()) && StringUtils.isNotEmpty(comment.getCommentIdReply().toString())){
                if (comment.getCommentId().equals(comment.getCommentIdReply())) {
                    var obj = this.commentRepository.findById(comment.getCommentIdReply());
                    CommentDTO commentDTOReply = this.commentMapper.commentEntityToCommentDTO(obj.get());
                    replyComments.add(commentDTOReply);
                    commentDTO.setCommentsReply(replyComments);
                }
            }*/

            commentDTO.setCommentTimestampCreate(comment.getCommentTimestampCreate());
            commentDTO.setCommentTimestampUpdate(comment.getCommentTimestampUpdate());
            commentDTO.setPostId(comment.getPost().getPostId());
            return commentDTO;
        }).collect(Collectors.toList());

    }

    public CommentSaveDTO edit(String commentId, CommentSaveDTO commentSaveDTO) {
        Comment commentSave = null;

        try {
            UUID uuid = UUID.fromString(commentId);
            Optional<Comment> comment = this.commentRepository.findById(uuid);
            if (comment.isPresent()) {
                comment.get().setCommentContent(commentSaveDTO.getCommentContent());
                commentSave = commentRepository.save(comment.get());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return commentMapper.commentToCommentDTO(commentSave);
    }

    public void delete(String commentId) {
        UUID uuid = UUID.fromString(commentId);
        Optional<Comment> comment = commentRepository.findById(uuid);
        if (comment.isEmpty()) {
            log.error("Comment With Id " + commentId + " Not Found");
            throw new Exception("Comment Not Found", HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteById(comment.get().getCommentId());
    }
}
