package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public int apply(Transaction transactionDto) {
        Fruit fruit = new Fruit(transactionDto.getName());
        int currentQuantity = Storage.storage.getOrDefault(fruit, 0);
        int newQuantity = currentQuantity - transactionDto.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("Fruit shop haven't this amount of : "
                    + transactionDto.getName() + "s");
        }
        Storage.storage.put(fruit, newQuantity);
        return newQuantity;
    }
}
