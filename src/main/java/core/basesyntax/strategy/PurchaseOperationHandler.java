package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void apply(TransactionDto transactionDto) {
        Fruit fruit = new Fruit(transactionDto.getFruitName());
        int quantity = transactionDto.getQuantity();
        int currentQuantity = Storage.storage.getOrDefault(fruit, 0);
        if (currentQuantity - quantity < 0) {
            throw new RuntimeException("Not enough fruits in storage");
        }
        currentQuantity -= quantity;
        Storage.storage.put(fruit, currentQuantity);
    }
}
