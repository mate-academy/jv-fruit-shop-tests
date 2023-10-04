package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class OperationSupply implements OperationStrategy {
    private final Storage storage;

    public OperationSupply(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void handleOperation(FruitTransaction transaction) {
        storage.addFruitInQuantity(transaction.getFruit(), transaction.getQuantity());
    }
}
