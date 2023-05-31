package core.basesyntax.strategy;

import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.template.FruitTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationsHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationsHandlerMap) {
        this.operationsHandlerMap = operationsHandlerMap;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operationType) {
        if (operationType == null) {
            throw new RuntimeException("Can't get OperationHandler for null operation type"
                    + operationType);
        }
        return operationsHandlerMap.get(operationType);
    }

    // edge cas - null
    // operationType -> Test If return correct Handler.
}
