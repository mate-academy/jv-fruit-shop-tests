package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_ok() {
        OperationHandler expectedHandler = new BalanceOperationHandler();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_supplyOperationHandler_ok() {
        OperationHandler expectedHandler = new SupplyOperationHandler();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_purchaseOperationHandler_ok() {
        OperationHandler expectedHandler = new PurchaseOperationHandler();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_returnOperationHandler_ok() {
        OperationHandler expectedHandler = new ReturnOperationHandler();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
