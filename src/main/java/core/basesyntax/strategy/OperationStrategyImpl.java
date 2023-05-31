package core.basesyntax.strategy;

import core.basesyntax.UnknownOperationException;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> operationMap;

    public OperationStrategyImpl(Map<Operation, OperationHandler> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public OperationHandler get(Operation operationType) {
        try {
            return operationMap.get(operationType);
        } catch (UnknownOperationException e) {
            throw new UnknownOperationException("Entered unknown operation " + operationType);
        }
    }
}
