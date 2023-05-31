package core.basesyntax.strategy;

import core.basesyntax.service.model.FruitTransaction;
import core.basesyntax.service.operations.OperationHandler;
import java.util.Map;

public class OperationHandlerStrategyImpl implements OperationHandlerStrategy {
    private final Map<FruitTransaction.TypeOperation, OperationHandler> listOperations;

    public OperationHandlerStrategyImpl(Map<FruitTransaction.TypeOperation,
                                       OperationHandler> listOperations) {
        this.listOperations = listOperations;
    }

    @Override
    public OperationHandler chooseOperation(FruitTransaction.TypeOperation operation) {
        return listOperations.get(operation);
    }
}
