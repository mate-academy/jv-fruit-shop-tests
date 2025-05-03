package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FruitTransactionParserImpl implements FruitTransactionParser {
    private static final int OPERATION_FIELD_INDEX = 0;
    private static final int FRUIT_FIELD_INDEX = 1;
    private static final int QUANTITY_FIELD_INDEX = 2;
    private static final String dataSeparator = ",";

    @Override
    public List<FruitTransaction> toTransactions(String data) {
        String[] lines = data.split(System.lineSeparator());
        return Stream.of(lines)
                .skip(1)
                .map(this::parseTransaction)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseTransaction(String line) {
        String[] fields = line.split(dataSeparator);
        FruitTransaction.Operation operation =
                FruitTransaction.Operation.fromString(fields[OPERATION_FIELD_INDEX]);
        String fruit = fields[FRUIT_FIELD_INDEX];
        int quantity = Integer.parseInt(fields[QUANTITY_FIELD_INDEX]);
        return new FruitTransaction(operation, fruit, quantity);
    }
}
