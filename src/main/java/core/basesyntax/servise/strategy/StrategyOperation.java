package core.basesyntax.servise.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;

public interface StrategyOperation {
    OperationHandler getOperation(FruitTransaction fruitTransaction);
}
