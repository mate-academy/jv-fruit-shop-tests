package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHalndlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHalndlerMap) {
        this.operationHalndlerMap = operationHalndlerMap;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation codeOperation) {
        return operationHalndlerMap.get(codeOperation);
    }
}
