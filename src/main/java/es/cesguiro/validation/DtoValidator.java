package es.cesguiro.validation;

import jakarta.validation.*;

import java.util.Set;

public class DtoValidator {
    private static final Validator validator;

    static {
        ValidatorFactory factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
                .buildValidatorFactory();
        validator = factory.getValidator();
    }

    public static <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toString());
        }
    }
}
