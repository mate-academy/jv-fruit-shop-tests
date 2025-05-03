package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (Storage.fruits.get(fruit) != null) {
            Storage.fruits.put(fruit, Storage.fruits.get(fruit) 
                    + Math.abs(transaction.getQuantity()));
        } else {
            Storage.fruits.put(fruit, Math.abs(transaction.getQuantity()));
        }
    }
}
