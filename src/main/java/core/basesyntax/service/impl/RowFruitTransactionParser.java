package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;

public class RowFruitTransactionParser implements FruitTransactionParser<String[]> {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;

    @Override
    public FruitTransaction parse(String[] value) {
        if (value == null) {
            return null;
        } else if (value.length != 3) {
            throw new IllegalArgumentException("Array must have only 3 elements!");
        } else if (value[OPERATION_INDEX] == null
                        || value[FRUIT_INDEX] == null
                        || value[QUANTITY_INDEX] == null) {
            throw new IllegalArgumentException("Array must have no null elements!");
        } else {
            return new FruitTransaction(
                    FruitTransaction.Operation.getByCode(value[OPERATION_INDEX]),
                    value[FRUIT_INDEX],
                    Integer.valueOf(value[QUANTITY_INDEX]));
        }
    }
}
