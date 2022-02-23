package core.basesyntax.strategy;

import core.basesyntax.model.FruitModel;

public class StrategyImpl {
    private final OperationHandler operationHandler;

    public StrategyImpl(OperationHandler operationHandler) {
        this.operationHandler = operationHandler;
    }

    public boolean executeStrategy(FruitModel fruitModel) {
        return operationHandler.doOperation(fruitModel);
    }
}
