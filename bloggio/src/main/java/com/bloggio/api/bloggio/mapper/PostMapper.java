package com.bloggio.api.bloggio.mapper;

import com.bloggio.api.bloggio.dto.PostListDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.persistence.entity.Post;
import org.mapstruct.*;

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

    @Mapping(target = "categoryDesc", source = "post.category.categoryName")
    PostListDTO postToPostWithUserDTO(Post post);

}
