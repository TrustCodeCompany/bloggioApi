package com.bloggio.api.bloggio.mapper;

import com.bloggio.api.bloggio.dto.PostDTO;
import com.bloggio.api.bloggio.persistence.entity.Post;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PostMapper {

    Post postDtoToPost(PostDTO postDTO);

    PostDTO postToPostDTO(Post post);
}
