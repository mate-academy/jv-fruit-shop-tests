package core.basesyntax.strategy.impl.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strorage.FruitStorage;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int fruitsStorageValue = FruitStorage.fruits.get(transaction.getFruit());
        FruitStorage.fruits.put(transaction.getFruit(),
                fruitsStorageValue + transaction.getQuantity());
    }
}
