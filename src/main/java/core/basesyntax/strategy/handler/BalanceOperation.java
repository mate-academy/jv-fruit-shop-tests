package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid quantity: " + transaction.getQuantity());
        }
        Storage.addFruit(transaction.getFruitName(), transaction.getQuantity());
    }
}
