package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ActivityStrategy;
import core.basesyntax.service.activity.ActivityHandler;
import java.util.Map;

public class ActivityStrategyImpl implements ActivityStrategy {
    private Map<FruitTransaction.Operation, ActivityHandler> operationActivityHandlerMap;

    public ActivityStrategyImpl(Map<FruitTransaction.Operation,
            ActivityHandler> operationActivityHandlerMap) {
        this.operationActivityHandlerMap = operationActivityHandlerMap;
    }

    @Override
    public ActivityHandler getHandler(FruitTransaction.Operation operation) {
        return operationActivityHandlerMap.get(operation);
    }
}
