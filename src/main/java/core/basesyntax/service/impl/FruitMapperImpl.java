package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class FruitMapperImpl implements FruitMapper {
    private static final String OPERATION_FRUIT_AMOUNT_SEPARATOR = ",";
    private static final int INDEX_FOR_OPERATION = 0;
    private static final int INDEX_FOR_FRUIT = 1;
    private static final int INDEX_FOR_QUANTITY = 2;
    private static final int NUMBER_OF_TRANSACTION_ELEMENTS = 3;
    private static final String INCORRECT_NUMBER_OF_TRANSACTION_ELEMENTS_MESSAGE
            = "Incorrect number of transaction elements. Should be ";

    @Override
    public List<FruitTransaction> mapLinesIntoTransactions(List<String> lines) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                throw new RuntimeException("The line is empty");
            }
            FruitTransaction transaction = new FruitTransaction();
            String[] operationFruitAmount = line.split(OPERATION_FRUIT_AMOUNT_SEPARATOR);
            if (operationFruitAmount.length != NUMBER_OF_TRANSACTION_ELEMENTS) {
                throw new RuntimeException(
                        INCORRECT_NUMBER_OF_TRANSACTION_ELEMENTS_MESSAGE
                                + NUMBER_OF_TRANSACTION_ELEMENTS);
            }
            FruitTransaction.Operation operation = FruitTransaction.Operation
                    .getOperationByCode(operationFruitAmount[INDEX_FOR_OPERATION]);
            String fruit = operationFruitAmount[INDEX_FOR_FRUIT];
            int quantity = Integer.parseInt(operationFruitAmount[INDEX_FOR_QUANTITY]);
            transaction.setOperation(operation);
            transaction.setFruit(fruit);
            transaction.setQuantity(quantity);
            transactions.add(transaction);
        }
        return transactions;
    }
}
