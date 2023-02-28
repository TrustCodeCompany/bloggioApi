package com.bloggio.api.bloggio.payload.post;

import com.bloggio.api.bloggio.persistence.entity.Category;
import com.bloggio.api.bloggio.persistence.entity.Users;
import com.bloggio.api.bloggio.persistence.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class ValidatorFKCategory implements ConstraintValidator<FKCategory, UUID> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Category> category = categoryRepository.findById(uuid);
        log.debug("isValid {}", category);
        return category.isPresent();
    }
}
