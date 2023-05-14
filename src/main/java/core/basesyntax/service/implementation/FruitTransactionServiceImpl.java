package core.basesyntax.service.implementation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_TYPE_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;
    private static final int CORRECT_ARRAY_SIZE = 3;

    @Override
    public FruitTransaction createFruitTransaction(String inputLine) {
        if (inputLine == null) {
            throw new RuntimeException("Input line is null");
        }
        String[] inputLineElements = inputLine.split(SEPARATOR);
        if (inputLineElements.length != CORRECT_ARRAY_SIZE) {
            throw new RuntimeException("Invalid input line");
        }
        FruitTransaction.Operation operation = FruitTransaction.Operation
                .getOperationByCode(inputLineElements[OPERATION_INDEX]);
        String fruit = inputLineElements[FRUIT_TYPE_INDEX];
        int quantity = Integer.parseInt(inputLineElements[FRUIT_QUANTITY_INDEX]);
        return new FruitTransaction(operation, fruit, quantity);
    }
}
