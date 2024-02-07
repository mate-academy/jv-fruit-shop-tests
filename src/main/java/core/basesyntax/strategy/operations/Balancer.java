package core.basesyntax.strategy.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class Balancer implements OperationCompiler {
    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Balance cannot be negative: "
                    + fruitTransaction.getQuantity());
        }

        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Invalid fruit name: "
                    + fruitTransaction.getFruit());
        }

        if (fruitTransaction.getOperation() != FruitTransaction.Operation.BALANCE) {
            throw new IllegalArgumentException("Invalid operation: "
                    + fruitTransaction.getOperation());
        }

        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
