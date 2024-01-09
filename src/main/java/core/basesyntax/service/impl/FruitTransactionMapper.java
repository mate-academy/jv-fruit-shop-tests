package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Mapper;
import java.util.Arrays;
import javax.naming.OperationNotSupportedException;

public class FruitTransactionMapper implements Mapper<FruitTransaction> {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public FruitTransaction getMappedObject(String[] data) {
        validateData(data);
        return map(data);
    }

    private FruitTransaction map(String[] data) {
        try {
            return new FruitTransaction(getMapOperation(data[OPERATION_INDEX]),
                    data[FRUIT_NAME_INDEX],
                    Integer.parseInt(data[QUANTITY_INDEX]));
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private FruitTransaction.Operation getMapOperation(String operation)
            throws OperationNotSupportedException {
        return switch (operation) {
            case "b" -> FruitTransaction.Operation.BALANCE;
            case "s" -> FruitTransaction.Operation.SUPPLY;
            case "p" -> FruitTransaction.Operation.PURCHASE;
            case "r" -> FruitTransaction.Operation.RETURN;
            default -> throw new OperationNotSupportedException(
                    operation + " operation is not supported");
        };
    }

    private void validateData(String[] data) {
        if (data.length != 3) {
            throw new RuntimeException("Incorrect input data: " + Arrays.toString(data));
        }
        int colCounter = 1;
        for (String s : data) {
            if (s.isEmpty() || s.isBlank()) {
                throw new RuntimeException("Data in " + colCounter + " column is empty.");
            }
            colCounter++;
        }
        int quantity = Integer.parseInt(data[QUANTITY_INDEX]);
        if (quantity < 0) {
            throw new RuntimeException("Quantity should be positive.");
        }
    }
}
