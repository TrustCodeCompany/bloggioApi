package com.bloggio.api.bloggio.mapper;


import com.bloggio.api.bloggio.dto.CommentDTO;
import com.bloggio.api.bloggio.dto.CommentSaveDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.persistence.entity.Comment;
import com.bloggio.api.bloggio.persistence.entity.Post;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CommentMapper {

    @Mapping(target = "post.postId", source = "commentSaveDTO.postId")
    Comment commentDtoToComment(CommentSaveDTO commentSaveDTO);

    @Mapping(target = "postId", source = "post.postId")
    CommentSaveDTO commentToCommentDTO(Comment comment);

    @Mapping(target = "postId", source = "post.postId")
    CommentDTO commentEntityToCommentDTO(Comment comment);

}
