package com.sh0tanet.backend.validation;

import com.sh0tanet.backend.dto.AuthRegisterRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, AuthRegisterRequest> {
    @Override
    public boolean isValid(AuthRegisterRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.password() != null
            && value.password().equals(value.confirmPassword());
    }
}