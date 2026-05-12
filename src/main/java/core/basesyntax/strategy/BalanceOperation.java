package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        Storage.getInstance().getFruitStorage().put(fruit, quantity);
    }
}
