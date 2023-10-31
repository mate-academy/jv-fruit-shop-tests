package core.basesyntax.strategy.serviceintrface.operation;

import core.basesyntax.strategy.serviceintrface.operation.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> map;

    public OperationStrategy(OperationHandler... handlers) {
        this.map = new HashMap<>();
        for (OperationHandler handler : handlers) {
            map.put(handler.getType(), handler);
        }
    }

    public OperationHandler getHandler(FruitTransaction.Operation operation) {
        return map.get(operation);
    }
}

