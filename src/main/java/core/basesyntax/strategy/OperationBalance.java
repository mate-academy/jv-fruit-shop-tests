package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class OperationBalance implements OperationStrategy {
    private final Storage storage;

    public OperationBalance(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (transaction == null) {
            throw new NullPointerException("Object can't be null");
        }
        storage.addFruitInQuantity(transaction.getFruit(), transaction.getQuantity());
    }
}
