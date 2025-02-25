package core.basesyntax.handler;

import core.basesyntax.database.Storage;
import core.basesyntax.transactor.FruitTransaction;

public class PurchaseHandler implements OperationHandler {

    @Override
    public void operate(FruitTransaction transaction) {
        int currentAmount = Storage.storage.get(transaction.getFruit());
        int purchaseAmount = transaction.getQuantity();
        int amountAfterPurchase = currentAmount - purchaseAmount;
        if (amountAfterPurchase < 0) {
            throw new RuntimeException(String.format(
                    "Can't do purchase, because amount = %d < purchase = %d",
                    currentAmount, purchaseAmount));
        }
        Storage.storage.put(transaction.getFruit(), amountAfterPurchase);
    }
}
