package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.operation.BalanceOperationHandler;
import core.basesyntax.strategy.impl.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.operation.ReturnOperationHandler;
import core.basesyntax.strategy.impl.operation.SupplyOperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    static {
        operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler()
        );
    }

    @Override
    public OperationHandler getOperationHandler(FruitTransaction.Operation operation) {
        return operationHandlerMap.get(operation);
    }
}
