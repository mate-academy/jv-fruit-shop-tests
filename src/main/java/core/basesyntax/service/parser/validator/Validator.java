package core.basesyntax.service.parser.validator;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitOperationDto;

public interface Validator {
    boolean validate(String value) throws ValidationException;

    boolean validFruitOperationDto(FruitOperationDto fruitOperationDto);
}
