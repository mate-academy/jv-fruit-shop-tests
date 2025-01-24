package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationType;
import java.util.ArrayList;
import java.util.List;

public class TransactionParser {
    public static final String SEPARATOR = ",";
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int QUANTITY_INDEX = 2;

    public List<FruitTransaction> parseTransactions(List<String> lines) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : lines) {
            try {
                String[] data = line.split(SEPARATOR);
                OperationType operation;
                try {
                    operation = OperationType.fromCode(data[OPERATION_INDEX]);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid operation code: "
                            + data[OPERATION_INDEX]);
                }
                String fruit = data[FRUIT_INDEX];
                int quantity;
                try {
                    quantity = Integer.parseInt(data[QUANTITY_INDEX]);
                    if (quantity < 0) {
                        throw new IllegalArgumentException("Quantity cannot be negative: "
                                + quantity);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Quantity is not a valid number: "
                            + data[QUANTITY_INDEX], e);
                }
                FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
                transactions.add(transaction);
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }
        return transactions;
    }
}
