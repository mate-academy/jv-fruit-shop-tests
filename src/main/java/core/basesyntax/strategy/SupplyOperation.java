package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void operationProcess(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int quantity = Storage.fruitsStorage.getOrDefault(fruit, 0);
        int newQuantity = quantity + fruitTransaction.getQuantity();
        if (newQuantity >= 0) {
            Storage.fruitsStorage.put(fruit, newQuantity);
        } else {
            throw new RuntimeException("Can't add negative quantity of fruits");
        }
    }
}
