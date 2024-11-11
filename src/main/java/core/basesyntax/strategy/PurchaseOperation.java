package core.basesyntax.strategy;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(ShopStorage storage, FruitTransaction transaction) {
        int currentQuantity = storage.getFruitQuantity(transaction.getFruit());
        if (currentQuantity < transaction.getQuantity()) {
            throw new RuntimeException("Not enough " + transaction.getFruit() + " in stock.");
        }
        storage
                .setFruitQuantity(transaction
                        .getFruit(), currentQuantity - transaction.getQuantity());
    }
}
