package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.PostDTO;
import com.bloggio.api.bloggio.dto.UsersDTO;
import com.bloggio.api.bloggio.mapper.PostMapperImpl;
import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.PostRepository;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class PostService {

    private final PostRepository postRepository;

    private final UsersRepository usersRepository;

    private final PostMapperImpl postMapper;


    public PostService(PostRepository postRepository, UsersRepository usersRepository, PostMapperImpl postMapper) {
        this.postRepository = postRepository;
        this.usersRepository = usersRepository;
        this.postMapper = postMapper;
    }

    public PostDTO create(PostDTO postDTO) throws Exception {
        Post postSave = null;

        if (!existsUsers(postDTO.getUsers())) {
            throw new Exception("El usuario con id " + postDTO.getUsers().getUserId() + " no existe");
        }

        try {
            Post post = postMapper.postDtoToPost(postDTO);
            postSave = postRepository.save(post);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return postMapper.postToPostDTO(postSave);
    }

    private boolean existsUsers(UsersDTO usersDTO) {
        Optional<Users> users = usersRepository.findById(UUID.fromString(usersDTO.getUserId()));
        return users.isPresent();
    }
}
