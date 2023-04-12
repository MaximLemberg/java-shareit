package ru.practicum.shareit.common.validate.validator;

import ru.practicum.shareit.common.validate.annotation.EmptyOrNullOrEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EmptyOrNullOrEmailValidator implements ConstraintValidator<EmptyOrNullOrEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        String pattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
        if (Objects.isNull(email) || email.isBlank()) {
            return false;
        }
        if (!email.isEmpty()) {
            return email.matches(pattern);
        }
        return false;
    }
}
