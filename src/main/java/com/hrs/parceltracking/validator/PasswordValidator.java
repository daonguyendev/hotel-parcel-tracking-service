package com.hrs.parceltracking.validator;

import com.hrs.parceltracking.annotation.ValidPassword;
import com.hrs.parceltracking.constant.RegexConstant;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.matches(RegexConstant.PASSWORD_REGEX);
    }
}
