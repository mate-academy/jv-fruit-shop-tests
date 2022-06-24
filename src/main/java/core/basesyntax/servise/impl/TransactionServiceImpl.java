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

    @Override
    public List<Transaction> processData(List<String> records) {
        List<Transaction> transactions = new ArrayList<>();
        records.remove(0);
        for (String record : records) {
            String[] recordSplit = record.split(COMMA_DELIMITER);
            Transaction.Operation operation =
                    Transaction.getOperationType(recordSplit[OPERATION_INDEX]);
            String item = recordSplit[ITEM_INDEX];
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
