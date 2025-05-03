package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void processOperation(FruitTransaction transaction, Storage fruitsStorage) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Fruit quantity can't be less than 0");
        }
        fruitsStorage.getFruitsStorage().put(transaction.getFruit(), transaction.getQuantity());
    }
}
