package core.basesyntax.strategy.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class Supplier implements OperationCompiler {
    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Cannot supply negative amount: "
                    + fruitTransaction.getQuantity());
        }

        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Invalid fruit name: "
                    + fruitTransaction.getFruit());
        }

        if (fruitTransaction.getOperation() != FruitTransaction.Operation.SUPPLY) {
            throw new IllegalArgumentException("Invalid operation: "
                    + fruitTransaction.getOperation());
        }

        Storage.fruits.putIfAbsent(fruitTransaction.getFruit(), 0);
        Integer fruitBalance = Storage.fruits.get(fruitTransaction.getFruit());
        Storage.fruits.put(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity() + fruitBalance);
    }
}
