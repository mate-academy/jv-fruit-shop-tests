package core.basesyntax.strategy;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getHandler_BalanceOperation_IsOk() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertTrue(actual instanceof BalanceOperationHandler);
    }

    @Test
    void getHandler_PurchaseOperation_IsOk() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        assertTrue(actual instanceof PurchaseOperationHandler);
    }

    @Test
    void getHandler_SupplyOperation_IsOk() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertTrue(actual instanceof SupplyOperationHandler);
    }

    @Test
    void getHandler_ReturnOperation_IsOk() {
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        assertTrue(actual instanceof ReturnOperationHandler);
    }
}
