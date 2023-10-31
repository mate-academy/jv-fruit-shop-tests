package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface OperationStrategy {
    public void handleOperation(FruitTransaction fruitTransaction);
}
