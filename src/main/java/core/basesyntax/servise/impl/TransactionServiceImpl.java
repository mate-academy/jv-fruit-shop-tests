package core.basesyntax.servise.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.servise.TransactionService;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private static final String COMMA_DELIMITER = ",";
    private static final int OPERATION_INDEX = 0;
    private static final int ITEM_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final int TRANSACTION_COMPONENTS = 3;

    @Override
    public List<Transaction> processData(List<String> records) {
        List<Transaction> transactions = new ArrayList<>();
        records.remove(0);
        for (String record : records) {
            String[] recordSplit = record.split(COMMA_DELIMITER);
            if (recordSplit.length != TRANSACTION_COMPONENTS) {
                throw new RuntimeException("Can't proceed."
                        + "The transaction should be composed of " + TRANSACTION_COMPONENTS
                        + " elements, but was: " + recordSplit.length
                        + "for record: " + record);
            }
            Transaction.Operation operation =
                    Transaction.getOperationType(recordSplit[OPERATION_INDEX]);
            String item = recordSplit[ITEM_INDEX];
            if (item.isEmpty() || item.isBlank()) {
                throw new RuntimeException("Can't proceed transaction if item is unnamed");
            }
            int quantity = Integer.parseInt(recordSplit[QUANTITY_INDEX]);
            if (quantity < 0) {
                throw new RuntimeException("Can't proceed transaction with negative quantity");
            }
            Transaction transaction = new Transaction(operation, item, quantity);
            transactions.add(transaction);
        }
        return transactions;
    }
}
