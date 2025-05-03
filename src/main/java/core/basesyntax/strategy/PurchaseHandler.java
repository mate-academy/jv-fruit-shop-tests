package core.basesyntax.strategy;

import static core.basesyntax.storage.FruitStorage.fruitStorage;

import core.basesyntax.storage.FruitTransaction;
import java.util.NoSuchElementException;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handleTransaction(FruitTransaction transaction) {
        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("You can't sell 0 or less fruit");
        }
        if (!fruitStorage.containsKey(transaction.getFruit())) {
            throw new NoSuchElementException("There is no such item ");
        }
        int stockNewValue = fruitStorage.get(transaction.getFruit())
                - transaction.getQuantity();
        if (stockNewValue < 0) {
            throw new RuntimeException("There is no required amount of fruit"
                    + transaction.getFruit());
        }
        fruitStorage.put(transaction.getFruit(), stockNewValue);
    }
}
