package core.basesyntax.data;

import core.basesyntax.data.operations.*;

import java.util.HashMap;
import java.util.Map;

public class DataOperationStrategyImpl implements DataOperationStrategy {
    private final Map<Operation, DataOperation> operationDataMap;

    public DataOperationStrategyImpl(Map<Operation, DataOperation> operationDataMap) {
        this.operationDataMap = operationDataMap;
    }

    public DataOperationStrategyImpl() {
        operationDataMap = new HashMap<>();
        operationDataMap.put(Operation.BALANCE, new OperationBalance());
        operationDataMap.put(Operation.PURCHASE, new OperationPurchase());
        operationDataMap.put(Operation.RETURN, new OperationReturn());
        operationDataMap.put(Operation.SUPPLY, new OperationSupply());
    }

    @Override
    public DataOperation get(Operation operation) {
        return operationDataMap.get(operation);
    }
}
