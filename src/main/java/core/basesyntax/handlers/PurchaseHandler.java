package core.basesyntax.handlers;

import core.basesyntax.storage.Storage;
import core.basesyntax.verificator.NegativeVerifier;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handle(String fruit, int quantity) {
        NegativeVerifier.isNegative(quantity);
        if (Storage.storage.get(fruit) == null) {
            throw new RuntimeException("This fruit is not available in the warehouse");
        }
        int currentQuantity = Storage.storage.get(fruit);
        int quantityAfterPurchase = currentQuantity - quantity;
        if (quantityAfterPurchase < 0) {
            throw new RuntimeException("There is not enough fruit in the warehouse. You can buy "
                    + currentQuantity + " or less");
        }
        Storage.storage.put(fruit, quantityAfterPurchase);
    }
}
