package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class FruitTransactionProcessorImpl implements FruitTransactionProcessor {
    private final Map<Operation, OperationHandler> operationHandlerMap;

    public FruitTransactionProcessorImpl(Map<Operation, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler get(Operation operation) {
        return operationHandlerMap.get(operation);
    }
}
