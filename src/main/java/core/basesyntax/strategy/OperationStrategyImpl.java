package core.basesyntax.strategy;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    @Override
    public OperationHandler getOperationHandler(FruitTransaction fruitTransaction,
                                Map<FruitTransaction.Operation, OperationHandler> mapOfHandler) {
        if (fruitTransaction == null || fruitTransaction.getOperation() == null) {
            throw new RuntimeException("FruitTransaction is null: " + fruitTransaction);
        }
        return mapOfHandler.get(fruitTransaction.getOperation());
    }
}
