package core.basesyntax.servise.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import java.util.Map;

public class StrategyOperationImpl implements StrategyOperation {
    private Map<String, OperationHandler> map;

    public StrategyOperationImpl(Map<String, OperationHandler> map) {
        this.map = map;
    }

    @Override
    public OperationHandler getOperation(FruitTransaction fruitTransaction) {
        return map.get(fruitTransaction.getOperation());
    }
}
