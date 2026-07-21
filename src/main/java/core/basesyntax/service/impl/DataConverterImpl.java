package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String COMMA = ",";

    @Override
    public List<FruitTransaction> convert(List<String> value) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : value) {
            if (line.startsWith("type")) {
                continue;
            }
            String[] parsedLine = line.split(COMMA);
            FruitTransaction.Operation operation = FruitTransaction
                    .Operation.fromCode(parsedLine[OPERATION_INDEX].trim());
            String fruit = parsedLine[FRUIT_INDEX].trim();
            int quantity = Integer.parseInt(parsedLine[QUANTITY_INDEX].trim());

            transactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactions;
    }
}

