package core.basesyntax.service.impl;

import core.basesyntax.service.DataConverter;
import core.basesyntax.strategy.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final int FRUIT_INDEX = 1;
    private static final int OPERATION_INDEX = 0;
    private static final int QUANTITY_INDEX = 2;
    private static final int VALID_LENGTH = 3;

    public List<FruitTransaction> convertToTransaction(List<String> rawData) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (int i = 1; i < rawData.size(); i++) {
            String[] parts = rawData.get(i).split(",");
            try {
                if (parts.length != VALID_LENGTH) {
                    throw new IllegalArgumentException("Invalid data format at line "
                            + (i + 1) + ": " + rawData.get(i));
                }
                FruitTransaction.Operation operation = FruitTransaction
                        .Operation.fromCode(parts[OPERATION_INDEX]);
                String fruit = parts[FRUIT_INDEX];
                int quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
                fruitTransactions.add(new FruitTransaction(operation, fruit, quantity));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format at line "
                        + (i + 1) + ": " + rawData.get(i), e);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error processing data at line "
                        + (i + 1) + ": " + rawData.get(i), e);
            }
        }
        return fruitTransactions;
    }
}
