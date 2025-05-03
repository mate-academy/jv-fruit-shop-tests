package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public class TransactionParser {
    private static final String DELIMITER = ",";
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    public FruitTransaction parseTransaction(String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Empty or null line!");
        }
        String[] parts = line.split(DELIMITER);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid data format!");
        }
        FruitTransaction.Operation operation = FruitTransaction.Operation
                .fromCode(parts[TYPE_INDEX]);
        String fruit = parts[FRUIT_INDEX];
        int quantity;
        try {
            quantity = Integer.parseInt(parts[QUANTITY_INDEX]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quantity format!", e);
        }
        return new FruitTransaction(operation,fruit,quantity);
    }
}
