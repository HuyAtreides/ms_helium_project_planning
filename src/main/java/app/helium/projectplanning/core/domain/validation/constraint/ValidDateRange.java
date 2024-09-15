package app.helium.projectplanning.core.domain.validation.constraint;

import app.helium.projectplanning.core.domain.validation.validator.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface ValidDateRange {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "Date range is invalid (start date is after end date)";
}
