package app.helium.projectplanning.core.domain.validation.validator;

import app.helium.projectplanning.core.domain.model.DateRange;
import app.helium.projectplanning.core.domain.validation.constraint.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, DateRange> {

    @Override
    public boolean isValid(DateRange dateRange, ConstraintValidatorContext context) {
        if (dateRange.getStartDate() == null || dateRange.getEndDate() == null) {
            return true;
        }

        return !dateRange.getStartDate().isAfter(dateRange.getEndDate());
    }
}
