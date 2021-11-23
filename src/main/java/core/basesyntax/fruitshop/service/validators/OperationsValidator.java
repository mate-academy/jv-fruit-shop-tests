package core.basesyntax.fruitshop.service.validators;

import java.util.List;

public interface OperationsValidator {
    List<String> validator(List<String> uncheckedInfo) throws InvalidDataException;
}
