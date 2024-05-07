package core.basesyntax.strategy;

import core.basesyntax.servise.impl.FruitTransaction;

public interface OperationStrategy {
    OperationService getOperationHandler(FruitTransaction fruitTransaction);
}
