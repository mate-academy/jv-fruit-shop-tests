package core.basesyntax.service.operations;

import core.basesyntax.dao.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction == null
                || transaction.getFruit() == null
                || transaction.getQuantity() == null
                || transaction.getOperation() == null) {
            throw new IllegalArgumentException("Transaction or its fields must not be null");
        }
        String fruit = transaction.getFruit();
        Integer quantityToAdd = transaction.getQuantity();
        Integer currentQuantity = FruitStorage.storage.getOrDefault(fruit, 0);
        FruitStorage.storage.put(fruit, currentQuantity + quantityToAdd);
    }
}
