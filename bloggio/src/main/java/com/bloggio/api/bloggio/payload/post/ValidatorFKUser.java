package com.bloggio.api.bloggio.payload.post;

import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class ValidatorFKUser implements ConstraintValidator<FKUser, UUID> {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Users> user = usersRepository.findById(uuid);
        log.debug("isValid {}",user);
        return user.isPresent();
    }
}
