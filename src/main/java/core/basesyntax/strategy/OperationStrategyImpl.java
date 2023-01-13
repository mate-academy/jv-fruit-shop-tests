package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.service.amount.BalanceHandler;
import core.basesyntax.service.amount.OperationHandler;
import core.basesyntax.service.amount.PurchaseHandler;
import core.basesyntax.service.amount.ReturnHandler;
import core.basesyntax.service.amount.SupplyHandler;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> operationHandlerMap;

    {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandler());
    }

    @Override
    public OperationHandler getOperationHandler(Operation operation) {
        return operationHandlerMap.get(operation);
    }
}
