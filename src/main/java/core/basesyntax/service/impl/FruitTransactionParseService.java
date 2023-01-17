package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;

public class FruitTransactionParseService implements ParseService<FruitTransaction> {
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String DELIMITER = ",";

    @Override
    public FruitTransaction parse(String line) {
        if (!line.contains(DELIMITER)) {
            throw new RuntimeException("Couldn't find delimiter to parse data.");
        }
        String[] data = line.split(DELIMITER);
        int quantity = Integer.parseInt(data[QUANTITY_INDEX]);
        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity. Quantity: " + quantity);
        }
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(data[OPERATION_INDEX]);
        fruitTransaction.setFruit(data[FRUIT_INDEX]);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
