package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private static final String INVALID_QUANTITY = "Invalid quantity: ";

    @Override
    public void process(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException(INVALID_QUANTITY + transaction.getQuantity());
        }
        Storage.fruits.put(transaction.getFruitName(), transaction.getQuantity());
    }
}
