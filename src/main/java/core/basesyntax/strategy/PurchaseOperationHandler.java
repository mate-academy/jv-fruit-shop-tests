package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public int perform(Transaction transferObject) {
        Fruit fruit = new Fruit(transferObject.getName());
        int newQuantity = Storage.getStorage().get(fruit) - transferObject.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("We don't have enough product");
        }
        Storage.getStorage().put(fruit, newQuantity);
        return newQuantity;
    }
}
