package core.basesyntax.strategy.impl.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strorage.FruitStorage;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        FruitStorage.fruits.put(transaction.getFruit(), transaction.getQuantity());
    }
}
