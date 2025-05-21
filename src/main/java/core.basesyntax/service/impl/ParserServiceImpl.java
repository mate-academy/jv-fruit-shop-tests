package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.List;

public class ParserServiceImpl implements ParserService {
    private static final int INDEX_OF_OPERATION_CODE = 0;
    private static final int INDEX_OF_FRUIT_NAME = 1;
    private static final int INDEX_OF_QUANTITY_VALUE = 2;
    private static final String SEPARATOR = ",";

    @Override
    public List<FruitTransaction> parse(List<String> dataFromFile) {
        if (dataFromFile == null || dataFromFile.isEmpty()) {
            throw new IllegalArgumentException("Input data cannot be null or empty.");
        }
        List<FruitTransaction> listOfTransactions = new ArrayList<>();
        for (String transactions : dataFromFile) {
            if (transactions.trim().isEmpty()) {
                continue;
            }
            String[] transactionArray = null;
            try {
                transactionArray = transactions.split(SEPARATOR);
                if (transactionArray.length < 3) {
                    System.err.println("Invalid transaction format! Expected 3 values, but got "
                            + transactionArray.length + " in: " + transactions);
                    continue;
                }
                FruitTransaction fruitTransaction = new FruitTransaction();
                fruitTransaction.setOperation(FruitTransaction
                        .getOperationByCode(transactionArray[INDEX_OF_OPERATION_CODE]));
                fruitTransaction.setFruit(transactionArray[INDEX_OF_FRUIT_NAME]);
                fruitTransaction.setQuantity(Integer
                        .parseInt(transactionArray[INDEX_OF_QUANTITY_VALUE]));
                listOfTransactions.add(fruitTransaction);
            } catch (NumberFormatException e) {
                System.err.println("Invalid quantity value "
                        + (transactionArray != null
                        ? transactionArray[INDEX_OF_QUANTITY_VALUE] : null)
                        + " in transaction: " + transactions);
            }
        }
        return listOfTransactions;
    }
}
