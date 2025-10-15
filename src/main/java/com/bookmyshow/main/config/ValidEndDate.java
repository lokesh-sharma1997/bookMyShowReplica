package com.bookmyshow.main.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EndDateValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEndDate {
	String message() default "End date must be the same or after start date";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
