package core.basesyntax.operation;

import core.basesyntax.storage.FruitStorage;
import core.basesyntax.transaction.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    private FruitStorage fruitStorage;

    public PurchaseHandler(FruitStorage fruitStorage) {
        this.fruitStorage = fruitStorage;
    }

    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int transactionQuantity = transaction.getQuantity();
        int currentQuantity = fruitStorage.getQuantity(fruit);
        if (transactionQuantity > currentQuantity) {
            throw new RuntimeException("Not enough " + fruit + " in stock for purchase");
        }
        fruitStorage.addFruits(fruit, currentQuantity - transactionQuantity);
    }
}
