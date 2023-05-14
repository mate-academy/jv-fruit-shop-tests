package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null
                || fruitTransaction.getFruit() == null
                || fruitTransaction.getOperation() == null
                || fruitTransaction.getFruit().isEmpty()) {
            throw new RuntimeException("Bad data transaction: " + fruitTransaction);
        }
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
