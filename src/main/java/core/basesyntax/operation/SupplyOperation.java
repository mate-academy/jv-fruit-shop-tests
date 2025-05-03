package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        validateTransaction(transaction);
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        Storage.getFruitStorage().put(fruit, Storage.getFruitStorage()
                .getOrDefault(fruit, 0) + quantity);
    }
}
