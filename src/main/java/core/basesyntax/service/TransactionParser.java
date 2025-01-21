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
            String[] data = line.split(SEPARATOR);
            OperationType operation = OperationType.fromCode(data[OPERATION_INDEX]);
            String fruit = data[FRUIT_INDEX];
            int quantity = Integer.parseInt(data[QUANTITY_INDEX]);
            FruitTransaction transaction = new FruitTransaction(operation, fruit, quantity);
            transactions.add(transaction);
        }
        return transactions;
    }
}
