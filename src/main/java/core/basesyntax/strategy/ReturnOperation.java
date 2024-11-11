package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(ShopStorage storage, FruitTransaction transaction) {
        int currentQuantity = storage.getFruitQuantity(transaction.getFruit());
        storage.setFruitQuantity(transaction.getFruit(), currentQuantity + transaction.getQuantity());
    }
}
