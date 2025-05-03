package service.impl;

import java.util.Map;
import model.Operation;
import service.OperationHandler;
import service.OperationStrategy;

public class OperationStrategyImpl implements OperationStrategy {
    private static final Map<Operation, OperationHandler> HANDLERS = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(),
            Operation.RETURN, new ReturnOperationHandler(),
            Operation.PURCHASE, new PurchaseOperationHandler(),
            Operation.SUPPLY, new SupplyOperationHandler()
    );

    @Override
    public OperationHandler getOperationHandler(Operation operation) {
        return HANDLERS.get(operation);
    }
}
