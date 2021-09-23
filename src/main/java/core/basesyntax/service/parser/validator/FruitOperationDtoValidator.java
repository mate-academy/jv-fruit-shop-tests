package core.basesyntax.service.parser.validator;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitOperationDto;

public class FruitOperationDtoValidator implements Validator {
    private static final String SPLITERATOR_REGEX = ",";
    private static final int MIN_LENGTH = 3;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public boolean validate(String value) throws ValidationException {
        if (value == null) {
            throw new ValidationException("Value is null");
        }
        String[] data = value.split(SPLITERATOR_REGEX);
        if (data.length != MIN_LENGTH) {
            throw new ValidationException("Not enough data: " + value);
        }
        if (Integer.parseInt(data[QUANTITY_INDEX]) < 0) {
            throw new ValidationException("Еhe quantity must not be negative: " + value);
        }
        return true;
    }

    @Override
    public boolean validFruitOperationDto(FruitOperationDto fruitOperationDto) {
        if (fruitOperationDto == null) {
            throw new RuntimeException("FruitOperationDto is null");
        }
        return true;
    }
}
