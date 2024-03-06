package com.bloggio.api.bloggio.payload.users;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValidatorUserNickname.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueUserNickname {

    String message() default "Nickname Already Exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
