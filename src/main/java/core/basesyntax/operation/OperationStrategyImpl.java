package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation,OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler getOperationHandler(String operation) {
        return operationHandlerMap.entrySet()
                .stream()
                .filter(op -> op.getKey().getSymbolOperation().equals(operation))
                .map(Map.Entry::getValue)
                .findFirst().get();
    }
}
