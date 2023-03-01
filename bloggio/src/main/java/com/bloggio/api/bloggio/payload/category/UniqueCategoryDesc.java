package com.bloggio.api.bloggio.payload.category;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValidatorCategoryDesc.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueCategoryDesc {

    String message() default "Category Already Exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
