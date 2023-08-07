package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.TransactionParser;
import java.util.ArrayList;
import java.util.List;

public class TransactionsParserImpl implements TransactionParser<List<FruitTransaction>, String> {
    private static final int INDEX_OF_OPERATION_IN_RECORD = 0;
    private static final int INDEX_OF_FRUIT_IN_RECORD = 1;
    private static final int INDEX_OF_QUANTITY_IN_RECORD = 2;
    private static final String COMMA_DIVIDER = ",";

    @Override
    public List<FruitTransaction> parse(String data) {
        if (data == null) {
            throw new InvalidDataException("Data for transactions parsing must not be null!");
        }
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        String[] records = data.split(System.lineSeparator());
        for (int i = 1; i < records.length; i++) {
            fruitTransactions.add(valueOf(records[i]));
        }
        return fruitTransactions;
    }

    private static FruitTransaction valueOf(String record) {
        record = record.trim();
        String[] fields = record.split(COMMA_DIVIDER);
        FruitShopOperation fruitShopOperation =
                FruitShopOperation.fromCode(fields[INDEX_OF_OPERATION_IN_RECORD]);
        String fruit = String.valueOf(fields[INDEX_OF_FRUIT_IN_RECORD]);
        int quantity = Integer.parseInt(fields[INDEX_OF_QUANTITY_IN_RECORD]);
        return new FruitTransaction(fruitShopOperation, fruit, quantity);
    }
}

