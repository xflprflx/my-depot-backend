package com.xflprflx.my_depot_backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({FIELD, METHOD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Senha no formato inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minLength() default 8;

    boolean requireUppercase() default true;

    boolean requireLowercase() default true;

    boolean requireDigit() default true;

    boolean requireSpecial() default true;
}
