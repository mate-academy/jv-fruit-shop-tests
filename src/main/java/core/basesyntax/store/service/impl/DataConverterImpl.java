package core.basesyntax.store.service.impl;

import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String SEPARATOR = ",";
    private static final int START_INDEX = 1;
    private static final int EXPECTED_PARTS_COUNT = 3;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (int i = START_INDEX; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(SEPARATOR);

            if (parts.length != EXPECTED_PARTS_COUNT) {
                throw new IllegalArgumentException("Insufficient data for transaction: "
                        + lines.get(i));
            }

            FruitTransaction.Operation operation = FruitTransaction
                    .Operation.fromCode(parts[OPERATION_INDEX]);
            String fruit = parts[FRUIT_INDEX];
            int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
            transactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactions;
    }
}
