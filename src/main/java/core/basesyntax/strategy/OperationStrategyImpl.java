package core.basesyntax.strategy;

import core.basesyntax.FruitTransaction;
import core.basesyntax.operations.Operation;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Activity, Operation> operationMap;

    public OperationStrategyImpl(Map<FruitTransaction.Activity, Operation> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public Operation get(FruitTransaction.Activity activity) {
        return operationMap.get(activity);
    }
}
