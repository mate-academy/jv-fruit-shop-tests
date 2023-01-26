package core.basesyntax.strategy;

import core.basesyntax.model.StoreOperation;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.Map;

public class StoreOperationStrategyImpl implements StoreOperationStrategy {
    private Map<StoreOperation, OperationHandler> operationHandlerMap;

    public StoreOperationStrategyImpl(Map<StoreOperation, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperation(StoreOperation storeOperation) {
        return operationHandlerMap.get(storeOperation);
    }
}
