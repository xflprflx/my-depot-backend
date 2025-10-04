package com.xflprflx.my_depot_backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private int minLength;
    private boolean requireUppercase;
    private boolean requireLowercase;
    private boolean requireDigit;
    private boolean requireSpecial;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.requireUppercase = constraintAnnotation.requireUppercase();
        this.requireLowercase = constraintAnnotation.requireLowercase();
        this.requireDigit = constraintAnnotation.requireDigit();
        this.requireSpecial = constraintAnnotation.requireSpecial();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        if (password.length() < minLength) return false;
        if (requireUppercase && !password.matches(".*[A-Z].*")) return false;
        if (requireLowercase && !password.matches(".*[a-z].*")) return false;
        if (requireDigit && !password.matches(".*\\d.*")) return false;
        if (requireSpecial && !password.matches(".*[@$!%*?&].*")) return false;
        return true;
    }
}
