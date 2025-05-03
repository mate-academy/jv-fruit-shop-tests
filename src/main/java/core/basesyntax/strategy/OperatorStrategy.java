package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface OperatorStrategy {
    OperationHandler getHandler(FruitTransaction transaction);
}
