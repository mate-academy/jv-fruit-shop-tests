package core.basesyntax.strategy;

import core.basesyntax.exceptions.PurchaseBeyondStockException;
import core.basesyntax.storage.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (quantity > Storage.getFruits().get(fruit)) {
            throw new PurchaseBeyondStockException("Purchase quantity is bigger than stock. "
                    + "Purchase q-ty: " + quantity
                    + " Stock q-ty: " + Storage.getFruits().get(fruit));
        } else {
            Storage.getFruits().put(fruit, Storage.getFruits().get(fruit) - quantity);
        }
    }
}
