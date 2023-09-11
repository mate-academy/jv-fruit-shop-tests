package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.operations.OperationHandler;
import core.basesyntax.operations.impl.BalanceOperationHandler;
import core.basesyntax.operations.impl.PurchaseOperationHandler;
import core.basesyntax.operations.impl.ReturnOperationHandler;
import core.basesyntax.operations.impl.SupplyOperationHandler;
import core.basesyntax.service.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private final Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();

    public Map<Operation, OperationHandler> getOperationHandlerMap() {
        return operationHandlerMap;
    }

    @BeforeEach
    void setUp() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getOperationHandler_ok() {
        Assertions.assertEquals(SupplyOperationHandler.class,
                operationStrategy.getOperationHandler(Operation.SUPPLY).getClass());
    }
}
