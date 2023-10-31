package core.basesyntax.strategy.serviceintrface.operation;

import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;

public interface OperationHandler {
    int getFruitAmount(int amount);

    FruitTransaction.Operation getType();
}
