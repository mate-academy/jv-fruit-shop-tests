package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public OperationHandler getHandler(FruitTransaction.Operation operation) {
        switch (operation) {
            case BALANCE:
                return new BalanceOperation();
            case SUPPLY:
                return new SupplyOperation();
            case PURCHASE:
                return new PurchaseOperation();
            case RETURN:
                return new ReturnOperation();
            default:
                throw new UnsupportedOperationException("Unsupported operation");
        }
    }
}
