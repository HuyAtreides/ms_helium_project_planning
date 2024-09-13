package app.helium.projectplanning.shared;

import app.helium.projectplanning.shared.datetime.ClockProviderForTesting;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class DomainEntityValidator {

    private final static Validator validator = Validation
            .byDefaultProvider()
            .configure()
            .clockProvider(new ClockProviderForTesting())
            .buildValidatorFactory().getValidator();

    public static <T> void validate(T domainEntity) {
        var constraintViolations = validator.validate(domainEntity);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
