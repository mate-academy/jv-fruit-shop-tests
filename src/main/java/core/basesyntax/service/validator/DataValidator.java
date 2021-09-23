package core.basesyntax.service.validator;

import core.basesyntax.exception.ValidationException;

public interface DataValidator<T> {
    boolean validate(T value) throws ValidationException, ValidationException;
}
