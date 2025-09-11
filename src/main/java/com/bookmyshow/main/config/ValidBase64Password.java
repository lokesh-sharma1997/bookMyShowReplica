package com.bookmyshow.main.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = Base64PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBase64Password {
    String message() default "Invalid Base64 encoded password";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
