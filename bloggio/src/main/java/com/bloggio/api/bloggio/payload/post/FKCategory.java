package com.bloggio.api.bloggio.payload.post;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidatorFKCategory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FKCategory {

    String message() default "category not exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
