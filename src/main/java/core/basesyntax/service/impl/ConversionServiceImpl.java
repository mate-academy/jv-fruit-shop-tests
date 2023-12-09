package core.basesyntax.service.impl;

import core.basesyntax.exceptions.NonExistingOperationException;
import core.basesyntax.exceptions.NonNumericDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ConversionService;
import java.util.ArrayList;
import java.util.List;

public class ConversionServiceImpl implements ConversionService {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;

    @Override
    public List<FruitTransaction> convert(List<String[]> data) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        String operationString;
        int quantity;
        for (String[] transaction : data) {
            boolean operationExists = false;
            operationString = transaction[OPERATION_INDEX];
            for (FruitTransaction.Operation operation : FruitTransaction.Operation.values()) {
                if (operation.getCode().equals(operationString)) {
                    operationExists = true;
                    break;
                }
            }
            if (!operationExists) {
                throw new NonExistingOperationException("Provided a non-existing operation code!");
            }
            try {
                quantity = Integer.parseInt(transaction[AMOUNT_INDEX]);
            } catch (NumberFormatException e) {
                throw new NonNumericDataException("Non-numeric data provided as quantity!", e);
            }
            fruitTransactions.add(new FruitTransaction(FruitTransaction
                    .getOperationByCode(operationString),
                    transaction[FRUIT_INDEX],
                    quantity));
        }
        return fruitTransactions;
    }
}
