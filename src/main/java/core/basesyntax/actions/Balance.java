package core.basesyntax.actions;

import core.basesyntax.strategy.serviceintrface.operation.OperationHandler;
import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;

public class Balance implements OperationHandler {
    @Override
    public int getFruitAmount(int amount) {
        return amount;
    }

    @Override
    public FruitTransaction.Operation getType() {
        return FruitTransaction.Operation.BALANCE;
    }
}
