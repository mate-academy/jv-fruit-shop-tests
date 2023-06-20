package core.basesyntax.strategy.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private static final String EXCEPTION_MESSAGE = "Key can't be: ";
    private final Map<FruitTransaction.Operation, OperationHandler> operationMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation type) {
        if (type == null) {
            throw new TransactionException(EXCEPTION_MESSAGE + type);
        }
        return operationMap.get(type);
    }
}
