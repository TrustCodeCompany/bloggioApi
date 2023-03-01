package com.bloggio.api.bloggio.payload.category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.bloggio.api.bloggio.persistence.repository.CategoryRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ValidatorCategoryDesc implements ConstraintValidator<UniqueCategoryDesc, String> {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("Running is Valid");
        return !categoryRepository.findByCategoryDesc(value).isPresent();
    }

}
