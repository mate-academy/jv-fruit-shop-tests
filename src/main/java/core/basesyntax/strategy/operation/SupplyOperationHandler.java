package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null
                || fruitTransaction.getFruit() == null
                || fruitTransaction.getOperation() == null
                || fruitTransaction.getFruit().isEmpty()) {
            throw new RuntimeException("Bad data transaction: " + fruitTransaction);
        }
        add(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }

    public void add(String name, int quantity) {
        if (Storage.fruits.containsKey(name)) {
            quantity += Storage.fruits.get(name);
        }
        Storage.fruits.put(name, quantity);
    }
}
