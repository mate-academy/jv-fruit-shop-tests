package core.basesyntax.fruitshop.service.validators;

import core.basesyntax.fruitshop.service.validators.InvalidDataException;

import java.util.List;

public interface OperationsValidator {
    List<String> validator(List<String> uncheckedInfo) throws InvalidDataException;
}
