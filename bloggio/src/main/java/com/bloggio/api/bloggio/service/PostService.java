package com.bloggio.api.bloggio.service;


import com.bloggio.api.bloggio.dto.PostListDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.exception.Exception;
import com.bloggio.api.bloggio.mapper.PostMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.PostRepository;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Log4j2
public class PostService {

    private final PostRepository postRepository;

    private final UsersRepository usersRepository;

    private final PostMapperImpl postMapper;


    public PostService(PostRepository postRepository, UsersRepository usersRepository,
                       PostMapperImpl postMapper) {
        this.postRepository = postRepository;
        this.usersRepository = usersRepository;
        this.postMapper = postMapper;
    }

    public PostSaveDTO create(PostSaveDTO postSaveDTO) {
        Post postSave = null;

        try {
            Post post = postMapper.postDtoToPost(postSaveDTO);
            postSave = postRepository.save(post);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return postMapper.postToPostDTO(postSave);
    }

    public List<PostListDTO> findAll(){
        return postMapper.postsToPostListDTO(postRepository.findAll());
    }

    public PostListDTO findById(UUID postId){
        Optional<Post> post = postRepository.findById(postId);
        return post.map(postMapper::postToPostWithUserDTO).orElse(null);
    }

    public PostSaveDTO update(UUID postId, PostSaveDTO postSaveDTO){
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()){
            log.error("Post With Id "+postId+" Not Found");
            throw new Exception("Post Not Found", HttpStatus.NOT_FOUND);
        }
        Post postUpdate = postMapper.postDtoToPost(postSaveDTO);
        return postMapper.postToPostDTO(postRepository.save(postUpdate));
    }

    public void delete(UUID postId){
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()){
            log.error("Post With Id "+postId+" Not Found");
            throw new Exception("Post Not Found", HttpStatus.NOT_FOUND);
        }
        var postDelete = Post.builder()
                .postId(postId)
                .postContent(post.get().getPostContent())
                .postDescription(post.get().getPostDescription())
                .postPriority(post.get().getPostPriority())
                .postState(0)
                .user(post.get().getUser())
                .category(post.get().getCategory()).build();
        postRepository.save(postDelete);
    }

}
