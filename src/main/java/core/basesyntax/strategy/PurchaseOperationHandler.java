package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public int apply(Transaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity can`t be less than 0");
        }
        Fruit fruit = new Fruit(transaction.getName());
        int currentQuantity = Storage.getFruits().getOrDefault(fruit, 0);
        int newQuantity = currentQuantity - transaction.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("The store does`t have the required amount of fruits");
        }
        Storage.getFruits().put(fruit, newQuantity);
        return newQuantity;
    }
}
