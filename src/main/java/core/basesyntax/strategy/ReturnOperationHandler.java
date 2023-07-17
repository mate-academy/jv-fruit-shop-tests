package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void processOperation(FruitTransaction transaction, Storage fruitsStorage) {
        if (!fruitsStorage.getFruitsStorage().containsKey(transaction.getFruit())) {
            throw new RuntimeException("There is no valid fruit in the storage");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Fruit quantity can't be less than 0");
        }
        fruitsStorage.getFruitsStorage().put(transaction.getFruit(), fruitsStorage
                .getFruitsStorage().get(transaction.getFruit()) + transaction.getQuantity());
    }
}
