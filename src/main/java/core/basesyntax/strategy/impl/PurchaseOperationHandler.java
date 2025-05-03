package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int balance = FruitStorage.fruits.get(transaction.getFruit());
        int purchaseQuantity = transaction.getQuantity();
        if (purchaseQuantity == 0) {
            throw new RuntimeException("Purchase quantity can't be zero");
        }
        if (purchaseQuantity > balance) {
            throw new RuntimeException("Purchase quantity can't be bigger than balance, "
                    + "balance: " + balance + ", purchase quantity: " + purchaseQuantity);
        }
        FruitStorage.fruits.put(transaction.getFruit(), balance - purchaseQuantity);
    }
}
