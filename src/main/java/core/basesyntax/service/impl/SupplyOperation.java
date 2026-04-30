package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class SupplyOperation implements OperationHandler {

    @Override
    public void process(FruitTransaction tx, Map<String, Integer> storage) {
        String fruit = tx.getFruit();
        int quantity = tx.getQuantity();

        Integer current = storage.get(fruit);

        if (current == null) {
            current = 0;
        }

        storage.put(fruit, current + quantity);
    }
}
