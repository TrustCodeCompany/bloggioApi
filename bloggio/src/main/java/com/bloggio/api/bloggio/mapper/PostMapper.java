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

    PostListDTO postToPostWithUserDTO(Post post);

    /*@Mapping(target = "post.postId", source = "postSaveDTO.postId")
    @Mapping(target = "post.postContent", source = "postSaveDTO.postContent")
    @Mapping(target = "post.postTitle", source = "postSaveDTO.postTitle")
    @Mapping(target = "post.postDescription", source = "postSaveDTO.postDescription")
    @Mapping(target = "post.postState", source = "postSaveDTO.postState")
    @Mapping(target = "post.postPriority", source = "postSaveDTO.postPriority")
    @Mapping(target = "post.user.userId", source = "postSaveDTO.userId")
    @Mapping(target = "post.category.categoryId", source = "postSaveDTO.categoryId")
    Post postSaveDTOMergePostEntity(PostSaveDTO postSaveDTO, Post post);*/
    //void updatePostFromPostDto(PostSaveDTO postSaveDTO, @MappingTarget Post post);
}
