package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;

public class OperationStrategyImpl implements OperationStrategy {
    @Override
    public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
        switch (operation) {
            case BALANCE:
                return new BalanceOperationHandler();
            case SUPPLY:
                return new SupplyOperationHandler();
            case PURCHASE:
                return new PurchaseOperationHandler();
            case RETURN:
                return new ReturnOperationHandler();
            default:
                throw new RuntimeException("Operation type isn't valid: " + operation);
        }
    }
}
