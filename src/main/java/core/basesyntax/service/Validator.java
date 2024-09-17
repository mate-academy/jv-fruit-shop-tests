package core.basesyntax.service;

import core.basesyntax.exception.ValidationException;
import java.util.List;

public interface Validator<T> {
    boolean validate(List<T> dataFromFile) throws ValidationException;
}
