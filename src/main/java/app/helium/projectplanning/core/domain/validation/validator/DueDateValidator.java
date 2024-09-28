package app.helium.projectplanning.core.domain.validation.validator;

import app.helium.projectplanning.core.domain.model.HasDueDateItem;
import app.helium.projectplanning.core.domain.validation.constraint.ValidDueDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DueDateValidator implements ConstraintValidator<ValidDueDate, HasDueDateItem> {

    @Override
    public boolean isValid(HasDueDateItem item, ConstraintValidatorContext context) {
        return item.hasValidDueDate();
    }
}
