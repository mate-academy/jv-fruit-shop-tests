package core.basesyntax.testclasses;

import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.OperationStrategy;

public class OperationStrategyForTest implements OperationStrategy {
    @Override
    public OperationService getOperationHandler(FruitTransaction fruitTransaction) {
        return new MapOfHandlersForTest().getHandlers().get(fruitTransaction.getOperation());
    }
}
