package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;

import core.basesyntax.storage.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handleTransaction(FruitTransaction transaction) {
        if (fruitStorage.get(transaction.getFruit()) == null) {
            throw new IllegalArgumentException("This item is not on sale");
        }
        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("You can't sell 0 or less fruit");
        }
        if (transaction.getOperation() != FruitTransaction.Operation.PURCHASE) {
            throw new IllegalArgumentException("Unable to process operation "
                    + transaction.getOperation() + "in purchaseHandler");
        }
        if (fruitStorage.containsKey(transaction.getFruit())) {
            int stockNewValue = fruitStorage.get(transaction.getFruit())
                    - transaction.getQuantity();
            if (stockNewValue < 0) {
                throw new RuntimeException("There is no required amount of fruit"
                        + transaction.getFruit());
            }
            fruitStorage.put(transaction.getFruit(), stockNewValue);
        }
    }
}
