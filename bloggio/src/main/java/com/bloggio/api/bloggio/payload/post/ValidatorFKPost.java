package com.bloggio.api.bloggio.payload.post;

import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class ValidatorFKPost implements ConstraintValidator<FKPost, UUID> {

    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Post> post = postRepository.findById(uuid);
        log.debug("isValid {}",post);
        return post.isPresent();
    }
}
