package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.base.Storage;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        Object object = Storage.fruitStorage.put(
                fruit,
                Storage.fruitStorage.getOrDefault(fruit, 0)
        );
    }
}
