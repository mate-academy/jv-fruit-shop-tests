package core.basesyntax.services;

import core.basesyntax.services.operation.OperationHandler;
import core.basesyntax.services.transaction.model.ProductTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<ProductTransaction.Operation, OperationHandler> operationMap;

    public OperationStrategyImpl(Map<ProductTransaction.Operation, OperationHandler> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public OperationHandler get(ProductTransaction.Operation operation) {
        return operationMap.get(operation);
    }
}
