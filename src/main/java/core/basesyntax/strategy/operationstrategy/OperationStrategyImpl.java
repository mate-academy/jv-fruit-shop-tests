package core.basesyntax.strategy.operationstrategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.transactionhandlers.TransactionHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, TransactionHandler> operationMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, TransactionHandler> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public TransactionHandler get(FruitTransaction.Operation operation) {
        return operationMap.get(operation);
    }
}
