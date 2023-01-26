package core.basesyntax.strategy.handler;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        Integer amount = FruitStorage.storage.getOrDefault(transaction.getFruit(), 0);
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Invalid purchase data of fruit: " + transaction.getFruit());
        }
        if (transaction.getQuantity() > amount) {
            throw new RuntimeException("Total amount of fruit is less then "
                    + transaction.getQuantity());
        }
        FruitStorage.storage.put(transaction.getFruit(), amount - transaction.getQuantity());
    }
}
