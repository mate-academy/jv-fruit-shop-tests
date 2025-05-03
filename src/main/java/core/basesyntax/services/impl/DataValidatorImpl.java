package core.basesyntax.services.impl;

import core.basesyntax.exception.IncorrectQuantityException;
import core.basesyntax.exception.InsufficientQuantityException;
import core.basesyntax.model.OperationType;
import core.basesyntax.services.interfaces.DataValidator;

public class DataValidatorImpl implements DataValidator {
    private static final int OPERATION_TYPE_INDEX = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final String SPLIT_REGEX = ",";

    @Override
    public void checkIfQuantityPositive(int fruitQuantity) {
        if (fruitQuantity < 0) {
            throw new IncorrectQuantityException("Quantity must be positive");
        }
    }

    public void checkIfQuantitySufficiently(int balance, int fruitQuantity) {
        if (balance - fruitQuantity < 0) {
            throw new InsufficientQuantityException("Insufficient quantity of fruit");
        }
    }

    @Override
    public boolean checkDtoLine(String line) {
        String[] dtoLine = line.split(SPLIT_REGEX);
        OperationType.getOperationType(dtoLine[OPERATION_TYPE_INDEX]);
        int quantity = Integer.parseInt(dtoLine[QUANTITY_INDEX]);
        checkIfQuantityPositive(quantity);
        return true;
    }
}
