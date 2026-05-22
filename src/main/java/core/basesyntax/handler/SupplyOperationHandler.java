package core.basesyntax.handler;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Storage;

public class SupplyOperationHandler implements OperationHandler {
    private final Storage storage;

    public SupplyOperationHandler(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void apply(FruitTransaction transaction) {
        int currentQuantity = storage.getFruits().getOrDefault(transaction.getFruit(), 0);
        storage.getFruits().put(transaction.getFruit(),
                currentQuantity + transaction.getQuantity());
    }
}

