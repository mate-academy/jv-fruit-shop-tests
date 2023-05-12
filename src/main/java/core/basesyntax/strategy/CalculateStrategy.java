package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface CalculateStrategy {
    OperationHandler getHandler(FruitTransaction transaction);
}
