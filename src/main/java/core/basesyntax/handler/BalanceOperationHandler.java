package core.basesyntax.handler;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Storage;

public class BalanceOperationHandler implements OperationHandler {
    private final Storage storage;

    public BalanceOperationHandler(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void apply(FruitTransaction transaction) {
        storage.getFruits().put(transaction.getFruit(), transaction.getQuantity());
    }
}
