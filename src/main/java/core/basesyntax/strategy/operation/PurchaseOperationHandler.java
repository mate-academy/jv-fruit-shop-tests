package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void operation(FruitTransaction fruitTransaction) {
        String fruitInStorage = fruitTransaction.getFruit();
        int purchaseQty = fruitTransaction.getQuantity();
        int currentQtyInStorage = Storage.fruitsStorage.get(fruitInStorage);

        if (currentQtyInStorage >= purchaseQty) {
            Storage.fruitsStorage.put(fruitInStorage, (currentQtyInStorage - purchaseQty));
        } else {
            throw new RuntimeException(
                    "Quantity of " + fruitInStorage + " in storage " + currentQtyInStorage
                            + ". " + "Purchase quantity is " + purchaseQty + ".");
        }
    }
}
