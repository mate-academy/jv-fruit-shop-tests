package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.InventoryService;
import java.util.Map;

public class OperationStrategyProvider {

    private final Map<FruitTransaction.OperationType, OperationHandler> handlers;

    public OperationStrategyProvider(InventoryService inventoryService) {
        handlers = Map.of(
                FruitTransaction.OperationType.ADD, new AddOperationHandler(inventoryService),
                FruitTransaction.OperationType.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.OperationType.RETURN, new ReturnOperationHandler(),
                FruitTransaction.OperationType.BALANCE, new BalanceOperationHandler()
        );
    }

    public OperationHandler getHandler(FruitTransaction.OperationType operation) {
        OperationHandler handler = handlers.get(operation);
        if (handler == null) {
            throw new IllegalArgumentException("Unknown operation: " + operation);
        }
        return handler;
    }
}
