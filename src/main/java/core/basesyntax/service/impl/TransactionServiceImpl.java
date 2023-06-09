package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {
    public static final int OPERATION_INDEX = 0;
    public static final int FRUIT_INDEX = 1;
    public static final int AMOUNT_INDEX = 2;
    public static final String SEPARATOR = ",";

    @Override
    public List<FruitTransaction> parseTransactions(List<String> transaction) {
        transaction.remove(OPERATION_INDEX);
        return transaction.stream()
                .map(this::parseRow)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseRow(String row) {
        String[] separateRow = row.split(SEPARATOR);
        return new FruitTransaction(FruitTransaction.Operation
                .getOperation(separateRow[OPERATION_INDEX]),
                separateRow[FRUIT_INDEX],
                Integer.parseInt(separateRow[AMOUNT_INDEX]));
    }
}
