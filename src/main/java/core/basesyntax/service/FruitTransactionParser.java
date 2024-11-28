package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;

public class FruitTransactionParser {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int OFFSET = 1;

    public List<FruitTransaction> parseTransaction(List<String> transactions) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();

        for (int i = OFFSET; i < transactions.size(); i++) {
            String transaction = transactions.get(i);
            String[] parts = transaction.split(",");

            Operation operation = Operation.getOperation(parts[OPERATION_INDEX]);

            int quantity;

            if (parts[QUANTITY_INDEX] instanceof String) {
                try {
                    quantity = Integer.parseInt((String) parts[QUANTITY_INDEX]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid integer format: "
                            + parts[QUANTITY_INDEX], e);
                }
            } else {
                throw new RuntimeException("Expected a String type but got: "
                        + parts[QUANTITY_INDEX].getClass().getSimpleName());
            }

            FruitTransaction fruitTransaction = new FruitTransaction(operation,
                    parts[FRUIT_INDEX], quantity);

            fruitTransactions.add(fruitTransaction);
        }
        return fruitTransactions;
    }
}
