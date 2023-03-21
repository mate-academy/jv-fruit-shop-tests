package core.basesyntax.strategy;

import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.transactionexecutor.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> handlersMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> handlersMap) {
        this.handlersMap = handlersMap;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        return handlersMap.get(operation);
    }
}
