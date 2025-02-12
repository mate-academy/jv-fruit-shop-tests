package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        Storage.getFruitStorage().put(fruit, Storage.getFruitStorage().getOrDefault(fruit, 0)
                + transaction.getQuantity());
    }
}
