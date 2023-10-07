package core.basesyntax.strategy.operation;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;

public interface BaseForOperation {
    static Map<Operation, OperationHandler> getOperationStrategies() {
        Map<Operation, OperationHandler> result = new HashMap<>();
        result.put(Operation.BALANCE, new BalanceImpl());
        result.put(Operation.PURCHASE, new PurchaseImpl());
        result.put(Operation.RETURN, new ReturnImpl());
        result.put(Operation.SUPPLY, new SupplyImpl());
        return result;
    }
}
