package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int AMOUNT_INDEX = 2;
    public static final String SEPARATOR = ",";

    @Override
    public List<FruitTransaction> parseTransactions(List<String> transactions) {
        List<FruitTransaction> newTransactions = new ArrayList<>();
        if (transactions.isEmpty()) {
            throw new RuntimeException("Transactions can't be empty.");
        }
        for (String transaction : transactions) {
            String[] splitTransaction = transaction.split(SEPARATOR);
            if (splitTransaction.length < 3) {
                throw new RuntimeException("Invalid input format: " + transaction);
            }
            FruitTransaction.Operation operation = FruitTransaction.Operation
                    .getOperationByCode(splitTransaction[OPERATION_INDEX]);
            String fruit = splitTransaction[FRUIT_INDEX];
            int quantity = Integer.parseInt(splitTransaction[AMOUNT_INDEX]);
            newTransactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return newTransactions;
    }
}
