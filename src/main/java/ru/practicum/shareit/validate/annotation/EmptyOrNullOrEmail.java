package ru.practicum.shareit.validate.annotation;



import ru.practicum.shareit.validate.validator.EmptyOrNullOrEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmptyOrNullOrEmailValidator.class)
@Documented
public @interface EmptyOrNullOrEmail {
    String message() default "{EmptyOrNullOrEmail.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}