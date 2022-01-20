package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;

public class AddOperationHandler implements OperationHandler {
    @Override
    public int apply(Transaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity can`t be less than 0");
        }
        Fruit fruit = new Fruit(transaction.getName());
        int currentQuantity = Storage.getFruits().getOrDefault(fruit, 0);
        Storage.getFruits().put(fruit, currentQuantity + transaction.getQuantity());
        return currentQuantity + transaction.getQuantity();
    }
}
