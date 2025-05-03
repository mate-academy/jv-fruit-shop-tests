package core.basesyntax.operations.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public int execute(FruitTransaction fruitTransaction) {

        return Storage.getFruitBalance(fruitTransaction.getFruitName());
    }
}
