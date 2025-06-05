package core.basesyntax.operation.impl;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        FruitStorage.getStorage().put(
                transaction.getFruit(),
                FruitStorage.getStorage()
                        .getOrDefault(transaction.getFruit(), 0)
                        + transaction.getQuantity());
    }
}
