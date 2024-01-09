package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.InputOperation;
import java.util.Map;

public class InputOperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, InputOperation> inputOperationMap;

    public InputOperationStrategyImpl(
            Map<FruitTransaction.Operation, InputOperation> inputOperationMap) {
        this.inputOperationMap = inputOperationMap;
    }

    @Override
    public InputOperation get(FruitTransaction.Operation operation) {
        return inputOperationMap.get(operation);
    }
}
