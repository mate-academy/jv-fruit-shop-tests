package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.InventoryService;

public class OperationStrategyProvider {

    private final AddOperationHandler addOperationHandler;
    private final SupplyOperationHandler supplyOperationHandler;
    private final ReturnOperationHandler returnOperationHandler;
    private final BalanceOperationHandler balanceOperationHandler;

    public OperationStrategyProvider(InventoryService inventoryService) {
        this.addOperationHandler = new AddOperationHandler(inventoryService);
        this.supplyOperationHandler = new SupplyOperationHandler();
        this.returnOperationHandler = new ReturnOperationHandler();
        this.balanceOperationHandler = new BalanceOperationHandler();

    }

    public OperationHandler getHandler(FruitTransaction.OperationType operation) {
        switch (operation) {
            case ADD:
                return addOperationHandler;
            case BALANCE:
                return balanceOperationHandler;
            case SUPPLY:
                return supplyOperationHandler;
            case RETURN:
                return returnOperationHandler;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}

