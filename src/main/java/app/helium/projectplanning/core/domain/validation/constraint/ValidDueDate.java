package app.helium.projectplanning.core.domain.validation.constraint;

import app.helium.projectplanning.core.domain.validation.validator.DueDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DueDateValidator.class)
public @interface ValidDueDate {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "Due date is before created date";
}
