package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int current = ShopStorage.storage.getOrDefault(transaction.getFruit(), 0);
        ShopStorage.storage.put(transaction.getFruit(), current + transaction.getQuantity());
    }
}
