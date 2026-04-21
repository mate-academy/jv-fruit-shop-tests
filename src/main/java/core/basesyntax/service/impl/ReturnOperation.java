package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class ReturnOperation implements OperationHandler {

    @Override
    public void process(FruitTransaction tx, Map<String, Integer> storage) {
        if (tx == null || storage == null) {
            throw new RuntimeException("Transaction and storage cannot be null");
        }

        String fruit = tx.getFruit();

        if (fruit == null || fruit.isBlank()) {
            throw new RuntimeException("Fruit cannot be null or empty");
        }

        int quantity = tx.getQuantity();

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }

        storage.put(fruit, storage.getOrDefault(fruit, 0) + quantity);
    }
}
