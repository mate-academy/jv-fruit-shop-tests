package core.basesyntax.strategy.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class Purchaser implements OperationCompiler {
    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Cannot purchase negative amount: "
                    + fruitTransaction.getQuantity());
        }

        if (fruitTransaction.getQuantity()
                > Storage.fruits.get(fruitTransaction.getFruit())) {
            throw new RuntimeException("There is no such amount on a storage: "
                    + fruitTransaction.getQuantity());
        }

        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Invalid fruit name: "
                    + fruitTransaction.getFruit());
        }

        if (fruitTransaction.getOperation() != FruitTransaction.Operation.PURCHASE) {
            throw new IllegalArgumentException("Invalid operation: "
                    + fruitTransaction.getOperation());
        }

        Integer fruitBalance = Storage.fruits.get(fruitTransaction.getFruit());
        Storage.fruits.put(fruitTransaction.getFruit(),
                fruitBalance - fruitTransaction.getQuantity());
    }
}
