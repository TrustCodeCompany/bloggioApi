package com.bloggio.api.bloggio.mapper;

import com.bloggio.api.bloggio.dto.PostListDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.persistence.entity.Post;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PostMapper {

    @Mapping(target = "user.userId", source = "postDTO.userId")
    @Mapping(target = "category.categoryId", source = "postDTO.categoryId")
    Post postDtoToPost(PostSaveDTO postDTO);

    @Mapping(target = "userId", source = "post.user.userId")
    @Mapping(target = "categoryId", source = "post.category.categoryId")
    PostSaveDTO postToPostDTO(Post post);

    List<PostListDTO> postsToPostListDTO(List<Post> postList);

    PostListDTO postToPostWithUserDTO(Post post);
}
