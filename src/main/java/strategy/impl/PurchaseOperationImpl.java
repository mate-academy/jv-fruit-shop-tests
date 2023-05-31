package strategy.impl;

import db.Storage;
import model.FruitTransaction;
import strategy.OperationHandler;

public class PurchaseOperationImpl implements OperationHandler {
    private static final int DEFAULT_QUANTITY = 0;

    @Override
    public void handler(FruitTransaction fruitTransaction) {
        if (!fruitTransaction.getOperation().equals(FruitTransaction.Operation.PURCHASE)) {
            throw new RuntimeException("Operation is not Purchase");
        } else {
            int amount = Storage.map.getOrDefault(fruitTransaction.getFruit(), DEFAULT_QUANTITY);
            int purchaseResult = amount - fruitTransaction.getQuantity();
            if (purchaseResult < 0) {
                throw new RuntimeException("Invalid quantity for purchase,"
                        + "you can't buy more than we have");
            }
            Storage.map.put(fruitTransaction.getFruit(), purchaseResult);
        }
    }
}
