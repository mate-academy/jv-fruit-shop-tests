package core.basesyntax.strategy.impl.operation;

import core.basesyntax.exception.ProductNotFoundException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strorage.FruitStorage;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        isAvailability(transaction);
        int fruitsStorageValue = FruitStorage.fruits.get(transaction.getFruit());
        FruitStorage.fruits.put(transaction.getFruit(),
                fruitsStorageValue - transaction.getQuantity());

    }

    private void isAvailability(FruitTransaction transaction) {
        String transactionFruit = transaction.getFruit();
        if (FruitStorage.fruits.containsKey(transactionFruit)) {
            if (FruitStorage.fruits.get(transactionFruit) < transaction.getQuantity()) {
                throw new ProductNotFoundException("Not enough fruit, but you can buy "
                        + transaction.getQuantity());
            }
            return;
        }
        throw new ProductNotFoundException("Sorry, but these fruits not available at the moment "
                + transactionFruit);
    }
}
