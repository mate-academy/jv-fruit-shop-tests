package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;

public class TransactionParserImpl implements TransactionParser {
    private static final String SPLIT_SYMBOL = ",";
    private static final int INFO_LINE_INDEX = 0;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_NAME_INDEX = 1;
    private static final int FRUIT_QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> getFruitTransactions(List<String> fileData) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fileData.remove(INFO_LINE_INDEX);
        for (String line : fileData) {
            FruitTransaction fruitTransaction = getFruitTransactionFromRecord(line);
            fruitTransactions.add(fruitTransaction);
        }
        return fruitTransactions;
    }

    private FruitTransaction getFruitTransactionFromRecord(String line) {
        String[] lineInfo = line.split(SPLIT_SYMBOL);
        FruitTransaction.Operation operation = FruitTransaction.Operation
                .getOperationFromString(lineInfo[OPERATION_INDEX]);
        String fruit = lineInfo[FRUIT_NAME_INDEX];
        int quantity = Integer.parseInt(lineInfo[FRUIT_QUANTITY_INDEX]);
        return new FruitTransaction(operation, fruit, quantity);
    }
}
