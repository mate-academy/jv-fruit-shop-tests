package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity can't be negative");
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit can't be null");
        }
        int newAmount = Storage.storage.get(fruitTransaction.getFruit())
                - fruitTransaction.getQuantity();
        if (newAmount < 0) {
            throw new RuntimeException(String
                    .format("Can't do purchase, because of shortage of %s. You can buy only %d",
                            fruitTransaction.getFruit(),
                            Storage.storage.entrySet().stream()
                                    .filter(e -> e.getKey().equals("banana"))
                                    .mapToInt(Map.Entry::getValue).sum()));
        }
        Storage.storage.put(fruitTransaction.getFruit(), newAmount);
    }
}
